package com.nepplus.apifinalpractice_20210501

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nepplus.apifinalpractice_20210501.datas.Project
import com.nepplus.apifinalpractice_20210501.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_project_detail.*
import org.json.JSONObject

class ViewProjectDetailActivity : BaseActivity() {

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        viewProofBtn.setOnClickListener {

            val myIntent = Intent(mContext, ViewProofByDataActivity::class.java)

            myIntent.putExtra("project", mProject)

            startActivity(myIntent)
        }

        giveUpBtn.setOnClickListener {

//            서버에 포기 의사 전달

            ServerUtil.deleteRequestGiveUpProject(mContext, mProject.id, object : ServerUtil.JsonResponseHandler {

                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")

                    if (code == 200) {

                        ServerUtil.getRequestProjectDetail(mContext, mProject.id, object : ServerUtil.JsonResponseHandler{

                            override fun onResponse(jsonObj: JSONObject) {

                                val dataObj = jsonObj.getJSONObject("data")
                                val projectObj = dataObj.getJSONObject("project")

                                mProject = Project.getProjectFromJson(projectObj)

                                runOnUiThread {
                                    refreshDataToUi()
                                }

                            }

                        })



                    } else {

                        runOnUiThread {

                            Toast.makeText(mContext, "실패하였습니다. ", Toast.LENGTH_SHORT).show()
                        }


                    }
                }

            })

        }

        applyBtn.setOnClickListener {

//            서버 참여 처리
            ServerUtil.postReqeustApplyProject(mContext, mProject.id, object : ServerUtil.JsonResponseHandler {

                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")

                    if (code == 200) {

                        val dataObj = jsonObj.getJSONObject("data")
                        val projectObj = dataObj.getJSONObject("project")

                        mProject = Project.getProjectFromJson(projectObj)

                        runOnUiThread {
                            refreshDataToUi()
                        }

                    } else {
                        runOnUiThread {
                            Toast.makeText(mContext, "참여 신청에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })


        }

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("projectinfo") as Project
        refreshDataToUi()

    }

    fun refreshDataToUi(){

        Glide.with(mContext).load(mProject.imageUrl).into(projectImg)
        titleText.text = mProject.title
        descriptionTxt.text = mProject.description

        userCountTxt.text = "${mProject.onGoingUesrCount}명"
        proofMethodTxt.text = mProject.proofMethod

//        태그 목록은 -> 몇 개일지 매번 다르다.
//        빈 layout을 불러내서 -> 기존에 텍스트뷰 모두 삭제하고 -> 태그 갯수만큼 텍스트뷰를 추가

        tagListLayout.removeAllViews()

        for(tag in mProject.tags){

            Log.d("프로젝트태그", tag)

            val tagTextView = TextView(mContext)
            tagTextView.text = "#${tag}"
            tagTextView.setTextColor(Color.BLUE)

            tagListLayout.addView(tagTextView)

        }


        if(mProject.myLastStatus == "ONGOING"){

            giveUpBtn.visibility = View.VISIBLE
            applyBtn.visibility = View.GONE
        }

        else{
            giveUpBtn.visibility = View.GONE
            giveUpBtn.visibility = View.VISIBLE
        }
    }
}