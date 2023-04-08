package my.rjtechnology.apprenticestreet.models

import androidx.room.*
import java.util.UUID

@Entity(tableName = "jobs") data class Job(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "icon_uri") val iconUri: String?,
    val title: String,
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "company_banner_uri") val companyBannerUri: String?,
    val location: String,
    @ColumnInfo(name = "min_salary") val minSalary: Int?,
    @ColumnInfo(name = "max_salary") val maxSalary: Int?,
    @Ignore var isLast: Boolean = false
) {
    constructor(
        id: UUID,
        iconUri: String?,
        title: String,
        companyName: String,
        companyBannerUri: String?,
        location: String,
        minSalary: Int?,
        maxSalary: Int?
    ) : this(
        id,
        iconUri,
        title,
        companyName,
        companyBannerUri,
        location,
        minSalary,
        maxSalary,
        false
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Job

        if (id != other.id) return false
        if (iconUri != other.iconUri) return false
        if (title != other.title) return false
        if (companyName != other.companyName) return false
        if (companyBannerUri != other.companyBannerUri) return false
        if (location != other.location) return false
        if (minSalary != other.minSalary) return false
        if (maxSalary != other.maxSalary) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (iconUri?.hashCode() ?: 0)
        result = 31 * result + title.hashCode()
        result = 31 * result + companyName.hashCode()
        result = 31 * result + (companyBannerUri?.hashCode() ?: 0)
        result = 31 * result + location.hashCode()
        result = 31 * result + (minSalary ?: 0)
        result = 31 * result + (maxSalary ?: 0)
        return result
    }
}
