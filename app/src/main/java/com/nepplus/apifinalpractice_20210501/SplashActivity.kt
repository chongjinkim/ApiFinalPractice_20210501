package com.nepplus.apifinalpractice_20210501

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nepplus.apifinalpractice_20210501.utils.ContextUtil

class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {
    //자동 로그인을 하는 상황인지 검사(질문)
    //1. 사용자가 자동 로그인 체크를 했는지
    //저장된 토큰 값이 있는지("")이 있는지 아닌지
    //둘다 만족하면 > 바로 메인으로 이동

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            val myIntent : Intent //나중에 intent를 넣는다.

             if(ContextUtil.getAutoLogin(mContext) && ContextUtil.getLoginToken(mContext) != ""){

//자동 로그인을 해도 된다. - MainActivity로 이동
                 myIntent = Intent(mContext, MainActivity::class.java)
             }

            else{
//로그인 화면으로 이동
                myIntent = Intent(mContext, LoginActivity::class.java)
            }

            startActivity(myIntent)
            finish()
        },2500)



    }
}