package com.example.myapplication.customclickListner;

import android.net.Uri;

import com.google.android.exoplayer2.SimpleExoPlayer;

public interface IPlayer
{
    void setSpeed(float speed);

    void initPlayer(Uri uri);

    boolean hasPlayer();

    void realReleasePlayer();

    void onCreate();

    boolean isMediaNeddSource();

    long getCurrentPosition();

    SimpleExoPlayer getExoPlayer();

    void onError();

    void resetPosition();
}
