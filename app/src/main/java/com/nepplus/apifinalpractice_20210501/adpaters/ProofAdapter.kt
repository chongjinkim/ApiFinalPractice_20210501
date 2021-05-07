package com.nepplus.apifinalpractice_20210501.adpaters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nepplus.apifinalpractice_20210501.R
import com.nepplus.apifinalpractice_20210501.datas.Proof
import java.util.ArrayList

class ProofAdapter(
        val mContext : Context,
        val resId : Int,
        val mList : ArrayList<Proof>) : ArrayAdapter<Proof>(mContext, resId, mList) {

        val inflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if(tempRow == null){
            tempRow = inflater.inflate(R.layout.proof_list_item, null)
        }

        val row = tempRow!!

        val proofData = mList[position]



        return row
    }
}