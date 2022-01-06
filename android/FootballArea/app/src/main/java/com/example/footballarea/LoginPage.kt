package com.example.footballarea

import android.content.ContentProviderOperation.newCall
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.footballarea.Adapters.MatchAdapters
import com.example.footballarea.Domain.Match
import com.example.footballarea.Domain.User
import com.google.gson.GsonBuilder
import okhttp3.*

import org.apache.http.params.HttpConnectionParams

import org.apache.http.params.HttpParams

import org.json.JSONObject

import okhttp3.internal.http2.Http2Connection
import okhttp3.OkHttpClient
import java.lang.Exception
import okhttp3.Response
import java.io.IOException
import okhttp3.RequestBody
import java.util.concurrent.TimeUnit
import org.json.JSONException
import kotlin.concurrent.thread


class LoginPage : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_page, container, false)
        var loginButton:Button = view.findViewById(R.id.loginButton)
        var username: EditText = view.findViewById(R.id.username)
        var password:EditText = view.findViewById(R.id.password)
        var errorText:TextView = view.findViewById(R.id.errorText)
        var myResponse : String = "false"
        var ok = false
        loginButton.setOnClickListener {

            val client = OkHttpClient()
            val jsonObject = JSONObject()
            try {
                jsonObject.put("username", username.text.toString())
                jsonObject.put("password", password.text.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val JSON = MediaType.parse("application/json; charset=utf-8")
            val body = RequestBody.create(JSON, jsonObject.toString())

                    val request: Request = Request.Builder()
                        .url("ws://192.168.100.20:8080/user/login")
                        .post(body)
                        .build()

                    client.newCall(request).enqueue(object : Callback {


                        override fun onFailure(call: Call?, e: IOException) {
                            e.printStackTrace()
                        }

                        @Throws(IOException::class)
                        override fun onResponse(call: Call?, response: Response) {

                            if (response.isSuccessful) {
                                val body = response.body()?.string()
                                val gson = GsonBuilder().create()
                                MainActivity.Companion.user = gson.fromJson(body,User::class.java)
                                println(MainActivity.Companion.user!!.id)
                                ok=true
                            }
                        }
                    })
            Thread.sleep(300)
            if(ok == true)
            {
                view.findNavController().navigate(
                    R.id.action_loginPage_to_mainPage,null)
            }
            else{
                errorText.visibility = View.VISIBLE
                username.text = null
                password.text = null
            }
        }
        return view
    }
}