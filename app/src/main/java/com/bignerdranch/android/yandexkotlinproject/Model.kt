package com.bignerdranch.android.yandexkotlinproject

object Model {
    data class Result(val query: WikiQuery)
    data class WikiQuery(val searchinfo: SearchInfo)
    data class SearchInfo(val totalhits: Int)
}

object TranslateModel{
    data class Result(val code: Int,
                      val lang: String,
                      val text: ArrayList<String>)
}