package com.nepplus.apifinalpractice_20210501

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.nepplus.apifinalpractice_20210501.adpaters.ProjectAdapter
import com.nepplus.apifinalpractice_20210501.datas.Project
import com.nepplus.apifinalpractice_20210501.utils.ContextUtil
import com.nepplus.apifinalpractice_20210501.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjects = ArrayList<Project>()

    lateinit var mProjectAdapter : ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        logoutBtn.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
//            확인을 누르면 할 일의 코드를 작성을 해야한다.
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
//          실행할 코드
//                로그인 : 아이디 / 비밀번호 서버전달 -> 회원이 맞는 지 전달 -> 성공시 토큰 값을 전달 -> 앱에서 토큰을 저장
//                 로그아웃 -> 기기에 저장된 토큰 값을 삭제

            ContextUtil.setLoginToken(mContext,"") //토큰을 빈 칸으로 맞추어준다

            val myIntent =  Intent(mContext, LoginActivity::class.java)

            startActivity(myIntent)

            finish()

            })

            alert.setNegativeButton("취소", null)
            alert.show()
        }

    }

    override fun setValues() {
//서버에서 -> 보여줄 프로젝트 목록이 어떤것들이 있는지를 받아서 project로 변환해서 추가

        projectListView.setOnItemClickListener { parent, view, position, id ->

           val clickedProject = mProjects[position]

           val myIntent = Intent(mContext, ViewProjectDetailActivity::class.java)

           myIntent.putExtra("projectinfo", clickedProject)

           startActivity(myIntent)
        }

        getProjectListFromServer()

        mProjectAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjects)
        projectListView.adapter = mProjectAdapter

    }

    fun getProjectListFromServer(){

           ServerUtil.getProjectList(mContext, object : ServerUtil.JsonResponseHandler{
               override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")

                val projectArr = dataObj.getJSONArray("projects")

// 반복 for문으로 => projectarr을 하나 씩 꺼내서 파싱을 한다.
                for(i in 0 until projectArr.length()){

//[] -> { }을 하나씩 꺼내서 파싱을한다.
                   val projectObj = projectArr.getJSONObject(i)

                   val project = Project.getProjectFromJson(projectObj)

 //-> project클래스에 보조생성자를 하나씩 추가를 한다
 //->project클래스에 함수(기능) 추가 -> JSONObject를 넣으면은 -> project형태로 변환해주는 긴으
                   mProjects.add(project)

                }
//어댑터 데이터가 바뀌었기 떄문에 notifyDatasetChanged활용한다.
                runOnUiThread {
                    mProjectAdapter.notifyDataSetChanged()
                }

               }


           })
    }



}