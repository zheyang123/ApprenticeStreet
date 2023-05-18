package my.rjtechnology.apprenticestreet.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import my.rjtechnology.apprenticestreet.R
import android.os.Parcel
import android.os.Parcelable

class Trainee() : Parcelable {
   var name: String = "12"
   var age: Int = 0
   var job: String = "programmer"
   var id: String = "U19994876"
   var learningOutcome: ArrayList<LearningOutcome> = ArrayList()

   constructor(parcel: Parcel) : this() {
      name = parcel.readString() ?: ""
      age = parcel.readInt()
      job = parcel.readString() ?: ""
      id = parcel.readString() ?: ""
      parcel.readList(learningOutcome, LearningOutcome::class.java.classLoader)
   }

   override fun writeToParcel(parcel: Parcel, flags: Int) {
      parcel.writeString(name)
      parcel.writeInt(age)
      parcel.writeString(job)
      parcel.writeString(id)
      parcel.writeList(learningOutcome)
   }

   override fun describeContents(): Int {
      return 0
   }

   companion object CREATOR : Parcelable.Creator<Trainee> {
      override fun createFromParcel(parcel: Parcel): Trainee {
         return Trainee(parcel)
      }

      override fun newArray(size: Int): Array<Trainee?> {
         return arrayOfNulls(size)
      }
   }
}
