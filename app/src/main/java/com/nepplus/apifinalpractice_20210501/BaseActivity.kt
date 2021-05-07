package com.nepplus.apifinalpractice_20210501

import androidx.appcompat.app.AppCompatActivity

//코드를 정리학 위한용도, 이벤트 처리 setupEvents
//ui처리 - setValues
//this를 써야 할때 고민 없이 mContext를 이용한다.

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()
}