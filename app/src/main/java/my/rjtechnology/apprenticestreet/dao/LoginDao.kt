package my.rjtechnology.apprenticestreet.dao

import androidx.annotation.WorkerThread
import androidx.room.*
import my.rjtechnology.apprenticestreet.LoginActivity

@WorkerThread
@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(userId:LoginActivity.User)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(userId:LoginActivity.Company)
    @Query("SELECT * FROM user LIMIT 1") suspend fun getUser(): LoginActivity.User
    @Query("SELECT * FROM company LIMIT 1") suspend fun getCompany(): LoginActivity.Company
    @Query("DELETE FROM company") suspend fun clearCompany()
    @Query("DELETE FROM user") suspend fun clearUser()
}