package ch.dictive.labs.geoquiz.data

import android.os.Parcel
import android.os.Parcelable

data class Question(val mTextResId: Int, val mAnswerTrue: Boolean,
                    var mAnswerShown: Boolean) : Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte()) {
    }

    constructor(textResId: Int, mAnswerTrue: Boolean) : this(textResId, mAnswerTrue, false)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mTextResId)
        parcel.writeByte(if (mAnswerTrue) 1 else 0)
        parcel.writeByte(if (mAnswerShown) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}