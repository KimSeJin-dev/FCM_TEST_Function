package so.ido.fcm_test_1015.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import so.ido.fcm_test_1015.R


class GetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_acticity)

        val userIdGet = intent.getStringExtra("userId")
        val userPwGet = intent.getStringExtra("userPw")
        val yourTokenGet = intent.getStringExtra("yourToken")
        val myTokenGet = intent.getStringExtra("myToken")

        Log.d("TAG", "userIdGet : $userIdGet")
        Log.d("TAG", "userPwGet : $userPwGet")
        Log.d("TAG", "yourTokenGet : $yourTokenGet")
        Log.d("TAG", "myTokenGet : $myTokenGet")

        // 포크라운드에서 푸시 알림 클릭시
        if(intent.hasExtra("userId") && intent.hasExtra("userPw") && intent.hasExtra("yourToken") && intent.hasExtra("myToken")) {
            Toast.makeText(this, "ID : " + userIdGet + "\n"
                    + "Password : " + userPwGet + "\n"
                    + "yourToken : " + yourTokenGet + "\n"
                    + "myToken : " + myTokenGet + "\n", Toast.LENGTH_LONG).show()
        }

        // 백그라운드에서 푸시 알림 클릭시
        else {
            val bundle: Bundle? = intent.extras
            if(bundle != null) {
                Toast.makeText(this, "ID : " + bundle.getString("user_id") + "\n"
                        + "Password : " + bundle.getString("user_pw") + "\n"
                        + "yourToken : " + bundle.getString("yourToken") + "\n"
                        + "myToken : " + bundle.getString("myToken") + "\n", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "데이터를 가져오는데 실패했습니다. ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}