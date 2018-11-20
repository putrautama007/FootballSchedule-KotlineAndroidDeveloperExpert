package com.pau.putrautama.footballschedule.api

import java.net.URL


class ApiRepository {

    fun requestUrl(url: String): String {
        return URL(url).readText()
    }
}