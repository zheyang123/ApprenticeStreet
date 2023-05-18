package my.rjtechnology.apprenticestreet.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize @Entity(
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
    var desc: String,
    var progress: Boolean = false,
    @ColumnInfo(name = "job_id", index = true) val jobId: String,
) : Parcelable {
    constructor(): this(id = "", progress=false, desc = "", jobId = "")
}
