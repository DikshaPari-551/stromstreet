package com.example.myapplication.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Activities.PostActivity2
import com.example.myapplication.Activities.UserProfile
import com.example.myapplication.R
import com.example.myapplication.util.SavedPrefManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson


const val channelId = "notification-channel"
const val channelName = "com.eazyalgo.fcmpushnotification"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.getNotification() != null) {
            var responce: String =
                remoteMessage.data.toString().replace("[", "").replace("]", "").replace("=", ":")
            val gson = Gson()
        //    val fcmResponse: FCMResponse = gson.fromJson(responce, FCMResponse::class.java)
//        generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)

            if (remoteMessage.data != null) {
                var value: Any?=null
                var postid: Any?=null
                var userId: Any?=null
                for (key in remoteMessage.data!!.keys) {
                   // value = remoteMessage.data!![key]
                    if (key.equals( "notificationType")) {
                        value =
                            remoteMessage.data!![key] // value will represend your message body... Enjoy It

                        Log.d("NotificationTag", key + "____" + value)
                    }
                    if (key.equals("postId")) {
                        postid =
                            remoteMessage.data!![key] // value will represend your message body... Enjoy It
                        SavedPrefManager.saveStringPreferences(baseContext, SavedPrefManager._id, postid.toString())
                       // Log.d("NotificationTag", key + "____" + value)
                    }
                   else if (key.equals("userId")) {
                        userId =
                            remoteMessage.data!![key] // value will represend your message body... Enjoy It
                        SavedPrefManager.saveStringPreferences(baseContext, SavedPrefManager.otherUserId, userId.toString())
                        // Log.d("NotificationTag", key + "____" + value)
                    }
                }
                getRemoteView(value,postid)
            }
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            SavedPrefManager.saveStringPreferences(
                applicationContext,
                SavedPrefManager.KEY_DEVICE_TOKEN,
                token
            )

        })

    }

    fun getRemoteView(value: Any?, postid: Any?) {

        var intent: Intent? = null
        var pendingIntent: PendingIntent? = null
        if (value != null) {
            val notifyImage = BitmapFactory.decodeResource(resources, R.drawable.splash_screen)
            val CHANNEL_ID = "my_channel_01"

            when(value.toString()){
                "LIKE_POST"->
                {
                    intent = Intent(this, PostActivity::class.java)
                    pendingIntent = PendingIntent.getActivity(
                        applicationContext,
                        0,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                }
                "COMMENT"->   {
                    intent = Intent(this, PostActivity2::class.java)
                    pendingIntent = PendingIntent.getActivity(
                        applicationContext,
                        0,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                }
                "LIKE_COMMENT"->   {
                    intent = Intent(this, PostActivity2::class.java)
                    pendingIntent = PendingIntent.getActivity(
                        applicationContext,
                        0,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                }
                "FOLLOW"->{
                    intent = Intent(this, UserProfile::class.java)
                    pendingIntent = PendingIntent.getActivity(applicationContext,0,intent,PendingIntent.FLAG_ONE_SHOT)

                }

            }
            val notificationId: Int = SavedPrefManager.getIntPreferences(this, SavedPrefManager.NOTIFICATION_ID)

            val notification: Notification
            if (Build.VERSION.SDK_INT >= 26) {
                //This only needs to be run on Devices on Android O and above
                val mNotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val id = "YOUR_CHANNEL_ID"
                val name: CharSequence = "YOUR_CHANNEL NAME" //user visible
                val description = "YOUR_CHANNEL_DESCRIPTION" //user visible
                val importance = NotificationManager.IMPORTANCE_MAX
                @SuppressLint("WrongConstant") val mChannel =
                    NotificationChannel(id, name, importance)
                mChannel.description = description
                mChannel.enableLights(true)
                mChannel.enableVibration(true)
                mChannel.canShowBadge()
                mChannel.setShowBadge(true)
                mChannel.vibrationPattern = longArrayOf(0, 1000)
                mNotificationManager.createNotificationChannel(mChannel)
                notification = Notification.Builder(applicationContext, "YOUR_CHANNEL_ID")
                    .setSmallIcon(R.drawable.splash_screen)
                    .setContentTitle(getString(R.string.app_name))
                    .setTicker(getString(R.string.app_name))
                    .setLargeIcon(notifyImage)
                    //.setContentText(fcmResponse.body)
                    .setAutoCancel(true) // .setLargeIcon(Bitmap.createScaledBitmap(notifyImage, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(false).build()
                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                if (notificationManager != null) {
                    notificationManager.notify(notificationId, notification)
                    SavedPrefManager.saveIntPreferences(this, SavedPrefManager.NOTIFICATION_ID,notificationId + 1)

                }
            }
                        else {
                val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.splash_screen)
                    .setContentTitle(getString(R.string.app_name))
                    .setLargeIcon(notifyImage)
                   // .setContentText(fcmResponse.body)
                   // .setStyle(NotificationCompat.BigTextStyle().bigText(fcmResponse.body)
                 .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setAutoCancel(true)
                notificationBuilder.setContentIntent(pendingIntent)
                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                if (notificationManager != null) {
                    notificationManager.notify(notificationId, notificationBuilder.build())
                            SavedPrefManager.saveIntPreferences(this, SavedPrefManager.NOTIFICATION_ID,notificationId + 1

                    )
                }
            }
        }


        }

//        fun generateNotification(title: String, message: String) {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//
//            val pendingIntent =
//                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//
//            var builder: NotificationCompat.Builder = NotificationCompat.Builder(
//                applicationContext,
//                channelId
//            )
//                .setSmallIcon(R.drawable.splash_screen)
//                .setAutoCancel(true)
//                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
//                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent)
//
//            builder = builder.setContent(getRemoteView(title, message))
//            val notificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val notificationChannel =
//                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
//                notificationManager.createNotificationChannel(notificationChannel)
//            }
//            notificationManager.notify(0, builder.build())
//
//
//        }

    }

