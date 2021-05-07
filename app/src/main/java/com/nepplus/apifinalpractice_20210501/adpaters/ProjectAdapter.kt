package com.nepplus.apifinalpractice_20210501.adpaters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nepplus.apifinalpractice_20210501.R
import com.nepplus.apifinalpractice_20210501.datas.Project

class ProjectAdapter(
        val mContext : Context,
        val resId : Int,
        val mList : ArrayList<Project>) : ArrayAdapter<Project>(mContext, resId, mList)  {

        val inflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if(tempRow == null){
            //재활용이 꼭 필요할 때만 활용을 한다.

           tempRow = inflater.inflate(R.layout.project_list_item, null)
        }

        val row = tempRow!!

        val Data = mList[position]

        val projectBackgroundImg = row.findViewById<ImageView>(R.id.projectBackgroundImg)
        val profileTitleTxt= row.findViewById<TextView>(R.id.profileTitleTxt)

        profileTitleTxt.text = Data.title
        Glide.with(mContext).load(Data.imageUrl).into(projectBackgroundImg)

        return row


    }
}