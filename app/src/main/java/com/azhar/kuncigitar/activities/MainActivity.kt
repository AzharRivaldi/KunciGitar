package com.azhar.kuncigitar.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.azhar.kuncigitar.R
import com.azhar.kuncigitar.networking.ApiClient.getApiClient
import com.azhar.kuncigitar.networking.ApiInterface
import com.github.akshay_naik.texthighlighterapi.TextHighlighter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var highlighter: TextHighlighter? = null
    var strInputTeks: String = ""
    var strResult: String = ""
    var strKunciGitar: String = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        highlighter = TextHighlighter()

        //set color kunci gitar
        highlighter?.setColorForTheToken("C", "red")
        highlighter?.setColorForTheToken("Cm", "red")
        highlighter?.setColorForTheToken("C#", "blue")
        highlighter?.setColorForTheToken("C#m", "blue")
        highlighter?.setColorForTheToken("D", "red")
        highlighter?.setColorForTheToken("Dm", "red")
        highlighter?.setColorForTheToken("D#", "blue")
        highlighter?.setColorForTheToken("D#m", "blue")
        highlighter?.setColorForTheToken("E", "red")
        highlighter?.setColorForTheToken("Em", "red")
        highlighter?.setColorForTheToken("F", "red")
        highlighter?.setColorForTheToken("Fm", "red")
        highlighter?.setColorForTheToken("F#", "blue")
        highlighter?.setColorForTheToken("F#m", "blue")
        highlighter?.setColorForTheToken("G", "red")
        highlighter?.setColorForTheToken("Gm", "red")
        highlighter?.setColorForTheToken("G#", "blue")
        highlighter?.setColorForTheToken("G#m", "blue")
        highlighter?.setColorForTheToken("A", "red")
        highlighter?.setColorForTheToken("Am", "red")
        highlighter?.setColorForTheToken("A#", "blue")
        highlighter?.setColorForTheToken("A#m", "blue")
        highlighter?.setColorForTheToken("B", "red")
        highlighter?.setColorForTheToken("Bm", "red")

        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Mohon Tunggu...")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang menampilkan data")

        imageClear.setVisibility(View.GONE)
        linearHasil.setVisibility(View.GONE)

        //clear data
        imageClear.setOnClickListener {
            inputJudul.getText().clear()
            tvKunciGitar.setText("")
            linearHasil.setVisibility(View.GONE)
            imageClear.setVisibility(View.GONE)
        }

        //button action search
        btnPencarian.setOnClickListener {
            strInputTeks = inputJudul.getText().toString()
            if (strInputTeks.isEmpty()) {
                Toast.makeText(this@MainActivity, "Judul lagu harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                getData(strInputTeks)
            }
        }

    }

    // get data
    private fun getData(strInputTeks: String) {
        progressDialog?.show()
        val apiInterface = getApiClient().create(ApiInterface::class.java)
        val call = apiInterface.getKunciGitar(strInputTeks)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful && response.body() != null) {
                    progressDialog?.dismiss()
                    try {
                        val responseObject = JSONObject(response.body())
                        strResult = responseObject.getString("result")

                        //get color kunci gitar
                        strKunciGitar = highlighter?.getHighlightedText(strResult)

                        tvKunciGitar.text = strKunciGitar

                        linearHasil.visibility = View.VISIBLE
                        imageClear.visibility = View.VISIBLE
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this@MainActivity,
                                "Oops, kunci gitar tidak ditemukan.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(this@MainActivity, "Oops, jaringan kamu bermasalah.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}