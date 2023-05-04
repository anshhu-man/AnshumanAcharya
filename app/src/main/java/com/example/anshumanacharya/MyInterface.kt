package com.example.anshumanacharya

import data
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MyInterface {
    @GET("WebService.asmx/GetJsonDATA?QueryNo=string&co_code=string&invcode=string&opncl=string&co_id=string&b_code=string&txntyp=string&docod=string&docno=string&user_id=string&acode=string&sessionId=string&JSONStringFormat1=string HTTP/1.1")
    fun fetchData(): Call<data>
}