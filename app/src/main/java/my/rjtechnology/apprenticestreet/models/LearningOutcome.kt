package my.rjtechnology.apprenticestreet.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

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
    @PrimaryKey val id: UUID,
    val desc: String,
    @ColumnInfo(name = "job_id") val jobId: UUID,
)
