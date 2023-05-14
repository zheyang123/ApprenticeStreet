package my.rjtechnology.apprenticestreet.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize @Entity(tableName = "jobs") data class Job(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "icon_uri") val iconUri: String? = null,
    val title: String,
    val desc: String,
    val industry: String,
    @ColumnInfo(name = "company_name") val companyName: String,
    val location: String,
    @ColumnInfo(name = "min_salary") val minSalary: Int? = null,
    @ColumnInfo(name = "max_salary") val maxSalary: Int? = null,
): Parcelable {
    constructor() : this(
        id = "",
        iconUri = null,
        title = "",
        desc = "",
        industry = "",
        companyName = "",
        location = "",
        minSalary = null,
        maxSalary = null
    )
}
