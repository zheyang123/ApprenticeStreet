package my.rjtechnology.apprenticestreet.models

import androidx.room.*

@Entity(tableName = "jobs") data class Job(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "icon_uri") val iconUri: String?,
    val title: String,
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "company_banner_uri") val companyBannerUri: String?,
    val location: String,
    @ColumnInfo(name = "min_salary") val minSalary: Int?,
    @ColumnInfo(name = "max_salary") val maxSalary: Int?,
) {
    constructor() : this(
        id = "",
        iconUri = null,
        title = "",
        companyName = "",
        companyBannerUri = null,
        location = "",
        minSalary = null,
        maxSalary = null
    )
}
