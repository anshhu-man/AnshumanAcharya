package com.example.anshumanacharya

import BatchDetail
import BatchMst
import BatchSummary
import android.content.res.AssetManager.AssetInputStream
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
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerAdapter
    private lateinit var list: ArrayList<data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        adapter = RecyclerAdapter(list)
        binding.apply {
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.adapter = adapter
        }

        loadJson()

    }

    private fun loadJson(){
        try {
            val inputStream:InputStream = assets.open("info.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer,StandardCharsets.UTF_8)
            val batchDetails = JSONArray(json).getJSONObject(0)
                .getJSONObject("batch_mst")
                .getJSONArray("batch_details")


            var batch = batchDetails.getJSONObject(0)
            var invDescp = batch.getString("invcod")
            var count = 1
            var pcs = 1
            var typeWt = batch.getDouble("net_qty")
            var totalGrams = batch.getDouble("gr_qty")
            var netWt = batch.getDouble("net_qty")
            var invtype = batch.getString("invtype")
            var result = ""
            var st=0

            for(i in 0 until batchDetails.length()){
                batch = batchDetails.getJSONObject(i)
                if(batch.getString("racodno")=="0"){
                    invDescp = batch.getString("invcod")
                    typeWt = batch.getDouble("net_qty")
                    totalGrams = batch.getDouble("gr_qty")
                    netWt = batch.getDouble("net_qty")
                    invtype = batch.getString("invtype")
                    st=i+1
                    break
                }
            }

            for(i in st until batchDetails.length()){

                batch = batchDetails.getJSONObject(i)

                if(batch.getString("racodno")=="0"){

                    if(invDescp == batch.getString("invcod")){
                        totalGrams += batch.getDouble("gr_qty")
                        netWt += batch.getDouble("net_qty")
                        count++
                        if(invtype == batch.getString("invtype")){
                            pcs++
                            typeWt += batch.getDouble("net_qty")
                        }
                        else{
                            list.add(data(invDescp,count,totalGrams,netWt,invtype,pcs,typeWt))
                            invtype = batch.getString("invtype")
                            typeWt = batch.getDouble("net_qty")
                            pcs = 1
                        }
                    }
                    else{
                        list.add(data(invDescp,count,totalGrams,netWt,invtype,pcs,typeWt))
                        invDescp = batch.getString("invcod")
                        totalGrams = batch.getDouble("gr_qty")
                        netWt = batch.getDouble("net_qty")
                        invtype = batch.getString("invtype")
                        typeWt = batch.getDouble("net_qty")
                        pcs = 1
                        count = 1
                    }
                }
            }

            list.add(data(invDescp,count,totalGrams,netWt,invtype,pcs,typeWt))

            result += list + "\n"
            Log.i("MYTAG", "loadJson: $result")

            }
        catch (e:Exception){
            Log.e("MYTAG", "loadJson error: $e")
        }
    }
}