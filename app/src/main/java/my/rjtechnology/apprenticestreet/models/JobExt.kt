package my.rjtechnology.apprenticestreet.models

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation

data class JobExt(
    @Embedded val job: Job,

    @Relation(parentColumn = "id", entityColumn = "job_id")
    val learningOutcomes: List<LearningOutcome>,
) {
    @Ignore val learningOutcomesText = learningOutcomes.map { it.desc }.fold("") { acc, text ->
        "$acc•  $text${System.getProperty("line.separator")}${System.getProperty("line.separator")}"
    }

    @Ignore var isLast: Boolean = false
}
