package com.collins.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.collins.medilabsapp.constants.Constants
import com.collins.medilabsapp.helpers.ApiHelper
import com.collins.medilabsapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val surname = findViewById<TextInputEditText>(R.id.surname)
        val password = findViewById<TextInputEditText>(R.id.password)

        val login = findViewById<MaterialButton>(R.id.login)
        login.setOnClickListener {
            val api = Constants.BASE_URL+"/member_signin"
            val helper = ApiHelper(applicationContext)
            val body = JSONObject()
            body.put("surname", surname.text.toString())
            body.put("password", password.text.toString())
            helper.post(api, body, object : ApiHelper.CallBack {
                override fun onSuccess(result: JSONArray?) {
                }
                //user: bob101
                //pass: Qwerty1234
                override fun onSuccess(result: JSONObject?) {
                    Toast.makeText(applicationContext, result.toString(),
                        Toast.LENGTH_SHORT).show()
                    //Consume the JSON - access keys
                    if (result!!.has("refresh_token")){
                        val refresh_token = result.getString("refresh_token")
                        val access_token = result.getString("access_token")
                        val message = result.getString("message")
                        Toast.makeText(applicationContext, "Success",
                            Toast.LENGTH_SHORT).show()
                        PrefsHelper.savePrefs(applicationContext,
                            "refresh_token", refresh_token)

                        PrefsHelper.savePrefs(applicationContext,
                            "access_token", access_token)

                        PrefsHelper.savePrefs(applicationContext,
                            "userObject", message)
                        //Proceed to Homepage
                    }
                    else {
                        Toast.makeText(applicationContext, result.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(result: String?) {
                }

            });
            //

        }//end listener
    }//end oncreate
}//end class