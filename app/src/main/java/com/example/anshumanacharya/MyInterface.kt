package com.example.anshumanacharya

import data
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface MyInterface {
    @GET(":8069/WebService.asmx/GetJsonDATA?QueryNo=18&co_code=A&invcode=F1B002&opncl=D&co_id=ananta&b_code=341&txntyp=sa&docod=xx&docno=544&user_id=1&acode=XX&sessionId=sahil&JSONStringFormat1=[{\"opncl\":\"O\",\"invcodstart\":\"AD\",\"invcodend\":\"EM1\",\"loc\":\"HO\",\"co_id\":\"ananta\"}]")
    fun fetchData(): Call<data>
}