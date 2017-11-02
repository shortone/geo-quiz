package ch.dictive.labs.geoquiz.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ch.dictive.labs.geoquiz.R
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_ANSWER_IS_TRUE: String = "ch.dictive.labs.geoquiz.answer_is_true"
        private val EXTRA_ANSWER_SHOWN: String = "ch.dictive.labs.geoquiz.answer_shown"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val i = Intent(packageContext, CheatActivity::class.java)
            i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return i
        }

        fun wasAnswerShown(intent: Intent): Boolean {
            return intent.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false)
        }
    }

    private var mAnswerIsTrue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        this.mAnswerIsTrue = this.intent.getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, false)

        this.showAnswerButton.setOnClickListener {
            if (this.mAnswerIsTrue) {
                this.answerTextView.setText(R.string.true_button)
            } else {
                this.answerTextView.setText(R.string.false_button)
            }
            this.setAnswerShown(true)
        }
    }

    private fun setAnswerShown(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra(CheatActivity.EXTRA_ANSWER_SHOWN, isAnswerShown)
        this.setResult(android.app.Activity.RESULT_OK, data)
    }
}
