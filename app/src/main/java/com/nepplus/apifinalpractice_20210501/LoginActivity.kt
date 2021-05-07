package com.nepplus.apifinalpractice_20210501

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.nepplus.apifinalpractice_20210501.utils.ContextUtil
import com.nepplus.apifinalpractice_20210501.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


//객체화를 시키지 않는다.
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

        //sharedPreference어떻게 되어잇는지 체크, 해제
        autoLoginCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

            ContextUtil.setAutoLogin(mContext, isChecked)
        }

        signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)

            startActivity(myIntent)


        }


        loginBtn.setOnClickListener {

            //입력한 이메일, 비번을 추출한다.
            val inputEmail = emailEdt.text.toString()
            val inputPassword = passwordEdt.text.toString()


            //서버에 로그인 요청

            ServerUtil.postReqeustLogin(inputEmail, inputPassword, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

                    val codeNum = jsonObj.getInt("code")

                    if (codeNum == 200) {

                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")

                        val nickname = userObj.getString("nick_name")

                        //서버에 내려두는 토큰값을 추출한다.
                        val token = dataObj.getString("token")

                        //sharedPreferences활용(영구저장)
                        ContextUtil.setLoginToken(mContext, token)

                        runOnUiThread {

                            Toast.makeText(mContext, "${nickname}님 환영합니다", Toast.LENGTH_SHORT).show()

                            val myIntent = Intent(mContext, MainActivity::class.java)

                            startActivity(myIntent)

                            finish()

                        }
                    }

                    else {


                        val message = jsonObj.getString("message")

                        runOnUiThread {

                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }

                    }




                }

            })


        }


    }

    override fun setValues() {

        autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)

    }
}