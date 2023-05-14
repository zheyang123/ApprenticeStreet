package my.rjtechnology.apprenticestreet.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize data class JobExt(
    @Embedded val job: Job,

    @Relation(parentColumn = "id", entityColumn = "job_id")
    val learningOutcomes: List<LearningOutcome>,
): Parcelable {
    @Ignore var learningOutcomesText: String = ""
    @Ignore var isLast: Boolean = false

    constructor() : this(job = Job(), learningOutcomes = listOf())
}
