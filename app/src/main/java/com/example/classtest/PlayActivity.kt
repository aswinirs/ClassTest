package com.example.classtest

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.classtest.databinding.ActivityPlayBinding
import java.util.*
import java.util.concurrent.TimeUnit

class PlayActivity : AppCompatActivity() {
    private lateinit var activityPlayBinding: ActivityPlayBinding
    //    timer
    private var countDownTimer: CountDownTimer? = null
    private val countDownInMilliSecond: Long = 30000
    private val countDownInterval: Long = 1000
    private lateinit var  builder: AlertDialog.Builder
    var alertDialog: AlertDialog? = null
    /**
     * TODO 2: Set the timer color to RED, when the time left is less than 10000 milliseconds.
     *  If otherwise it should should be BLUE in color.
     */
    private var timeLeftMilliSeconds: Long = 0
    private var defaultColor: ColorStateList? = null
    private var score = 0
    private var correct = 0
    private var wrong = 0
    private var skip = 0
    private var qIndex = 0
    private var updateQueNo = 1

    private var questions = arrayOf(
        " Question 1",
        " Question 2",
        " Question 3",
        " Question 4",
        " Question 5",
        " Question 6",
        " Question 7",
        " Question 8",
        " Question 9",
        " Question 10",
    )
    private var answer = arrayOf(
        "Answer for Q1",
        "Answer for Q2",
        "Answer for Q3",
        "Answer for Q4",
        "Answer for Q5",
        "Answer for Q6",
        "Answer for Q7",
        "Answer for Q8",
        "Answer for Q9",
        "Answer for Q10"
    )
    private var options = arrayOf(
        "Wrong choice for Q1",
        "Answer for Q1",
        "Wrong choice for Q1",
        "Wrong choice for Q1",
        "Answer for Q2",
        "Wrong choice for Q2",
        "Wrong choice for Q2",
        "Wrong choice for Q2",
        "Wrong choice for Q3",
        "Answer for Q3",
        "Wrong choice for Q3",
        "Wrong choice for Q3",
        "Answer for Q4",
        "Wrong choice for Q4",
        "Wrong choice for Q4",
        "Wrong choice for Q4",
        "Wrong choice for Q5",
        "Answer for Q5",
        "Wrong choice for Q5",
        "Wrong choice for Q5",
        "Answer for Q6",
        "Wrong choice for Q6",
        "Wrong choice for Q6",
        "Wrong choice for Q6",
        "Wrong choice for Q7",
        "Answer for Q7",
        "Wrong choice for Q7",
        "Wrong choice for Q7",
        "Answer for Q8",
        "Wrong choice for Q8",
        "Wrong choice for Q8",
        "Wrong choice for Q8",
        "Wrong choice for Q9",
        "Answer for Q9",
        "Wrong choice for Q9",
        "Wrong choice for Q9",
        "Answer for Q10",
        "Wrong choice for Q10",
        "Wrong choice for Q10",
        "Wrong choice for Q10",
        )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPlayBinding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(activityPlayBinding.root)
        builder = AlertDialog.Builder(this@PlayActivity)
        initViews()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (alertDialog != null) {
            alertDialog!!.dismiss()
            alertDialog = null
        }
    }
    @SuppressLint("SetTextI18n")
    private fun showNextQuestion() {
        checkAnswer()
        activityPlayBinding.apply {
            if (updateQueNo < 10) {
                tvNoOfQues.text = "${updateQueNo + 1}/10"
                updateQueNo++
            }
            if (qIndex <= questions.size - 1) {
                tvQuestion.text = questions[qIndex]
                choice1.text = options[qIndex * 4] // 2*4=8
                choice2.text = options[qIndex * 4 + 1] //  2*4+1=9
                choice3.text = options[qIndex * 4 + 2] //  2*4+2=10
                choice4.text = options[qIndex * 4 + 3] //  2*4+3=11
            } else {
                score = correct
                val intent = Intent(this@PlayActivity, ResultActivity::class.java)
                intent.putExtra("correct", correct)
                intent.putExtra("wrong", wrong)
                intent.putExtra("skip", skip)
                startActivity(intent)
                finish()
                /* TODO 3: Navigate to result activity and also pass along the information such
                    as number of correct answers, wrong answers, and skipped questions. Finish this activity
                     after starting next activity. */

            }
            radioGroup.clearCheck()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        activityPlayBinding.apply {
            if (radioGroup.checkedRadioButtonId == -1) {
                skip++
                timeOverAlertDialog()
            } else {
                val checkRadioButton =
                    findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                val checkAnswer = checkRadioButton.text.toString()
                if (checkAnswer == answer[qIndex]) {
                    correct++
                    txtPlayScore.text = "Score : $correct"
                    correctAlertDialog()
                    countDownTimer?.cancel()
                } else {
                    wrong++
                    wrongAlertDialog()
                    countDownTimer?.cancel()
                }
            }
            qIndex++
        }
    }
    @SuppressLint("SetTextI18n")
    private fun initViews() {
        activityPlayBinding.apply {
            tvQuestion.text = questions[qIndex]
            choice1.text = options[0]
            choice2.text = options[1]
            choice3.text = options[2]
            choice4.text = options[3]
            // check options selected or not
            // if selected then selected option correct or wrong
            nextQuesButton.setOnClickListener {
                if (radioGroup.checkedRadioButtonId == -1) {
                    Toast.makeText(this@PlayActivity,
                        "Please select an options",
                        Toast.LENGTH_SHORT)
                        .show()
                } else {
                    showNextQuestion()
                }
            }
            tvNoOfQues.text = "$updateQueNo/10"
            tvQuestion.text = questions[qIndex]
            defaultColor = quizTimer.textColors
            timeLeftMilliSeconds = countDownInMilliSecond
            statCountDownTimer()
        }
    }
    private fun statCountDownTimer() {
        countDownTimer = object : CountDownTimer(timeLeftMilliSeconds, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                activityPlayBinding.apply {
                    timeLeftMilliSeconds = millisUntilFinished
                    val second = TimeUnit.MILLISECONDS.toSeconds(timeLeftMilliSeconds).toInt()
                    // %02d format the integer with 2 digit
                    val timer = String.format(Locale.getDefault(), "Time: %02d", second)
                    quizTimer.text = timer
                    if (timeLeftMilliSeconds < 10000) {
                        quizTimer.setTextColor(Color.BLUE)
                    } else {
                        quizTimer.setTextColor(defaultColor)
                    }
                }
            }
            override fun onFinish() {
                showNextQuestion()
            }
        }.start()
    }
    @SuppressLint("SetTextI18n")
    private fun correctAlertDialog() {
        val view = LayoutInflater.from(this@PlayActivity).inflate(R.layout.right_answer_layout, null)
        builder.setView(view)
        val tvScore = view.findViewById<TextView>(R.id.tvDialog_score)
        val correctOkBtn = view.findViewById<Button>(R.id.correct_ok)
        tvScore.text = "Score : $correct"
        alertDialog = builder.create()
        correctOkBtn.setOnClickListener {
            timeLeftMilliSeconds = countDownInMilliSecond
            statCountDownTimer()
            alertDialog!!.dismiss()
        }
        alertDialog!!.show()
    }
    @SuppressLint("SetTextI18n")
    private fun wrongAlertDialog() {
        val view = LayoutInflater.from(this@PlayActivity).inflate(R.layout.wrong_answer_layout, null)
        builder.setView(view)
        val tvWrongDialogCorrectAns = view.findViewById<TextView>(R.id.tv_wrongDialog_correctAns)
        val wrongOk = view.findViewById<Button>(R.id.wrong_ok)
        tvWrongDialogCorrectAns.text = "Correct Answer : " + answer[qIndex]
        alertDialog = builder.create()
        wrongOk.setOnClickListener {
            timeLeftMilliSeconds =
                countDownInMilliSecond
            statCountDownTimer()
            alertDialog?.dismiss()
        }
        alertDialog?.show()
    }
    @SuppressLint("SetTextI18n")
    private fun timeOverAlertDialog() {
        /*
        * TODO 4: Similar to correctAlertDialog, wrongAlertDialog, implement the logic to inflate and show
        *  idle_state_time_out_layout.
        */
        val view = LayoutInflater.from(this@PlayActivity).inflate(R.layout.idle_state_time_out_layout, null)
        builder.setView(view)
        val timeOverOk = view.findViewById<Button>(R.id.timeOver_ok)
        alertDialog = builder.create()
        timeOverOk.setOnClickListener {
            timeLeftMilliSeconds = countDownInMilliSecond
            statCountDownTimer()
            alertDialog?.dismiss()
        }
        alertDialog?.show()
    }
}