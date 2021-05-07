package com.nepplus.apifinalpractice_20210501.datas

import org.json.JSONObject

class Proof {

    var id = 0
    var content = ""

    companion object{


//        멤버변수들만 추가

//        json한 덩어리 -> Proof한 덩어리 변환 가능

        fun getProofFromJson(jsonObj : JSONObject) : Proof{

            val proof = Proof()


//            json항목들 -> proof 변수들에 대입
            proof.id = jsonObj.getInt("code")
            proof.content = jsonObj.getString("content")

            return proof
        }

    }
}