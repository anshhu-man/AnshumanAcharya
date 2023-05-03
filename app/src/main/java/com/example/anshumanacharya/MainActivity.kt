package com.example.anshumanacharya

import BatchDetail
import BatchSummary
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anshumanacharya.databinding.ActivityMainBinding
import data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var call: Call<data>
    private lateinit var adapter: RecyclerAdapter
    private lateinit var list1: ArrayList<BatchDetail>
    private lateinit var list2: ArrayList<BatchSummary>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = RetrofitInstance.getRetrofitInstance()
        val api = retrofit.create(MyInterface::class.java)
        call = api.fetchData()

        list1 = ArrayList()
        list2 = ArrayList()
        adapter = RecyclerAdapter(list1)
        binding.apply {
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.adapter = adapter
        }

        getRequestWithQueryParameters()

    }

    private fun getRequestWithQueryParameters(){
        call.enqueue(object: Callback<data> {
            override fun onResponse(call: Call<data>, response: Response<data>) {
                Log.i("MYTAG","OnResponse: code : "+response.code()+" "+response.body()!!)
                if(response.isSuccessful) {
                    val dataResponse = response.body()!!
                    for (myData in dataResponse.batch_mst.batch_details)
                        list1.add(myData)
                    for (myData in dataResponse.batch_mst.batch_summary)
                        list2.add(myData)
                    adapter.notifyDataSetChanged()
                    binding.rvMain.adapter = adapter
                }
            }

            override fun onFailure(call: Call<data>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message,Toast.LENGTH_LONG).show()
                t.message?.let { Log.i("MYTAG", it) }
            }

        })
    }
}