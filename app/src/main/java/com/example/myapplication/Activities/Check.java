//import android.Manifest
//import android.animation.Animator
//import android.content.ActivityNotFoundException
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.content.res.ColorStateList
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.*
//import android.provider.MediaStore
//import android.text.Editable
//import android.text.TextUtils
//import android.text.TextWatcher
//import android.text.format.DateUtils
//import android.util.Log
//import android.view.*
//import android.view.inputmethod.InputMethodManager
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import androidx.core.content.res.ResourcesCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.google.gson.Gson
//import com.google.gson.JsonObject
//import com.google.gson.JsonParser
//import com.google.gson.reflect.TypeToken
//import com.karumi.dexter.Dexter
//import com.karumi.dexter.MultiplePermissionsReport
//import com.karumi.dexter.PermissionToken
//import com.karumi.dexter.listener.PermissionRequest
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener
//import com.sis.app.models.ChatDetailResponseModel
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
//import kotlinx.android.synthetic.main.activity_chat.*
//import okhttp3.MediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import org.json.JSONException
//import org.json.JSONObject
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.io.*
//import java.text.ParseException
//import java.text.SimpleDateFormat
//import java.util.*
//
//class ChatActivity : AppCompatActivity() {
//
//        var chatList: ArrayList<ChatData> = ArrayList()
//
//private var file_url = ""
//        var thumb_url = ""
//        var r_id = 0
//        var recieverId = ""
//private var isOpen = false
//private lateinit var socketInstance: SocketManager
//protected val CAMERA_REQUEST = 0
//protected val GALLERY_PICTURE = 1
//protected val MEDIA_REQUEST_CODE = 10
//        lateinit var pictureActionIntent: Intent
//        lateinit var bitmap: Bitmap
//        val PIC_CROP = 2
//        lateinit var f: File
//        lateinit var selectedImage: Uri
//        val PIC_CROP_GALLERY = 3
//        var selectedImagePath: String = ""
//
//private var type = ""
//private var extension = ""
//        var msg_type = "text"
//        var limit = "10"
//        var offset = 0
//private var isPagingEnabled = false
//private var isLoading = false
//private var previousSize = 0
//        var isRunning = true
//private var sharedprferencesDetails: SharedprferencesDetails? = null
//private val CAMERA_PERMISSION_CODE = 23
//private val BUFFER_SIZE = 1024 * 2
//
//private val IMAGE_DIRECTORY = "/demonuts_upload_gallery"
//
//        lateinit var layoutManager: LinearLayoutManager
//        lateinit var ll_bottomView: LinearLayoutManager
//        var progress_bar: ProgressBar?=null
//
//        companion object {
//public var date1: TextView?=null
//
//        }
//        override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chat)
//        var editText = findViewById<EditText>(R.id.editText)
//        var galleryOption = findViewById<LinearLayout>(R.id.galleryOption)
//        var sendMessageOption = findViewById<LinearLayout>(R.id.sendMessageOption)
//        date1 = findViewById<TextView>(R.id.date)
//        progress_bar = findViewById<ProgressBar>(R.id.progress_bar)
//        var ll_bottomView = findViewById<LinearLayout>(R.id.ll_bottomView)
//
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//        val policy: StrictMode.ThreadPolicy =
//        StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//        }
//
//        sharedprferencesDetails = SharedprferencesDetails.getInstance(this)
//
//        val builder = StrictMode.VmPolicy.Builder()
//        StrictMode.setVmPolicy(builder.build())
//        if (intent.extras != null) {
//        socketInstance = SocketManager.getInstance(this)
//        r_id = intent.getIntExtra("r_id", 0)
//        Log.e("browse_page_err", "recieverId" + intent.getStringExtra("r_id").toString())
//
//        tvHeader.text = "Dr. " + intent.getStringExtra("name")
//        recieverId = intent.getStringExtra("r_id").toString()
//
//        Glide.with(applicationContext)
//        .load(intent.getStringExtra("image"))
//        .thumbnail(Glide.with(applicationContext).load(R.drawable.image2))
//        .into(imguserprofile)
//
//        if (!ConnectionDetector.getInstance(this).isNetworkAvailable) {
//        lastSeen.text = "Offline"
//        }
//
//        }
////        if (AppConstants.elapsedDays >= 0) {
////            ll_bottomView.visibility=View.VISIBLE
////        } else {
////            ll_bottomView.visibility=View.GONE
////        }
//        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        chat.layoutManager = layoutManager
//        shimmerChat.startShimmer()
//
//        initializeSocket()
//
////        addOnScrollListener()
////        if (!socketInstance.isConnected) {
////            Log.e("browse_page_err", "" + "t.message")
////
////            socketInstance.connect()
////            Log.e("browse_page_err", "" + "t.messagerrr"+ socketInstance.connect())
////            onlineStatus()
////        } else{
////            Log.e("browse_page_err", "" + "t.message"+ socketInstance.connect())
////            //onlineStatus()
////        }
//
//
//        options.setOnClickListener {
//        //   optionpopup(options)
//        }
//        videoCall.setOnClickListener {
//
//
//        callingRequest("video")
////
////            startActivity(
////                Intent(this, VideoViewActivity::class.java).putExtra("outgoing", 1).putExtra(
////                    "accessToken",
////                    "dh7SrUC0RcenWmHOBpm1xD:APA91bGGiySJ8YnVIjPJS7UsIttQh1lTymctigasqJCmlJttZ6chb2exiGu1DVUzHhHurmhC7VbgXuyqurVx04Y0l9DV5n_TC8RSowOwPLZGdmZY108vHeCZ1_6lgW87QnF5R_uKlJMs"
////                )
////                    .putExtra("roomName", "roomName")
////            )
////            startActivity(Intent(this, VideoViewActivity :: class.java).putExtra("outgoing",1).putExtra("accessToken","it.message")
////                    .putExtra("roomName","it.roomName")
////                    .putExtra("coachId",12)
////                    .putExtra("userSessionId","it.userPlanSessionId")
////            )
//        }
//
//        audioCall.setOnClickListener {
//        callingRequest("audio")
//
//        //startActivity(Intent(this, VoiceActivity :: class.java))
//        }
//
//        editText.addTextChangedListener(object : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//        if (s.toString().length > 0) {
//        galleryOption.visibility = View.GONE
//        sendMessageOption.visibility = View.VISIBLE
//        } else {
//        galleryOption.visibility = View.VISIBLE
//        sendMessageOption.visibility = View.GONE
//        }
//        }
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        }
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        }
//        })
//        sendMessageOption.setOnClickListener {
//        if (ConnectionDetector.getInstance(this).isNetworkAvailable) {
//        if (editText.text.toString().trim().equals("")) {
//        CommanUtils.showCustomSnackBar(
//        sendMessageOption,
//        this,
//        "Please enter message first"
//        )
//
//        // Toast.makeText(this, "Please enter message first", Toast.LENGTH_LONG).show()
//        } else {
//        sendMessage()
//        }
//        } else {
//        Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG)
//        .show()
//        }
//
//        }
//
//        attachement.setOnClickListener {
//        val intent = Intent()
//        intent.action = Intent.ACTION_GET_CONTENT
//        intent.type = "application/pdf"
//        startActivityForResult(intent, 11)
//        }
//
//        keyboard.setOnClickListener {
//        keyboard.visibility = View.GONE
//        var imm: InputMethodManager =
//        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(layoutMain, InputMethodManager.SHOW_IMPLICIT);
//        }
//
//        imgBack.setOnClickListener {
//        onBackPressed()
//        }
//
//        layoutMain.setOnClickListener {
//        isOpen = false
//        layoutButtons.visibility = View.GONE
//        }
//
//
//        editText.setOnClickListener {
//        msg_type = "text"
//        }
//        send.setOnClickListener {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//        if (ContextCompat.checkSelfPermission(
//        this,
//        android.Manifest.permission.CAMERA
//        ) != PackageManager.PERMISSION_GRANTED ||
//        ContextCompat.checkSelfPermission(
//        this,
//        android.Manifest.permission.READ_EXTERNAL_STORAGE
//        ) != PackageManager.PERMISSION_GRANTED ||
//        ContextCompat.checkSelfPermission(
//        this,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//        ) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//        requestPermissions(
//        arrayOf(
//        android.Manifest.permission.CAMERA,
//        android.Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//        ),
//        CAMERA_PERMISSION_CODE
//        )
//        } else {
//        //   val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        // startActivityForResult(intent, REQUEST_CAMERA)
//        openPhoto()
//
//        }
//        } else {
//        // val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        //startActivityForResult(intent, REQUEST_CAMERA)
//        openPhoto()
//
//
//        }
//        }
//
//        camera.setOnClickListener {
//        isRunning = false
//        msg_type = "image"
//        val builder = StrictMode.VmPolicy.Builder()
//        StrictMode.setVmPolicy(builder.build())
//        val intent = Intent(
//        MediaStore.ACTION_IMAGE_CAPTURE
//        )
//        val f = File(
//        Environment
//        .getExternalStorageDirectory(), "temp.jpg"
//        )
//        intent.putExtra(
//        MediaStore.EXTRA_OUTPUT,
//        Uri.fromFile(f)
//        )
//
//        startActivityForResult(
//        intent,
//        CAMERA_REQUEST
//        )
//        }
//
//        gallery.setOnClickListener {
//        isRunning = false
//        msg_type = "image"
//        val builder = StrictMode.VmPolicy.Builder()
//        StrictMode.setVmPolicy(builder.build())
//        val pictureActionIntent = Intent(
//        Intent.ACTION_PICK,
//        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        )
//        startActivityForResult(
//        pictureActionIntent,
//        GALLERY_PICTURE
//        )
//        }
//
//        document.setOnClickListener {
//        isRunning = false
//        msg_type = "document"
//        var target = FileUtils1.createGetContentIntent()
//        // Create the chooser Intent
//        var intent = Intent.createChooser(
//        target, getString(R.string.app_name)
//        )
//        try {
//        startActivityForResult(intent, MEDIA_REQUEST_CODE)
//        } catch (e: ActivityNotFoundException) {
//        // The reason for the existence of aFileChooser
//        }
//
//        }
//        // onLogEvents(FirebaseEvents.chatBeginChat, Bundle())
//        }
//
//        override fun onResume() {
//        super.onResume()
//
//
//        }
//
//        fun openPhoto() {
//        CropImage.activity()
//        .setGuidelines(CropImageView.Guidelines.ON)
//        .start(this); }
////    fun optionpopup(view: View) {
////        var popUp: PopupMenu = PopupMenu(this, view)
////        var menu: Menu = popUp.menu
////        var inflater: MenuInflater = popUp.menuInflater
////        inflater.inflate(R.menu.language_popup_items, popUp.menu)
////
////        popUp.show()
////
////        popUp.setOnMenuItemClickListener { menuItem: MenuItem? ->
////            when (menuItem!!.itemId) {
////                R.id.deleteChat -> {
////                    val initialiseRideJson = JSONObject()
////                    try {
////                        initialiseRideJson.put(
////                                "s_id", SharedPrefManager.getInstance(this)?.getInt(
////                                AppConstant.USER_ID,
////                                0
////                        )!!
////                        )
////                        initialiseRideJson.put("r_id", r_id)
////                    } catch (e: JSONException) {
////                        e.printStackTrace()
////                    }
////                    socketInstance.sendMsg("deleteChat", initialiseRideJson)
////
////                    true
////                }
////                else -> false
////            }
////
////        }
////
////    }
//
//private fun initializeSocket() {
//
//        if (ConnectionDetector.getInstance(this).isNetworkAvailable) {
//        onAddListeners()
//        if (!socketInstance.isConnected) {
//        socketInstance.connect()
//        } else {
//        onlineStatus()
//
//        }
//
//        } else {
//        showData()
//        }
//        }
//
//private fun onAddListeners() {
//
//        socketInstance.initialize(object : SocketManager.SocketListener {
//        override fun onConnected() {
//        Log.e("browse_page_err", "omd " + "onConnected")
//
//        onlineStatus()
//        }
//
//        override fun onDisConnected() {
//        socketInstance.connect()
//        }
//        })
//
//        socketInstance.addListener("getChatMessages", object : SocketManager.SocketMessageListener {
//        override fun onMessage(vararg args: Any) {
//        val data = args[0] as JSONObject
//        Log.e("browse_page_err", "omd " + data.toString())
//        // CommanUtils.showCustomSnackBar(editText,  this@ChatActivity,data.toString())
//
//        var model: ChatDetailResponseModel = Gson().fromJson(
//        data.toString(),
//        object : TypeToken<ChatDetailResponseModel?>() {}.type
//        )
//        var jsonArr = data.getJSONArray("chat")
//
//        if (jsonArr.length() > 0) {
//        for (i in 0..jsonArr.length() - 1) {
//        val jsonObject = jsonArr.getJSONObject(i)
//
//        var dateData=DateFormated.getFrmatedDateFromApi(jsonObject.getString("created_at"))
//        var check=  AppConstants.chekdate(chatList,dateData)
//
//        if (check!!.equals("1"))
//        {
//        val viewModel =
//        ChatData(
//        jsonObject.getString("chat_id"),
//        jsonObject.getString("sender_id"),
//        jsonObject.getString("message"),
//        jsonObject.getString("media"),
//        jsonObject.getString("sender_type"),
//        jsonObject.getString("read_status"),
//        jsonObject.getString("created_at"),
//        jsonObject.getString("profile_picture"),
//        jsonObject.getString("fullname"),""
//        )
//        chatList.add(viewModel)
//        }else{
//        val viewModel =
//        ChatData(
//        jsonObject.getString("chat_id"),
//        jsonObject.getString("sender_id"),
//        jsonObject.getString("message"),
//        jsonObject.getString("media"),
//        jsonObject.getString("sender_type"),
//        jsonObject.getString("read_status"),
//        jsonObject.getString("created_at"),
//        jsonObject.getString("profile_picture"),
//        jsonObject.getString("fullname"),dateData
//        )
//        chatList.add(viewModel)
//        }
//
//        }
//        }
//
//        showData()
////                if (model.result.online_status.equals("offline")) {
////                    lastSeen.text = "Active " + parseDateToddMMyyyy(model.result.last_seen_time)
////                } else {
////                    lastSeen.text = model.result.online_status
////                }
//        // saveToLocal(model.result.chat_data)
//        }
//        })
//
//
//        socketInstance.addListener("addUser", object : SocketManager.SocketMessageListener {
//        override fun onMessage(vararg args: Any) {
//        val data = args[0] as JSONObject
//        Log.e("browse_page_err", "addUser " + data.toString())
//
//        var model: RecievedMsgResponseData = Gson().fromJson(
//        data.toString(),
//        object : TypeToken<RecievedMsgResponseData?>() {}.type
//        )
//        }
//        })
//
//        socketInstance.addListener("sendMessage", object : SocketManager.SocketMessageListener {
//        override fun onMessage(vararg args: Any) {
//        val data = args[0] as JSONObject
//        Log.e("browse_page_err", "sendMessage " + data.toString())
//        Log.e("browse_page_err", "sendMessage " + chatList.size)
//        //   CommanUtils.showCustomSnackBar(editText,  this@ChatActivity,data.toString())
//        var dateData=DateFormated.getFrmatedDateFromApi(data.getString("created_at"))
//        var check=  AppConstants.chekdate(chatList,dateData)
//        if (check!!.equals("1"))
//        {
//        Log.e("browse_page_err", "sendMessage " + "1")
//        val viewModel =
//        ChatData(
//        data.getString("chat_id"),
//        data.getString("sender_id"),
//        data.getString("message"),
//        data.getString("media"),
//        data.getString("user_type"),
//        data.getString("read_status"),
//        data.getString("created_at"),
//        "",
//        "",""
//        )
//        chatList.add(viewModel)
//        }else{
//        Log.e("browse_page_err", "sendMessage " + "0")
//        val viewModel =
//        ChatData(
//        data.getString("chat_id"),
//        data.getString("sender_id"),
//        data.getString("message"),
//        data.getString("media"),
//        data.getString("user_type"),
//        data.getString("read_status"),
//        data.getString("created_at"),
//        "",
//        "",dateData
//        )
//        chatList.add(viewModel)
//        }
//
//
//        showData()
//        //chat.adapter!!.notifyDataSetChanged()
//        //= ChatDetailAdapter(this@ChatActivity, removeDuplocatElemts(chatList))
//
//        //chat.scrollToPosition(0)
//
//        Log.e("browse_page_err", "sendMessage " + chatList.size)
//
//
//        }
//        })
//
//
//        }
//
//
//
//private fun showData() {
//        shimmerChat.stopShimmer()
//        shimmerChat.visibility = View.GONE
//
//        try {
////            chatList =
////                    Gson().fromJson(
////                            SharedprferencesDetails.getInstance(this)!!.getString("chat_" + r_id),
////                            object : TypeToken<List<ChatData>>() {}.type
////                    )
//
//        if (chatList.size == 0) {
//        notText.visibility = View.VISIBLE
//        chat.visibility = View.GONE
//        date1!!.visibility = View.GONE
//        } else {
//        notText.visibility = View.GONE
//        chat.visibility = View.VISIBLE
//        date1!!.visibility = View.GONE
//        chat.adapter = ChatDetailAdapter(this, removeDuplocatElemts(chatList))
//        chat.scrollToPosition(removeDuplocatElemts(chatList).size - 1)
//        }
//        } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//        notText.visibility = View.VISIBLE
//        chat.visibility = View.GONE
//        }
//
//        }
//
//        fun removeDuplocatElemts(chatList: ArrayList<ChatData>): ArrayList<ChatData> {
//
//        var copyChatList: ArrayList<ChatData> = ArrayList()
//
//        for (i in 0..chatList.size - 1) {
//        if (!copyChatList.contains(chatList[i])) {
//        copyChatList.add(chatList[i])
//        }
//        }
//
//        return copyChatList
//        }
//
//private fun sendMessage() {
//        Log.e("browse_page_err", "" + "send msg")
//        Log.e("browse_page_err", "" +AppConstants.appoint_id)
//        Log.e("browse_page_err", "" +AppConstants.appointmentType)
//
//        val initialiseRideJson = JSONObject()
//        try {
//        initialiseRideJson.put(
//        "sender_id",
//        sharedprferencesDetails!!.getString(SharedprferencesDetails.patientNo)!!
//        )
//        initialiseRideJson.put("receiver_id", recieverId)
//        if (msg_type.equals("text")) {
//        initialiseRideJson.put("message", editText.text.toString())
//        } else {
//        initialiseRideJson.put("message", "Image")
//        }
//        initialiseRideJson.put("user_type", "user")
//        initialiseRideJson.put("media", "")
//        initialiseRideJson.put("appoint_id", AppConstants.appoint_id)
//        initialiseRideJson.put("appointment_type", AppConstants.appointmentType)
//        } catch (e: JSONException) {
//        e.printStackTrace()
//        }
//        socketInstance.sendMsg("sendMessage", initialiseRideJson)
//        if (msg_type.equals("text")) {
//        editText.setText("")
//        }
//        file_url = ""
//        thumb_url = ""
//        msg_type = "text"
//        Log.e("browse_page_err", "" + "send msg" + initialiseRideJson.toString())
//
//        }
//
//
//private fun getChatList() {
//        val initialiseRideJson = JSONObject()
//        try {
//        initialiseRideJson.put(
//        "user_id", sharedprferencesDetails!!.getString(SharedprferencesDetails.patientNo)!!
//        )
//        initialiseRideJson.put("doctor_id", recieverId)
//        initialiseRideJson.put("appoint_id", AppConstants.appoint_id)
//
//        } catch (e: JSONException) {
//        e.printStackTrace()
//        }
//        socketInstance.sendMsg("getChatMessages", initialiseRideJson)
//        // onAddListeners()
//        Log.e("browse_page_err", "" + "get  msg" + initialiseRideJson.toString())
//
//        }
//
//private fun addOnScrollListener() {
//        chat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        super.onScrolled(recyclerView, dx, dy)
//        val visibleItemCount = layoutManager.childCount
//        val totalItemCount = layoutManager.itemCount
//        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//        if (firstVisibleItemPosition == 0) {
//        if (isPagingEnabled && !isLoading) {
//        isLoading = true
////                        offset = offset + 30
//        getChatList()
//        }
//        }
//        }
//        })
//        }
//
//        override fun onStop() {
//        super.onStop()
//        Log.e("browse_page_err", "" + "get  msg disconnectUser")
//
//        socketInstance.removeListener("disconnectUser")
//        }
//
//private fun performCrop(picUri: Uri) {
//        try {
//        this.grantUriPermission(
//        "com.android.camera", picUri,
//        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
//        )
//        val intent = Intent("com.android.camera.action.CROP")
//        intent.setDataAndType(picUri, "image/*")
//        //Android N need set permission to uri otherwise system camera don't has permission to access file wait crop
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//        intent.putExtra("crop", "true")
//        //The proportion of the crop box is 1:1
//        intent.putExtra("aspectX", 1)
//        intent.putExtra("aspectY", 1)
//        //Crop the output image size
//        intent.putExtra("outputX", 1024)
//        intent.putExtra("outputY", 1024)
//        //image type
//        intent.putExtra("outputFormat", "JPEG")
//        intent.putExtra("noFaceDetection", true)
//        //true - don't return uri |  false - return uri
//        intent.putExtra("return-data", true)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri)
//        startActivityForResult(intent, PIC_CROP)
//
//        } catch (anfe: ActivityNotFoundException) {
//        val errorMessage = "Whoops - your device doesn't support the crop action!"
//        val toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
//        toast.show()
//        }
//        // respond to users whose devices do not support the crop action
//        }
//
//private fun performCropGallery(picUri: Uri, returnType: Int) {
//        try {
//        try {
//        val cropIntent = Intent("com.android.camera.action.CROP")
//        //indicate image type and Uri
//        cropIntent.setDataAndType(picUri, "image/*")
//        //set crop properties
//        cropIntent.putExtra("crop", "true")
//        //indicate aspect of desired crop
//
//        cropIntent.putExtra("aspectX", 2)
//        cropIntent.putExtra("aspectY", 2)
//        //indicate output X and Y
//        cropIntent.putExtra("outputX", 1024)
//        cropIntent.putExtra("outputY", 1024)
//        val fileImage =
//        File(Environment.getExternalStorageDirectory(), "/temporary_holder1.jpg")
//        fileImage.delete()
//        f = File(Environment.getExternalStorageDirectory(), "/temporary_holder1.jpg")
//        try {
//        f.createNewFile()
//        } catch (ex: IOException) {
//        ex.message?.let { Log.e("io", it) }
//        }
//
//        val uri = Uri.fromFile(f)
//        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
//        startActivityForResult(cropIntent, returnType)
//        } catch (e: Exception) {
//        e.printStackTrace()
//        }
//
//
//        } catch (anfe: ActivityNotFoundException) {
//        val errorMessage = R.string.cropOptionError
//        val toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
//        toast.show()
//        }
//
//        }
//
//        fun getRealPathFromURI(contentUri: Uri): String {
//
//        // can post image
//        val proj = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = this.managedQuery(
//        contentUri,
//        proj, // WHERE clause selection arguments (none)
//        null, null, null
//        )// Which columns to return
//        // WHERE clause; which rows to return (all rows)
//        // Order-by clause (ascending by name)
//        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        cursor.moveToFirst()
//
//        return cursor.getString(column_index)
//        }
//
//private fun storeImageTosdCard(processedBitmap: Bitmap): String {
//        try {
//        // TODO Auto-generated method stub
//
//        val output: OutputStream
//        // Find the SD Card path
//        val filepath = Environment.getExternalStorageDirectory()
//        // Create a new folder in SD Card
//        val dir = File(filepath.absolutePath + "/app/")
//        dir.mkdirs()
//
//        val imge_name = "sis" + ".jpg"
//        // Create a name for the saved image
//        val file = File(dir, imge_name)
//        if (file.exists()) {
//        file.delete()
//        file.createNewFile()
//        } else {
//        file.createNewFile()
//        }
//        try {
//        output = FileOutputStream(file)
//        // Compress into png format image from 0% - 100%
//        processedBitmap
//        .compress(Bitmap.CompressFormat.PNG, 100, output)
//        output.flush()
//        output.close()
//
//        val file_size = Integer
//        .parseInt((file.length() / 1024).toString())
//        println("size ===>>> $file_size")
//        println("file.length() ===>>> " + file.length())
//
//        selectedImagePath = file.absolutePath
//        } catch (e: Exception) {
//        // TODO Auto-generated catch block
//        e.printStackTrace()
//        }
//
//        } catch (e: Exception) {
//        // TODO Auto-generated catch block
//        e.printStackTrace()
//        }
//
//        uploadApi(selectedImagePath)
//
//        return selectedImagePath
//        }
//
//private fun uploadApi(selectedImagePath: String) {
////        if (isNetworkAvailable()) {
////            /*  var map = HashMap<String, okhttp3.RequestBody>()
////              map.put("type", MultiPartHelperClass.getRequestBody(msg_type))*/
////
////            Coroutines.main {
////                try {
//////                    val result = ApiCalls()?.uploadMedia(
//////                            SharedPrefManager.getInstance(this)?.getString(
//////                                    AppConstant.TOKEN,
//////                                    ""
//////                            )!!,
//////                            MultiPartHelperClass.getRequestBody(msg_type),
//////                            MultiPartHelperClass.getMultipartData(File(selectedImagePath), "media")
//////                    )
////                    if (result?.isSuccessful == true) {
////                        if (result.body()?.statusCode == 200) {
////                            file_url = result.body()!!.result.url
////                            thumb_url = result.body()!!.result.thumb_url
////                            sendMessage()
////                        } else {
////                            runOnUiThread(object : Runnable {
////                                override fun run() {
////                                    Toast.makeText(
////                                            applicationContext,
////                                            result.body()?.statusMessage,
////                                            Toast.LENGTH_LONG
////                                    ).show()
////
////                                }
////
////                            })
////                        }
////
////                    } else {
////                        runOnUiThread(object : Runnable {
////                            override fun run() {
////                                Toast.makeText(
////                                        applicationContext,
////                                        R.string.error_generic,
////                                        Toast.LENGTH_LONG
////                                ).show()
////                            }
////                        })
////                    }
////
////                } catch (e: Exception) {
////
////                }
////            }
////
////        } else {
////            runOnUiThread(object : Runnable {
////                override fun run() {
////                    Toast.makeText(applicationContext, R.string.nointernet_text_validation, Toast.LENGTH_LONG)
////                            .show()
////                }
////            })
////        }
//        }
//
//        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        try {
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//        var result = CropImage.getActivityResult(data);
//        if (resultCode == AppCompatActivity.RESULT_OK) {
//        var resultUri = result.getUri()
//
//        var bitmap =
//        MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
//        var stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
//        var byteArray = stream.toByteArray();
//        // RotateBitmap.convertPDF(byteArray);
//        try {
//        val defaultFile =
//        File(Environment.getExternalStorageDirectory().absolutePath + "/Syn")
//
//        if (!defaultFile.exists())
//        defaultFile.mkdirs()
//        Log.v("LOG_RESPONCE", "" + "${defaultFile.absolutePath.toString()}")
//
//        var timeStamp =
//        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(
//        Date()
//        )
//
//        val filename = "IMG_" + timeStamp + ".png"
//
//        var file = File(defaultFile, filename)
//        if (file.exists()) {
//        file.delete()
//        file = File(defaultFile, filename)
//        }
//
//        val output = FileOutputStream(file)
//        bitmap!!.compress(Bitmap.CompressFormat.PNG, 50, output)
//        output.flush()
//        output.close()
//        val imgFile = File("${defaultFile.absolutePath}/$filename")
//        Log.v("LOG_RESPONCE", "" + "${imgFile.absolutePath.toString()}")
//
//        if (imgFile.exists()) {
//
//
//        val requestFile =
//        RequestBody.create(MediaType.parse("image/png"), imgFile)
//        var fileToUpload: MultipartBody.Part
//
//        fileToUpload = MultipartBody.Part.createFormData(
//        "media",
//        imgFile.getName(),
//        requestFile
//        )
//        AppConstants.upload_pic = fileToUpload
//        Log.e("image==", "" + AppConstants.upload_pic)
//        uploadfileOnserver()
//
//
//        }
//
//        } catch (e: Exception) {
//        e.printStackTrace()
//        }
//        // Toast.makeText(activity!!,"hello"+bitmap,Toast.LENGTH_SHORT).show()
//        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//        var error = result.getError()
//        }
//        } else {
//        val uri = data?.data
//        val uriString = uri.toString()
//        val myFile = File(uriString)
//        //  generateImageFromPdf(uri)
//        val path: String = getFilePathFromURI(this, uri)
//        Log.d("ioooo", path)
//        Log.d("ioooo", "" + getFileName(uri)!!)
//        //uploadPDF(path)
//
//        // invoiceName!!.setText(path.toString())
//
//
//        //Create a file object using file path
//        val file = File(path)
//        // Parsing any Media type file
//        // Parsing any Media type file
//        val requestBody =
//        RequestBody.create(MediaType.parse("/"), file)
//        val fileToUpload =
//        MultipartBody.Part.createFormData("media", file.name, requestBody)
//
//        AppConstants.upload_pic = fileToUpload
//
//        uploadfileOnserver()
//        }
//
//        } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//        }
//
//
//        }
//
//        fun getFilePathFromURI(
//        context: Context?,
//        contentUri: Uri?
//        ): String { //copy file and send new file path
//        val fileName = getFileName(contentUri)
//        val wallpaperDirectory =
//        File(Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
//        // have the object build the directory structure, if needed.
//        if (!wallpaperDirectory.exists()) {
//        wallpaperDirectory.mkdirs()
//        }
//        if (!TextUtils.isEmpty(fileName)) {
//        val copyFile =
//        File(wallpaperDirectory.toString() + File.separator + fileName)
//        // create folder if not exists
//        copy(this, contentUri, copyFile)
//        return copyFile.absolutePath
//        }
//        return null!!
//        }
//
//        fun getFileName(uri: Uri?): String? {
//        if (uri == null) return null
//        var fileName: String? = null
//        val path = uri.path
//        val cut = path!!.lastIndexOf('/')
//        if (cut != -1) {
//        fileName = path.substring(cut + 1)
//        }
//        return fileName
//        }
//
//
//        fun copy(
//        context: Context,
//        srcUri: Uri?,
//        dstFile: File?
//        ) {
//        try {
//        val inputStream =
//        context.contentResolver.openInputStream(srcUri!!) ?: return
//        val outputStream: OutputStream = FileOutputStream(dstFile)
//        copystream(inputStream, outputStream)
//        inputStream.close()
//        outputStream.close()
//        } catch (e: IOException) {
//        e.printStackTrace()
//        } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//        }
//        }
//
//@Throws(java.lang.Exception::class, IOException::class)
//    fun copystream(input: InputStream?, output: OutputStream?): Int {
//            val buffer = ByteArray(BUFFER_SIZE)
//            val `in` =
//            BufferedInputStream(input, BUFFER_SIZE)
//            val out =
//            BufferedOutputStream(output, BUFFER_SIZE)
//            var count = 0
//            var n = 0
//            try {
//            while (`in`.read(buffer, 0, BUFFER_SIZE).also { n = it } != -1) {
//            out.write(buffer, 0, n)
//            count += n
//            }
//            out.flush()
//            } finally {
//            try {
//            out.close()
//            } catch (e: IOException) {
//            Log.e(e.message, e.toString())
//            }
//            try {
//            `in`.close()
//            } catch (e: IOException) {
//            Log.e(e.message, e.toString())
//            }
//            }
//            return count
//            }
//
//            fun parseDateToddMMyyyy(time: String): String? {
//            var ago = ""
//
//            val inputPattern = "yyyy-MM-dd hh:mm:ss"
//            val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
//            inputFormat.timeZone = TimeZone.getTimeZone("GMT")
//            try {
//            val time: Long = inputFormat.parse(time).time
//            val now = System.currentTimeMillis()
//            ago =
//            DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
//            .toString()
//            } catch (e: ParseException) {
//            e.printStackTrace()
//            }
//            return ago
//            }
//
//private fun requestStoragePermission() {
//        Dexter.withActivity(this)
//        .withPermissions(
//        Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//        Manifest.permission.CAMERA
//        )
//        .withListener(object : MultiplePermissionsListener {
//        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//        // check if all permissions are granted
//        if (report.areAllPermissionsGranted()) {
//        viewMenu()
//        }
//        // check for permanent denial of any permission
//        if (report.isAnyPermissionPermanentlyDenied) {
//        // show alert dialog navigating to Settings
//        }
//        }
//
//        override fun onPermissionRationaleShouldBeShown(
//        permissions: List<PermissionRequest>,
//        token: PermissionToken
//        ) {
//        token.continuePermissionRequest()
//        }
//        }).withErrorListener {
//        Toast.makeText(
//        applicationContext,
//        R.string.cropOptionError,
//        Toast.LENGTH_SHORT
//        ).show()
//        }
//        .onSameThread()
//        .check()
//        }
//
//        override fun onTouchEvent(event: MotionEvent?): Boolean {
//        return true
//        }
//
//private fun viewMenu() {
//        if (!isOpen) {
//        val x: Int = layoutContent.right
//        val y: Int = layoutContent.bottom
//        val startRadius = 0
//        val endRadius =
//        Math.hypot(layoutMain.width.toDouble(), layoutMain.height.toDouble())
//        .toInt()
//        attachement.backgroundTintList = ColorStateList.valueOf(
//        ResourcesCompat.getColor(
//        resources,
//        R.color.white,
//        null
//        )
//        )
//        val anim: Animator = ViewAnimationUtils.createCircularReveal(
//        layoutButtons,
//        x,
//        y,
//        startRadius.toFloat(),
//        endRadius.toFloat()
//        )
//        layoutButtons.visibility = View.VISIBLE
//        anim.start()
//        isOpen = true
//        } else {
//        val x = layoutButtons.right
//        val y = layoutButtons.bottom
//        val startRadius: Int =
//        Math.max(layoutContent.width, layoutContent.height)
//        val endRadius = 0
//        attachement.backgroundTintList = ColorStateList.valueOf(
//        ResourcesCompat.getColor(
//        resources,
//        R.color.colorAccent,
//        null
//        )
//        )
//        val anim: Animator = ViewAnimationUtils.createCircularReveal(
//        layoutButtons,
//        x,
//        y,
//        startRadius.toFloat(),
//        endRadius.toFloat()
//        )
//        anim.addListener(object : Animator.AnimatorListener {
//        override fun onAnimationStart(animator: Animator?) {}
//        override fun onAnimationEnd(animator: Animator?) {
//        layoutButtons.visibility = View.GONE
//        }
//
//        override fun onAnimationCancel(animator: Animator?) {}
//        override fun onAnimationRepeat(animator: Animator?) {}
//        })
//        anim.start()
//        isOpen = false
//        }
//        }
//
////    fun onLogEvents(eventName: String, bundle: Bundle?) {
////        val firebaseAnalytics = Firebase.analytics
////        bundle?.putString(
////                FirebaseEventsParam.chatToUserId, r_id.toString()
////        )
////        bundle?.putString(
////                FirebaseEventsParam.chatToUserName, tvHeader.text.toString()
////        )
////        bundle?.putString(
////                FirebaseEventsParam.loggedInUserId,
////                SharedPrefManager.getInstance(this)?.getInt(AppConstant.USER_ID, 0)
////                        .toString()
////        )
////        bundle?.putString(
////                FirebaseEventsParam.loggedInUserName,
////                SharedPrefManager.getInstance(this)?.getString(AppConstant.COMPLETENAME, "")
////                        .toString()
////        )
////        firebaseAnalytics.logEvent(eventName, bundle)
////    }
//
//
//private fun onlineStatus() {
//        // onAddListeners()
//        //initializeSocket()
//
//        Log.e(
//        "browse_page_err",
//        "" + "t.message111------" + sharedprferencesDetails!!.getString(SharedprferencesDetails.patientNo)!!
//        )
//
//        val initialiseRideJson = JSONObject()
//        try {
//        initialiseRideJson.put(
//        "user_id", sharedprferencesDetails!!.getString(SharedprferencesDetails.patientNo)!!
//        )
//        initialiseRideJson.put(
//        "user_type", "user"
//        )
//        } catch (e: JSONException) {
//        e.printStackTrace()
//        }
//        socketInstance.sendMsg("addUser", initialiseRideJson)
//        Log.e("browse_page_err", "" + "t.addUser")
//        // getDataWithApi()
//
//        Handler().postDelayed(
//        {
//        getChatList()
//
//        }, 1800
//        )
//        }
//
//        fun uploadfileOnserver() {
////        CommanUtils.showCustomSnackBar(
////            editText,
////            this@ChatActivity,
////            AppConstants.upload_pic.toString()
////        )
//        progress_bar!!.visibility=View.VISIBLE
//        val call2 =
//        WebServiceClient.client.create(BackEndApi::class.java)
//        .uploadChatMedia(DateFormated.convertoRequestBodey("en"), AppConstants.upload_pic)
//        call2.enqueue(object : Callback<JsonObject> {
//        override fun onResponse(
//        call: Call<JsonObject>,
//        response: Response<JsonObject>
//            ) {
//                    Log.e("browse_page_err", "" + response.toString())
//                    Log.e("browse_page_err", "" + response.body().toString())
//                    progress_bar!!.visibility=View.GONE
////                CommanUtils.showCustomSnackBar(
////                    editText,
////                    this@ChatActivity,
////                    response.body().toString()
////                )
//
//                    if (!response.isSuccessful) {
//                    //    val jObjError = JSONObject(response.errorBody()!!.string())
//
//                    } else {
//                    val gson =
//                    JsonParser().parse(response.body().toString()).asJsonObject
//                    val jsonObj = JSONObject(gson.toString())
//
//                    Log.e("browse_page_err", "" + jsonObj.getString("media_name"))
//
//                    val initialiseRideJson = JSONObject()
//                    try {
//                    initialiseRideJson.put(
//                    "sender_id",
//                    sharedprferencesDetails!!.getString(SharedprferencesDetails.patientNo)!!
//                    )
//                    initialiseRideJson.put("receiver_id", recieverId)
//
//                    initialiseRideJson.put("message", "")
//                    initialiseRideJson.put("user_type", "user")
//                    initialiseRideJson.put("media", jsonObj.getString("media_name"))
//                    initialiseRideJson.put("appoint_id", AppConstants.appoint_id)
//                    initialiseRideJson.put("appointment_type", AppConstants.appointmentType)
//                    } catch (e: JSONException) {
//                    e.printStackTrace()
//                    }
//                    socketInstance.sendMsg("sendMessage", initialiseRideJson)
////                    CommanUtils.showCustomSnackBar(
////                        editText,
////                        this@ChatActivity,
////                        initialiseRideJson.toString()
////                    )
//                    }
//                    }
//
//                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//        call.cancel()
//        Log.e("responce==", t.toString())
//        }
//        })
//        }
//
//        fun getDataWithApi() {
//        val call2 =
//        WebServiceClient.client.create(BackEndApi::class.java)
//        .chatData(
//        sharedprferencesDetails!!.getString(SharedprferencesDetails.token)!!,
//        sharedprferencesDetails!!.getString(SharedprferencesDetails.patientNo)!!,
//        recieverId,
//        sharedprferencesDetails!!.getString(SharedprferencesDetails.language)!!
//        )
//
//        call2.enqueue(object : Callback<JsonObject> {
//        override fun onResponse(
//        call: Call<JsonObject>,
//        response: Response<JsonObject>
//            ) {
//                    if (!response.isSuccessful) {
//                    val jObjError = JSONObject(response.errorBody()!!.string())
//                    } else {
//                    val gson =
//                    JsonParser().parse(response.body().toString()).asJsonObject
//                    val jsonObj = JSONObject(gson.toString())
//
//                    Log.e("responce==", response.body().toString())
//                    Log.e("responce==", response.toString())
//                    Log.e("browse_page_err", "" + response.body().toString())
//                    Log.e("browse_page_err", "" + response.toString())
//
//                    }
//                    }
//
//                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//        call.cancel()
//        Log.e("responce==", t.toString())
//
//
//        }
//        })
//        }
//
//        override fun onDestroy() {
//        super.onDestroy()
//        socketInstance.disConnect()
//        }
//
//
//        fun callingRequest(type: String) {
//        val call2 =
//        WebServiceClient.client.create(BackEndApi::class.java)
//        .requestAudioVideo(
//        sharedprferencesDetails!!.getString(SharedprferencesDetails.token)!!,
//        recieverId,
//        type,
//        sharedprferencesDetails!!.getString(SharedprferencesDetails.language)!!
//        )
//
//        call2.enqueue(object : Callback<JsonObject> {
//        override fun onResponse(
//        call: Call<JsonObject>,
//        response: Response<JsonObject>
//            ) {
//                    if (!response.isSuccessful) {
//                    val jObjError = JSONObject(response.errorBody()!!.string())
//                    } else {
//                    val gson =
//                    JsonParser().parse(response.body().toString()).asJsonObject
//                    val jsonObj = JSONObject(gson.toString())
//
//                    Log.e("responce==", response.body().toString())
//                    Log.e("responce==", response.toString())
//                    CommanUtils.showCustomSnackBar(
//                    editText,
//                    this@ChatActivity,
//        jsonObj.getString("APICODERESULT")
//        )
//
//
//        }
//        }
//
//        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//        call.cancel()
//        Log.e("responce==", t.toString())
//
//
//        }
//        })
//        }
//
//        }