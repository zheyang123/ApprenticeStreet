package my.rjtechnology.apprenticestreet.models

import androidx.room.Entity
import androidx.room.PrimaryKey


    @Entity(tableName = "user") data class User(
        @PrimaryKey val id: String,
        val status:String,
        val email: String,
        val password: String,
        val fullName: String,
        val username: String,
    )
    {
        constructor() : this("", "", "", "", "", "")
    }
