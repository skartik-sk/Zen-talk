package com.example.zen_talk.notification

import com.google.android.gms.tasks.Task

class Token {
    private var token: String=""

    constructor(){}
    constructor(token: String){
        this.token= token.toString()
    }
    fun getToken(): String?{
        return token}
    fun setToken(token: String?){
        this.token= token!!
    }
}