package com.nepplus.apifinalpractice_20210501.datas

import org.json.JSONObject
import java.io.Serializable
import java.util.ArrayList

class Project(
        var id : Int,
        var title : String,
        var imageUrl : String,
        var description : String,
        var onGoingUesrCount : Int,
        var proofMethod : String,
        var myLastStatus : String) : Serializable{

  //태그 목록을 저장하기 위한 ArrayList를 하나 만들어 준다. -> 멤버변수 추가

    val tags = ArrayList<String>()

  //보조생성자 추가 - Project만으로도 만들 수 있게

    constructor() : this(0, "", "", "", 0,"","")


   //기능을 넣으면 json을 추가

    companion object{

        fun getProjectFromJson(jsonObj : JSONObject) : Project{

            val project = Project()

            // jsonObj에서 정보 추출 => project의 하위 항목 반영

            project.id = jsonObj.getInt("id")
            project.title = jsonObj.getString("title")
            project.imageUrl = jsonObj.getString("img_url")
            project.description = jsonObj.getString("description")
            project.onGoingUesrCount = jsonObj.getInt("ongoing_users_count")
            project.proofMethod = jsonObj.getString("proof_method")

//            내 최종 도전 상태 -> null일 상태일 가능성도 있따
//            null인 데이터를 파싱하려고 하면 -> 에러처리 파싱 중단
//            null인지 아닌지 확인

            if(!jsonObj.isNull("my_last_status")){

                project.myLastStatus = jsonObj.getString("my_last_status")
            }



//            태그 목록으로 같이 되어있다. jsonArray -> 태그 추가목록으로 파싱

            val tagsArr = jsonObj.getJSONArray("tags")

//            for문을 이용해서 하나씩 반복적으로 파싱한다. 추가로 목록에 담는 것 까지 한다.
            for(index in 0 until tagsArr.length()){

                val tagObj = tagsArr.getJSONObject(index)

                val title = tagObj.getString("title")

                project.tags.add(title)

            }



//           완성된 project가 결과로 나이도록

            return project


        }
    }

}