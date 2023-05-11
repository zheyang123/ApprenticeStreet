package my.rjtechnology.apprenticestreet.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "learning_outcomes",
    foreignKeys = [
        ForeignKey(
            entity = Job::class,
            parentColumns = ["id"],
            childColumns = ["job_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
) data class LearningOutcome(
    @PrimaryKey val id: String,
    val desc: String,
    @ColumnInfo(name = "job_id", index = true) val jobId: String,
)
