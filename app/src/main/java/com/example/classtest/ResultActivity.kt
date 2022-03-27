package com.example.classtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.classtest.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var activityResultBinding: ActivityResultBinding
    var totalScore = 0
    var correct = 0
    var wrong = 0
    var skip = 0
    var isKey = false
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(activityResultBinding.root)
        totalScore = intent.extras!!.getInt("correct")
        wrong = intent.extras!!.getInt("wrong")
        skip = intent.extras!!.getInt("skip")
        initializeViews()
        /*
         * TODO 5: Inflate the respective layout for this activity, retrieve the intents (keys, value pairs)
         *   you passed from previous activity to display in the result activity and initialize the views.
         */
    }
    @SuppressLint("SetTextI18n")
    private fun initializeViews() {
        activityResultBinding.apply {
            score.text = "Score: $totalScore"
            right.text = "Correct: $totalScore"
            tvwrong.text = "Wrong: $wrong"
            tvskip.text = "Skip: $skip"

            if (totalScore >= 6) {
                emojiReactionImg.setImageResource(R.drawable.happy_face)
                Toast.makeText(this@ResultActivity, "Wow Great", Toast.LENGTH_SHORT).show()
            } else {
                emojiReactionImg.setImageResource(R.drawable.blank_face)
                Toast.makeText(this@ResultActivity, "Need Improvement", Toast.LENGTH_SHORT).show()
            }
            /* TODO 6:
                If the total score is greater than 6 then set the emoji with happy_face and
                 display a Toast message "Wow Great"
                 or else show a Toast message "Need Improvement" and set the emoji with blank_face.
             */

            playAgain.setOnClickListener {
                finish()
            }
        }
    }
}