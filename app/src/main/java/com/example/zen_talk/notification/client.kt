package com.example.zen_talk.notification

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class client {

    object client{
        private var retrofit: Retrofit? = null
        fun gerClient(url: String?):Retrofit?{
            if(retrofit==null){
                retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}