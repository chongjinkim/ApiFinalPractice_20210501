package com.nepplus.apifinalpractice_20210501.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    interface JsonResponseHandler{
        fun onResponse(jsonObj: JSONObject)
    }

    companion object{

        //이 중괄호 안에 변수 / 함수는
        //SererUtil자체의 변수 , 함수로 이용이 가능하다.
        //Static개념에 대응이 된다.

        //서버의 호스트 주소 저장

        val HOST_URL = "http://15.164.153.174"

        fun postReqeustLogin(email : String, password : String, handler : JsonResponseHandler?){

            //어디로 가야 할 지 주소를 먼저 선정한다.

            val urlString = "${HOST_URL}/user"

            //어떤 정보를 들고 가야 할지 설정을 한다 - FormData에 담아준다.
            //갈 떄 어떤 파라미터를 들고가야할지는 - post formData방식으로 진행
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build()

            //모든 정보 종합 + 어떤 함수를 들고 갈것인가 설정
            val reqeust = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

            //정리된 정보를 들고 실제 API요청을 진행한다

            //클라이언트로써 동작을 쉽게한느 요령을 정리한 클라이언트 - OkhttpClient
           val client = OkHttpClient()
            client.newCall(reqeust).enqueue(object : Callback{

                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    //서버의 응답을 받아내는데 성공한 경우
                    //응답 -> 내부의 본문만 활용 -> String형태로 저장
                    val bodyString = response.body!!.string()

//                    bodystring은 인코딩이 되어있는 상태라 -> 사람이 읽기가 어렵다
//                  bodyString은 jsonObject형태로 변환 시키면은 읽을 수 있다.

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    //                    우리가 완성해낸 jsonObj변수를 -> 액티비티에 넘겨주자 => 파싱 등의 처리는 액티비티에서 작성
                    handler?.onResponse(jsonObj)//가이드북 적혀있다면 실행


                }


            })

        }

        //회원가입 기능 추가

        fun putReqeustSignUp(email : String, password : String, nickname : String, handler : JsonResponseHandler?){

            val urlString = "${HOST_URL}/user"

            val formData = FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .add("nick_name", nickname)
                    .build()

            val request = Request.Builder()
                    .url(urlString)
                    .put(formData)
                    .build()


            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

        fun getEmailCheck(email : String, handler: JsonResponseHandler?){

            //어디로 + 어떤데이터로 => url을 만들 때 전부 한꺼번에 적어야 한다.
            //주소로 적는게 복잡할 예정 -> 호스트 주소 email/check

            val urlBuilder = "${HOST_URL}/email_check".toHttpUrlOrNull()!!.newBuilder()

            //만들어진 기초 url에 파라미터들을 붙여준다.
            urlBuilder.addEncodedQueryParameter("email", email)

            //붙일 정보는 다 붙였으면 String으로 최종 변환
            val urlString = urlBuilder.build().toString()

            Log.d("가공된URL", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }


            })



        }

        fun getProjectList(context : Context,handler : JsonResponseHandler?){

            val urlBuilder = "${HOST_URL}/project".toHttpUrlOrNull()!!.newBuilder()

            //            만들어진 기초 url에 필요한 파라미터들을 붙여주자
//            urlBuilder.addEncodedQueryParameter("email", email)

            val urlString = urlBuilder.build().toString()

            Log.d("가공된URL", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .header("X-Http-Token", ContextUtil.getLoginToken(context))
                    .build()


            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }

            })



        }

//        프로젝트에 참가 신청하기
        fun postReqeustApplyProject(context: Context, projectId : Int, handler : JsonResponseHandler?){

            //어디로 가야 할 지 주소를 먼저 선정한다.

            val urlString = "${HOST_URL}/project"

            //어떤 정보를 들고 가야 할지 설정을 한다 - FormData에 담아준다.
            //갈 떄 어떤 파라미터를 들고가야할지는 - post formData방식으로 진행
            val formData = FormBody.Builder()
                .add("project_id", projectId.toString()) //형변환 진행시애 -> toString, toInt진행
                .build()

            //모든 정보 종합 + 어떤 함수를 들고 갈것인가 설정
            val reqeust = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getLoginToken(context))
                .build()

            //정리된 정보를 들고 실제 API요청을 진행한다

            //클라이언트로써 동작을 쉽게한느 요령을 정리한 클라이언트 - OkhttpClient
            val client = OkHttpClient()
            client.newCall(reqeust).enqueue(object : Callback{

                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    //서버의 응답을 받아내는데 성공한 경우
                    //응답 -> 내부의 본문만 활용 -> String형태로 저장
                    val bodyString = response.body!!.string()

//                    bodystring은 인코딩이 되어있는 상태라 -> 사람이 읽기가 어렵다
//                  bodyString은 jsonObject형태로 변환 시키면은 읽을 수 있다.

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    //                    우리가 완성해낸 jsonObj변수를 -> 액티비티에 넘겨주자 => 파싱 등의 처리는 액티비티에서 작성
                    handler?.onResponse(jsonObj)//가이드북 적혀있다면 실행


                }


            })

        }

//        프로젝트 포기하기
        fun deleteRequestGiveUpProject(context : Context, projectId : Int, handler : JsonResponseHandler?){

            val urlBuilder = "${HOST_URL}/project".toHttpUrlOrNull()!!.newBuilder()

            //            만들어진 기초 url에 필요한 파라미터들을 붙여주자
            urlBuilder.addEncodedQueryParameter("project_id", projectId.toString())

            val urlString = urlBuilder.build().toString()

            Log.d("가공된URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .delete()
                .header("X-Http-Token", ContextUtil.getLoginToken(context))
                .build()


            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }

            })



        }

//        특정 프로젝트 상세보기 - API 추가
        fun getRequestProjectDetail(context : Context, projectId : Int, handler: JsonResponseHandler?){

            //어디로 + 어떤데이터로 => url을 만들 때 전부 한꺼번에 적어야 한다.
            //주소로 적는게 복잡할 예정 -> 호스트 주소 email/check

            val urlBuilder = "${HOST_URL}/project".toHttpUrlOrNull()!!.newBuilder()

//    몇 번 프로젝트를 볼건지 / 1 등으로 추가하자.
//    주소에 더 달라붙는 거 표현.
            urlBuilder.addEncodedPathSegment(projectId.toString())

            //만들어진 기초 url에 파라미터들을 붙여준다.
            //urlBuilder.addEncodedQueryParameter("email", email)

            //붙일 정보는 다 붙였으면 String으로 최종 변환
            val urlString = urlBuilder.build().toString()

            Log.d("가공된URL", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .header("X-Http-Token", ContextUtil.getLoginToken(context))
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }


            })



        }

//특정 프로젝트의 인증글을 날짜별로 받아오기
        fun getRequestProofListByDate(context : Context, projectId : Int, date : String, handler: JsonResponseHandler?){

            //어디로 + 어떤데이터로 => url을 만들 때 전부 한꺼번에 적어야 한다.
            //주소로 적는게 복잡할 예정 -> 호스트 주소 email/check

            val urlBuilder = "${HOST_URL}/project".toHttpUrlOrNull()!!.newBuilder()

//    몇 번 프로젝트를 볼건지 / 1 등으로 추가하자.
//    주소에 더 달라붙는 거 표현.
            urlBuilder.addEncodedPathSegment(projectId.toString())

            //만들어진 기초 url에 파라미터들을 붙여준다.
            urlBuilder.addEncodedQueryParameter("proof_date", date)

            //붙일 정보는 다 붙였으면 String으로 최종 변환
            val urlString = urlBuilder.build().toString()

            Log.d("가공된URL", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .header("X-Http-Token", ContextUtil.getLoginToken(context))
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }


            })



        }




    }
}