package ch.dictive.labs.geoquiz.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import ch.dictive.labs.geoquiz.R
import ch.dictive.labs.geoquiz.data.Question
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    companion object {
        private val TAG: String = "QuizActivity"

        private val KEY_QUESTION_BANK: String = "question_bank"
        private val KEY_INDEX: String = "index"

        private val REQUEST_CODE_CHEAT = 0
    }

    private var mQuestionBank: Array<Question> = arrayOf(
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)
    )

    private var mCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        this.mTrueButton.setOnClickListener {
            this.checkAnswer(true)
        }

        this.mFalseButton.setOnClickListener {
            this.checkAnswer(false)
        }

        this.mPreviousButton.setOnClickListener {
            this.mCurrentIndex = (this.mCurrentIndex - 1).modulo(this.mQuestionBank.size)
            this.updateQuestion()
        }

        this.mNextButton.setOnClickListener {
            this.mCurrentIndex = (this.mCurrentIndex + 1).modulo(this.mQuestionBank.size)
            this.updateQuestion()
        }

        this.cheatButton.setOnClickListener {
            val answerIsTrue = this.mQuestionBank[this.mCurrentIndex].mAnswerTrue
            val i = CheatActivity.newIntent(this, answerIsTrue)
            this.startActivityForResult(i, QuizActivity.REQUEST_CODE_CHEAT)
        }

        if (savedInstanceState != null) {
            this.mCurrentIndex = savedInstanceState.getInt(QuizActivity.KEY_INDEX)
            this.mQuestionBank = savedInstanceState
                    .getParcelableArray(QuizActivity.KEY_QUESTION_BANK) as Array<Question>
        }

        this.updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == QuizActivity.REQUEST_CODE_CHEAT) {
            if (data == null) {
                return
            }

            this.mQuestionBank[this.mCurrentIndex].mAnswerShown = CheatActivity.wasAnswerShown(data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.i(QuizActivity.TAG, "onSaveInstanceState")
        outState!!.putInt(QuizActivity.KEY_INDEX, this.mCurrentIndex)
        outState.putParcelableArray(QuizActivity.KEY_QUESTION_BANK, this.mQuestionBank)
    }

    private fun updateQuestion() {
        val question = this.mQuestionBank[this.mCurrentIndex].mTextResId
        this.mQuestionTextView.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = this.mQuestionBank[this.mCurrentIndex].mAnswerTrue
        val messageResId = if (this.mQuestionBank[this.mCurrentIndex].mAnswerShown) {
            R.string.judgement_toast
        } else {
            if (userPressedTrue == answerIsTrue) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun Int.modulo(other: Int) = ((this % other) + other) % other
}
