package com.example.zen_talk.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class viewspager(private val context:Context,
    fm: FragmentManager?,
    val list: ArrayList<Fragment>,

    ):FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
return list.size;
    }

    override fun getItem(position: Int): Fragment {
       return list[position]
    }
    override
    fun
            getPageTitle(position: Int): CharSequence? {
        return Tabtitle[position]}
    companion object{
      val  Tabtitle= arrayOf("Chat", "Group", "Call")
    }

}