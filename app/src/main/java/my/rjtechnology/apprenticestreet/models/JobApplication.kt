package my.rjtechnology.apprenticestreet.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "job_application", primaryKeys = ["user_id", "job_id"]) data class JobApplication(
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "job_id") val jobId: String,
    val status: String = "Registered",
)
