package so.ido.fcm_test_1015.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import so.ido.fcm_test_1015.R
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var myToken: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true


        getMyToken()
        button.setOnClickListener {

            val id = send_ID.text.toString()
            val password = send_Password.text.toString()
            //  var myToken ="" // Pixel_2_API_25
            val yourToken = "f3EOnmanRMOLayUPPsxtcP:APA91bFp00L4J9_DFEyvGx0wn-JrUoT7osUVOSjBspp71GZBh2wELz2XOn9J2lOVl8d34jFhmVTIFqgTdKRfS2E0o_NwSDe8Z0TwKqcn-sEUq5i1p_NWzuWttr36f9SjgR6jxKUhEDyj" // Pixel_2_API_25_2

            val data = mapOf<String, String>(
                    "user_id" to id, "user_pw" to password, "myToken" to myToken!!, "yourToken" to yourToken)
            // Emulator Pixel_2_API_25 =
            // e5EsHSJ0SD6QAteA3iQK_n:APA91bHH6PI4uBVO6BCLKQskiBqxvVOwltz7QCF4nMJb68jdYIDySQ5Sekqa6hCgGsgapTTIlqxvZ8m2PGrfJ6Jh9MXahJmN7Rs88wXcs3YpqngL1N06tb--pqZkEQ5FuQjyx-wazIaq

            // Emulator Pixel_2_API_25_2 =
            // f3EOnmanRMOLayUPPsxtcP:APA91bFp00L4J9_DFEyvGx0wn-JrUoT7osUVOSjBspp71GZBh2wELz2XOn9J2lOVl8d34jFhmVTIFqgTdKRfS2E0o_NwSDe8Z0TwKqcn-sEUq5i1p_NWzuWttr36f9SjgR6jxKUhEDyj

            Log.d("TAG", "data : $data")
            FirebaseFunctions.getInstance()
                    .getHttpsCallable("Notification")
                    .call(data)
                    .continueWith { task ->
                        Log.d("Tag", "result: ${task.result?.data}")
                        val dataFunction = task.result?.data as Map<*,*>
                        val result = dataFunction["result"]
                        result
                    }
                    .addOnSuccessListener { result ->
                        Toast.makeText(this, "로그인에 성공했습니다. ", Toast.LENGTH_SHORT).show()

                        Log.d("TAG", "search(): success $result")
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "로그인에 실패했습니다. 에러 원인 : ${exception.message} ", Toast.LENGTH_SHORT).show()

                        Log.d("TAG", "search(): exception $exception") // exception kotlin.TypeCastException: null cannot be cast to non-null type kotlin.String
                        Log.d("TAG", "search(): localMsg ${exception.localizedMessage}") // localMsg null cannot be cast to non-null type kotlin.String
                        Log.d("TAG", "search(): cause ${exception.cause}") // cause null
                        Log.d("TAG", "search(): msg ${exception.message}") // msg null cannot be cast to non-null type kotlin.String

                    }
        }
    }

    private fun getMyToken() {
        Thread {
            try {
                FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener(OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.i("TAG", "getInstanceId failed", task.exception)
                                return@OnCompleteListener
                            }
                            myToken = task.result?.token!!
                            Log.d("TAG", "Token is $myToken")
                        })
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

    }

}