package my.rjtechnology.apprenticestreet.models

import androidx.room.Embedded
import androidx.room.Relation

data class JobExt(
    @Embedded val job: Job,

    @Relation(parentColumn = "id", entityColumn = "job_id")
    val learningOutcomes: List<LearningOutcome>
)
