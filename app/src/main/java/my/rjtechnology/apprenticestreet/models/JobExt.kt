package my.rjtechnology.apprenticestreet.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize data class JobExt(
    @Embedded val job: Job,

    @Relation(parentColumn = "id", entityColumn = "job_id")
    val learningOutcomes: List<LearningOutcome>,
): Parcelable {
    @Ignore var learningOutcomesText: String = ""
    @IgnoredOnParcel @Ignore var isLast: Boolean = false

    constructor() : this(job = Job(), learningOutcomes = listOf())

    private companion object : Parceler<JobExt> {
        override fun JobExt.write(parcel: Parcel, flags: Int) {
            parcel.writeParcelable(job, flags)
            parcel.writeParcelableArray(learningOutcomes.toTypedArray(), flags)
            parcel.writeString(learningOutcomesText)
        }

        override fun create(parcel: Parcel): JobExt {
            val result = JobExt(
                parcel.readParcelable(ClassLoader.getSystemClassLoader())!!,
                parcel.readParcelableArray(ClassLoader.getSystemClassLoader())!!
                    .map { it as LearningOutcome }
            )

            result.learningOutcomesText = parcel.readString()!!
            return result
        }
    }
}
