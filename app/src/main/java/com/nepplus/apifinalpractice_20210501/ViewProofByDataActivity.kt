package com.nepplus.apifinalpractice_20210501

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import com.nepplus.apifinalpractice_20210501.adpaters.ProofAdapter
import com.nepplus.apifinalpractice_20210501.datas.Project
import com.nepplus.apifinalpractice_20210501.datas.Proof
import com.nepplus.apifinalpractice_20210501.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_proof_by_data.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ViewProofByDataActivity : BaseActivity() {

    lateinit var mProject : Project

    var mSelectedDate = Calendar.getInstance()

    val mProofList = ArrayList<Proof>()

    lateinit var mProofAdapter : ProofAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_proof_by_data)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

        selectDateBtn.setOnClickListener {

            val dateSetlistener = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                    Log.d("선택된날짜", "${year}년 ${month}월 ${dayOfMonth}일")

                    mSelectedDate.set(year,month,dayOfMonth)

                    val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
                    dateTxt.text = simpleDateFormat.format(mSelectedDate.time)

//                    서버에서 선택된 날짜에 해당하는 글 불러오기
                    getProofListByDate()

                }


            }

//            실제로 달력 띄우기
            val datePickerDialog = DatePickerDialog(mContext, dateSetlistener,
                    mSelectedDate.get(Calendar.YEAR),
                    mSelectedDate.get(Calendar.MONTH),
                    mSelectedDate.get(Calendar.DAY_OF_MONTH))

                    datePickerDialog.show()



        }



    }

    override fun setValues() {

       mProject = intent.getSerializableExtra("project") as Project

       mProofAdapter = ProofAdapter(mContext, R.layout.proof_list_item, mProofList)
       proofListView.adapter = mProofAdapter


    }

//    서버에서, 선택된 날짜의 글을 받아와주는 함수

    fun getProofListByDate(){

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateStr = sdf.format(mSelectedDate.time)

        ServerUtil.getRequestProofListByDate(mContext, mProject.id, dateStr, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val projectObj = dataObj.getJSONObject("project")

                val proofsArr = projectObj.getJSONArray("proofs")

                for(i in 0 until proofsArr.length()){

                    val proofObj = proofsArr.getJSONObject(i)
//                    인증글Json -> proof객체로 변환 -> mProofList

                    mProofList.add(Proof.getProofFromJson(proofsArr.getJSONObject(i) ) )

                }

            }

        })

    }

}

