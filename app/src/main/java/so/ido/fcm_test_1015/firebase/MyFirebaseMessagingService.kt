package so.ido.fcm_test_1015.firebase

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import so.ido.fcm_test_1015.R
import so.ido.fcm_test_1015.activity.GetActivity

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        // Check if message contains a data payload.
             //데이터 메시지인 경우
            if (p0.data.isNotEmpty())
            {
                val data = mapOf<String,String>("title" to p0.notification!!.title.toString()
                        ,"body" to p0.notification!!.body.toString()
                        ,"user_id" to p0.data.getValue("user_id")
                        ,"user_pw" to p0.data.getValue("user_pw")
                        ,"myToken" to p0.data.getValue("myToken")
                        ,"yourToken" to p0.data.getValue("yourToken"))

                sendNotification(data)

            }
            // 알림 메시지인 경우
            else if (p0.notification != null){
                Log.d("TAG", "sejin1")
                val remoteMessageData = mapOf<String,String>("title" to p0.data.getValue("title"),
                        "body" to p0.data.getValue("body"))
                sendNotification(remoteMessageData)
            }

    }


    private fun sendNotification(msgData: Map<String, String>){

        val intent = Intent(this, GetActivity::class.java)

        if(msgData.containsKey("user_id") && msgData.containsKey("user_pw") && msgData.containsKey("yourToken") && msgData.containsKey("myToken")) {
            intent.putExtra("userId", msgData.getValue("user_id"))
            intent.putExtra("userPw", msgData.getValue("user_pw"))
            intent.putExtra("yourToken", msgData.getValue("yourToken"))
            intent.putExtra("myToken", msgData.getValue("myToken"))
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_SERVICE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msgData.getValue("title"))
                .setContentText(msgData.getValue("body"))
                .setSound(defaultSound)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}






