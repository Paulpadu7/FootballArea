package com.example.footballarea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.footballarea.Domain.User

class MainActivity : AppCompatActivity() {
    companion object {
        var user: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}