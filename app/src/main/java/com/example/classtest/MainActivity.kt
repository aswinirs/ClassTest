package com.example.classtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.classtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.playQuizHomeBtn.setOnClickListener {
            /*
            * TODO 1: Launch play activity to start playing the quiz.
             */
            val intent = Intent(this@MainActivity, PlayActivity::class.java)
            startActivity(intent)
        }
    }
}
