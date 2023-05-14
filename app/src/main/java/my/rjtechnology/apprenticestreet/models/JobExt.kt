package my.rjtechnology.apprenticestreet.models

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation

data class JobExt(
    @Embedded val job: Job,

    @Relation(parentColumn = "id", entityColumn = "job_id")
    val learningOutcomes: List<LearningOutcome>,
) {
    @Ignore var learningOutcomesText: String = ""
    @Ignore var isLast: Boolean = false

    constructor() : this(job = Job(), learningOutcomes = listOf())
}
