package com.nepplus.apifinalpractice_20210501


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.nepplus.apifinalpractice_20210501.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject


class SignUpActivity : BaseActivity() {

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        //이메일 입력칸의 변경 -> 무조건 다시 검사하도록 문구 변경

        emailEdt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                emailCheckResultTxt.text = "이메일 중복을 확인해주세요"
            }

        })


        emailCheckBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getEmailCheck(inputEmail, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    val codeNum = jsonObj.getInt("code")

                    runOnUiThread {

                        if (codeNum == 200) {
                            emailCheckResultTxt.text = "사용해도 좋은 이메일입니다."
                        }

                        else{
                            emailCheckResultTxt.text = "중복된 이메일이라 사용할 수 없습니다."
                        }
                    }

                }

            })


        }

        signUpBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()
            val inputPassword = passwordEdt.text.toString()
            val inputNickname = nicknameEdt.text.toString()

            ServerUtil.putReqeustSignUp(inputEmail, inputPassword, inputNickname, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

                        val codeNum = jsonObj.getInt("code")

                        if (codeNum == 200) {
                            runOnUiThread {
                                Toast.makeText(mContext, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                            }
                        } else {

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

    }
}