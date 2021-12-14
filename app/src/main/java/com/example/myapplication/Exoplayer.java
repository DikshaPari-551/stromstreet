package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;

import android.app.Activity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.PostActivity;
import com.example.myapplication.Activities.PostActivity2;
import com.example.myapplication.Activities.UserProfile;
import com.example.myapplication.BottomSheets.BottomSheetOptions;
import com.example.myapplication.customclickListner.ClickListnerDelete;
import com.example.myapplication.customclickListner.IPlayer;
import com.example.myapplication.customclickListner.IPlayerUI;
import com.example.myapplication.customclickListner.PlayerImp;
import com.example.myapplication.entity.ApiCallBack;
import com.example.myapplication.entity.Response.Responce;
import com.example.myapplication.entity.Service_Base.ApiResponseListener;
import com.example.myapplication.entity.Service_Base.ServiceManager;
import com.example.myapplication.util.SavedPrefManager;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.EventLogger;

import android.content.Context;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.util.Util;


import java.io.File;
import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;

public class Exoplayer extends AppCompatActivity implements OnKeyListener, OnTouchListener, ClickListnerDelete,
        OnClickListener, ExoPlayer.EventListener, PlaybackPreparer, SimpleExoPlayer.VideoListener, IPlayerUI, PlaybackControlView.VisibilityListener, ApiResponseListener<Responce> {
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String DRM_LICENSE_URL = "drm_license_url";
    public static final String PREFER_EXTENSION_DECODERS = "prefer_extension_decoders";
    private DefaultTrackSelector trackSelector;
    public static final String ACTION_VIEW = "com.google.android.exoplayer.demo.action.VIEW";
    public static final String EXTENSION_EXTRA = "extension";
    private boolean flag = true;
    public static final String ACTION_VIEW_LIST =
            "com.google.android.exoplayer.demo.action.VIEW_LIST";
    public static final String URI_LIST_EXTRA = "uri_list";
    public static final String EXTENSION_LIST_EXTRA = "extension_list";
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private IPlayer player = new PlayerImp(this);

    private View rootView;
    private LinearLayout debugRootView;
    private TextView debugTextView;
    private TextView address;
    private Button retryButton;
    private boolean isShowingTrackSelectionDialog;
    private FrameLayout exo_fullscreen_button;
    private DebugTextViewHelper debugViewHelper;
    private Spinner spinnerSpeeds;
    Boolean click = false;
    private TrackGroupArray lastSeenTrackGroupArray;
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView simpleExoPlayerView;
    private File myFile;
    private boolean playWhenReady = true, isLiked, LikeUnlike = false, isFollow = false, isSaved= false;
    private boolean startAutoPlay;
    private String viedeourl = "";
    private String des = "";
    private String USERID_data = "";
    private String position = "";

    private LinearLayout llComment, llShare, llDownload, bottomlayout;
    private TextView tvPostLike, tvPostComment, tvPostShare, tvPostViews, tvPostUserName, tvUserImageNull, tvDPostTime, txtDiscription;

    private ImageView ivHeart, ivPostDownloads, ivPostComment, ivPostShare, ivPostProfilePic, setting,three_dots;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private int startWindow;
    private long startPosition;
    private String video, videoURL, postId = "", shareId = "", postid = "", USERID = "";
    private ImageButton exo_rew, exo_ffwd;
    private Integer forwardandback;
    private Context mContext;
    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";
    private TextView follow, more, username, commentcount, totalLike, eventType, layoutMore, limitTextMore;
    private CircleImageView profileimg;
    private ImageView backPostButton, comment, video_post_like, savePost, sharePost, notifyPost;
    private LottieAnimationView lottie;

    private LinearLayout internetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player.onCreate();

        setContentView(R.layout.activity_exoplayer);
        mContext = this;
        USERID = SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.Companion.get_id()).toString();
        rootView = findViewById(R.id.root);
        backPostButton = findViewById(R.id.back_arrow_post);
        debugRootView = findViewById(R.id.controls_root);
        profileimg = findViewById(R.id.profileimg);
        sharePost = findViewById(R.id.share_post);
        savePost = findViewById(R.id.saved_post);
        video_post_like = findViewById(R.id.video_post_like);
        notifyPost = findViewById(R.id.notify_post);
        follow = findViewById(R.id.follow);
        address = findViewById(R.id.address);
        more = findViewById(R.id.more);
        simpleExoPlayerView = findViewById(R.id.player_view);
        username = findViewById(R.id.username);
        layoutMore = findViewById(R.id.text_more);
        limitTextMore = findViewById(R.id.limit_text_more);
        eventType = findViewById(R.id.eventType);
        totalLike = findViewById(R.id.totalLike);
        commentcount = findViewById(R.id.commentcount);
        internetConnection = findViewById(R.id.no_wifi);
        simpleExoPlayerView = findViewById(R.id.player_view);
        three_dots = findViewById(R.id.three_dots);
        spinnerSpeeds = ((Spinner) findViewById(R.id.spinner_speeds));
        lottie = findViewById(R.id.loader);
        rootView.setOnTouchListener(this);
        rootView.setOnKeyListener(this);
        if (!checkMyPermission(this))
            permissions(this);
        myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "PMGInstitute" + File.separator + "decrypt.mp4");
        //   myFile = new File(Environment.getExternalStorageDirectory(), "videoplayback.mp4");
        video = String.valueOf(Uri.fromFile(myFile));
        backPostButton.setOnClickListener(v -> {
            finish();
        });
        getINent();
        layoutMore.setMovementMethod(new ScrollingMovementMethod());
        comment = findViewById(R.id.comment);
        comment.setOnClickListener(v -> {
            if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                Intent i = new Intent(this, PostActivity2.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        });
        more.setOnClickListener(v ->{
            limitTextMore.setVisibility(View.GONE);
            layoutMore.setVisibility(View.VISIBLE);
            layoutMore.setText(des);
            more.setText("");
        });

        if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                .equals("true")
        ) {
            three_dots.setVisibility(View.VISIBLE);

        } else {
            three_dots.setVisibility(View.GONE);

        }
        video_post_like.setOnClickListener(v -> {
            int count = 0;
            if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                if(count == 0 && LikeUnlike == false){
                    count++;
                    video_post_like.setImageDrawable(getResources().getDrawable(R.drawable.heartred));

                } else {
                    video_post_like.setImageDrawable(getResources().getDrawable(R.drawable.heartwhite));
                    count = 0;
                }
                likeunlike();
            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        });
        username.setOnClickListener(v -> {

            if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {

                Intent i = new Intent(this, UserProfile.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        });
        profileimg.setOnClickListener(v -> {

            if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {

                Intent i = new Intent(this, UserProfile.class);
                startActivity(i);
            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        });


        savePost.setOnClickListener(v -> {
            int count = 0;
            if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {

                if(count == 0 && isSaved == false){
                    count++;
                    savePost.setImageDrawable(getResources().getDrawable(R.drawable.saved_post));
                } else {
                    savePost.setImageDrawable(getResources().getDrawable(R.drawable.unsaved_post));
                    count = 0;

                }

                saveunsave();
//                if (click == false) {
//                    Toast.makeText(this, "Post Saved", Toast.LENGTH_SHORT).show();
//                    savePost.setImageDrawable(getResources().getDrawable(R.drawable.saved_post));
//
//                    click = true;
//                } else if (click == true) {
//                    Toast.makeText(this, "Post Unsaved", Toast.LENGTH_SHORT).show();
//                    savePost.setImageDrawable(getResources().getDrawable(R.drawable.unsaved_post));
//                    click = false;
//                }
            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        });

        sharePost.setOnClickListener(v -> {
            if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareBody = viedeourl;
                String shareSubject = "Share link:-";
                i.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                i.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(i, "Sharing using"));
            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        });

        notifyPost.setOnClickListener(v -> {
            if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }

        });

        follow.setOnClickListener(v -> {
            if (SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                followunfollow();
            } else {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        });
        three_dots.setOnClickListener(v -> {
            BottomSheetOptions bottomSheet = new BottomSheetOptions(this);
            bottomSheet.show(getSupportFragmentManager(),"");
        });


        debugRootView = (LinearLayout) findViewById(R.id.controls_root);
        debugTextView = (TextView) findViewById(R.id.debug_text_view);
        retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);
        player.setSpeed(1.0f);

        simpleExoPlayerView.setControllerVisibilityListener(this);
        simpleExoPlayerView.requestFocus();

        DefaultTrackSelector.ParametersBuilder builder =
                new DefaultTrackSelector.ParametersBuilder();
        trackSelectorParameters = builder.build();
        postdetails();

    }

    private void getINent() {
        USERID_data = SavedPrefManager.Companion.getStringPreferences(this, SavedPrefManager.USERID).toString();
        position=getIntent().getStringExtra("postion");
    }

    private void saveunsave() {
        ServiceManager serviceManager = new ServiceManager(mContext);
        ApiCallBack<Responce> callBack = new ApiCallBack<Responce>(this, "SaveUnsave", mContext);
//            val apiRequest = Api_Request()
//            apiRequest.email = emailSignUp_et.getText().toString().trim()
        try {
            serviceManager.getSavepost(callBack, USERID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void likeunlike() {
        ServiceManager serviceManager = new ServiceManager(mContext);
        ApiCallBack<Responce> callBack =
                new ApiCallBack<Responce>(this, "LikeUnlike", mContext);
        try {
            serviceManager.getLikeunlike(callBack, USERID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void followunfollow() {
        // String Token = SavedPrefManager.getStringPreferences(this,SavedPrefManager.TOKEN).toString();

        ServiceManager serviceManager = new ServiceManager(mContext);
        ApiCallBack<Responce> callBack = new ApiCallBack<Responce>(this, "FollowUnfollow", mContext);
        try {
            serviceManager.getFollowunfollow(callBack, postid);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void postdetails() {
        ServiceManager serviceManager = new ServiceManager(mContext);
        ApiCallBack<Responce> callBack = new ApiCallBack<Responce>(this, "PostDetails", mContext);
        try {
            serviceManager.getPostDetails(callBack, USERID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletepostapi() {
        ServiceManager serviceManager = new ServiceManager(mContext);
        ApiCallBack<Responce> callBack = new ApiCallBack<Responce>(this, "DeletePost", mContext);
        try {
            serviceManager.deletepost(callBack, USERID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApiSuccess(Responce response, @Nullable String apiName) {
        commentcount.setText(String.valueOf(response.result.getCommentCount()));
        LikeUnlike = response.result.isLike();
        isFollow = response.result.isFollow();
        isSaved = response.result.isSave();

        if (apiName.equals("PostDetails")) {
            username.setText(response.result.getPostResult().getUserId().getUserName());
            des = response.result.getPostResult().getDescription();
            if(des.length() > 70) {
                more.setVisibility(View.VISIBLE);
            }
            try {
                limitTextMore.setText(response.result.getPostResult().getDescription());
                eventType.setText(response.result.getPostResult().getCategoryId().getCategoryName().toString());
                totalLike.setText(String.valueOf(response.result.getLikeCount()));
                commentcount.setText(String.valueOf(response.result.getCommentCount()));
                postid = response.result.getPostResult().getUserId().get_id();
                viedeourl = response.result.getPostResult().getVideoLink();
                address.setText(response.result.getPostResult().getAddress());

                initPre23(response.result.getPostResult().getVideoLink());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (postid.equals(USERID_data)){
                three_dots.setVisibility(View.VISIBLE);
                follow.setVisibility(View.GONE);
            }else {
                three_dots.setVisibility(View.GONE);
                follow.setVisibility(View.VISIBLE);
            }
            SavedPrefManager.Companion.saveStringPreferences(mContext, SavedPrefManager.otherUserId, postid);

            try {
                String profile = response.result.getPostResult().getUserId().getProfilePic().toString();
                Glide.with(this).load(profile).placeholder(R.drawable.circleprofile).into(profileimg);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isFollow == true) {
                follow.setText("Unfollow");
            } else if (isFollow == false) {
                follow.setText("Follow");
            }

            if (LikeUnlike == true) {
                video_post_like.setImageDrawable(getResources().getDrawable(R.drawable.heartred));
            } else if (LikeUnlike == false) {
                video_post_like.setImageDrawable(getResources().getDrawable(R.drawable.heartwhite));
            }

            if (isSaved == true) {
                savePost.setImageDrawable(getResources().getDrawable(R.drawable.saved_post));
            } else if (isSaved == false) {
                savePost.setImageDrawable(getResources().getDrawable(R.drawable.unsaved_post));
            }

        } else if (apiName.equals("FollowUnfollow")) {
            postdetails();
        } else if (apiName.equals("LikeUnlike")) {
            postdetails();
        } else if (apiName.equals("SaveUnsave")) {
            postdetails();
        }else if (apiName.equals("DeletePost")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", (Serializable) position);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }


    @Override
    public void onApiErrorBody(@Nullable String response, @Nullable String apiName) {

    }

    @Override
    public void onApiFailure(@Nullable String failureMessage, @Nullable String apiName) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        updateTrackSelectorParameters();
        updateStartPosition();
        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters);
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
        outState.putLong(KEY_POSITION, startPosition);
    }

    private void updateTrackSelectorParameters() {
        if (trackSelector != null) {
            trackSelectorParameters = trackSelector.getParameters();
        }
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = simpleExoPlayer.getPlayWhenReady();
            startWindow = simpleExoPlayer.getCurrentWindowIndex();
            startPosition = Math.max(0, simpleExoPlayer.getContentPosition());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        releasePlayer();
        player.resetPosition();
        setIntent(intent);
    }

    /*  private void takePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                  @Override
                  public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                      myFile = new File(Environment.getExternalStorageDirectory(), "/Make Video/123.mp4");
                      video= String.valueOf(Uri.fromFile(myFile));
                    }

                    if (report.isAnyPermissionPermanentlyDenied()) {

                    }
                  }

                  @Override
                  public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                  }
                })
                .onSameThread()
                .check();
      }*/
    @Override
    public void onStart() {
        super.onStart();
        //  initAfter23();
    }

    private void initAfter23() {
        if (Util.SDK_INT > 23) {
            initPlayer(video);
        }
    }

    private void initPlayer(String video) {
        TrackSelection.Factory trackSelectionFactory;
        trackSelectionFactory = new AdaptiveTrackSelection.Factory();
        trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        trackSelector.setParameters(trackSelectorParameters);
        lastSeenTrackGroupArray = null;
        simpleExoPlayer = new SimpleExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .build();
        simpleExoPlayer.addListener(new PlayerEventListener());
        // player.setAudioAttributes(AudioAttributes.DEFAULT, // handleAudioFocus= // true);
        simpleExoPlayer.setPlayWhenReady(startAutoPlay);
        simpleExoPlayer.addAnalyticsListener(new EventLogger(trackSelector));
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayerView.setPlaybackPreparer(this);
//    Uri uri = Uri.parse(video);
        myFile = new File(Environment.getExternalStorageDirectory() + File.separator + "PMGInstitute" + File.separator + "decrypt.mp4");
        //   myFile = new File(Environment.getExternalStorageDirectory(), "videoplayback.mp4");
        // videoURL = "https://res.cloudinary.com/yellowseed-pvt-ltd/video/upload/v1622119232/fz3afeketc9u43f8ryho.mp4";
        videoURL = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
        video = String.valueOf(Uri.fromFile(myFile));
        Uri uri = Uri.parse(viedeourl);
        player.initPlayer(uri);

        debugViewHelper = new DebugTextViewHelper(player.getExoPlayer(), debugTextView);
        debugViewHelper.start();

        MediaSource mediaSource = buildMediaSource(uri);

        // simpleExoPlayer.setPlayWhenReady(playWhenReady);
        // simpleExoPlayer.seekTo(currentWindow, playbackPosition);
        simpleExoPlayer.prepare(mediaSource, false, false);
        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            simpleExoPlayer.seekTo(startWindow, startPosition);
        }
        simpleExoPlayer.prepare(mediaSource, !haveStartPosition, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        postdetails();
    }

    private void initPre23(String videoLink) {
        if ((Util.SDK_INT <= 23 || !player.hasPlayer())) {
            initPlayer(videoLink);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePre23();
    }

    private void releasePre23() {
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseAfter23();
    }

    private void releaseAfter23() {
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initPlayer(video);
        } else {
            // showToast(R.string.storage_permission_denied);
            finish();
        }
    }

    // OnTouchListener methods

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            toggleControlsVisibility();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            view.performClick();
        }
        return true;
    }

    // OnKeyListener methods

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return keyCode != KeyEvent.KEYCODE_BACK && keyCode != KeyEvent.KEYCODE_ESCAPE
                && keyCode != KeyEvent.KEYCODE_MENU;
    }

    // OnClickListener methods

    @Override
    public void onClick(View view) {
        if (view == retryButton) {
            initPlayer(video);
        } else if (view.getParent() == debugRootView) {
            //trackSelectionHelper.showSelectionDialog(this, ((Button) view).getText(),
            //        player.getTrackInfo(), (int) view.getTag());
        }
    }


    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing.
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_ENDED) {
            //initPre23(videoURL);
          //  FINISH();
        }
    updateButtonVisibilities();
    }

    private void FINISH() {

        finish();
        overridePendingTransition(R.anim.stay, R.anim.activity_popup_hide);

    }

    @Override
    public void onBackPressed() {
        FINISH();
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        String errorString = null;
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = e.getRendererException();
            if (cause instanceof DecoderInitializationException) {
                // Special case for decoder initialization failures.
                DecoderInitializationException decoderInitializationException =
                        (DecoderInitializationException) cause;
                if (decoderInitializationException.codecInfo == null) {
                    if (decoderInitializationException.getCause() instanceof DecoderQueryException) {
                        // errorString = getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        //errorString = getString(R.string.error_no_secure_decoder,decoderInitializationException.mimeType);
                    } else {
                        //rrorString = getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                    }
                } else {
                    //errorString = getString(R.string.error_instantiating_decoder,
                    //// decoderInitializationException.decoderName);
                }
            }
        }
        if (errorString != null) {
            showToast(errorString);
        }
        player.onError();
        updateButtonVisibilities();
        // showControls();
    }


    // SimpleExoPlayer.VideoListener implementation

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthAspectRatio) {
    }

    @Override
    public void onRenderedFirstFrame() {

    }

    // User controls

    public void updateButtonVisibilities() {
        debugRootView.removeAllViews();

        retryButton.setVisibility(player.isMediaNeddSource() ? View.VISIBLE : View.GONE);
        debugRootView.addView(retryButton);

        if (!player.hasPlayer()) {
            return;
        }
    }

    private void toggleControlsVisibility() {

    }

    private void showControls() {
        debugRootView.setVisibility(View.VISIBLE);
    }

    public void showToast(int messageId) {
        showToast(getString(messageId));
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreatePlayer() {
        simpleExoPlayerView.setPlayer(player.getExoPlayer());
    }

    private void releasePlayer() {
        if (player.hasPlayer()) {
            player.realReleasePlayer();
            debugViewHelper.stop();
            debugViewHelper = null;
        }
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    public static boolean checkMyPermission(Context mContext) {
        int permissionChecExternal = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionChecInternal = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);


        return permissionChecExternal == 0 && permissionChecInternal == 0;
    }

    public static void permissions(Context mContext) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.System.canWrite(mContext)) {
                ((Activity) mContext).requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 2);
            }

        }
    }


    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void onVisibilityChange(int visibility) {
        debugRootView.setVisibility(visibility);
    }

    @Override
    public void preparePlayback() {
        simpleExoPlayer.retry();

    }

    @Override
    public void deletePost() {
        deletepostapi();
    }


    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, @Player.State int playbackState) {
            if (playbackState == Player.STATE_ENDED) {
                showControls();
            }
            // updateButtonVisibility();
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
//            if (isBehindLiveWindow(e)) {
//                clearStartPosition();
//                initializePlayer();
//            } else {
//               // updateButtonVisibility();
//                showControls();
//            }
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            // updateButtonVisibility();
            if (trackGroups != lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        //  showToast(R.string.error_unsupported_video);
                    }
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        //  showToast(R.string.error_unsupported_audio);
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }
        }
    }
}
