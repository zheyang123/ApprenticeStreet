package my.rjtechnology.apprenticestreet.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import my.rjtechnology.apprenticestreet.Company
import my.rjtechnology.apprenticestreet.LoginActivity
import my.rjtechnology.apprenticestreet.User

@WorkerThread
@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(userId:LoginActivity. User)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(userId:LoginActivity.Company)
    @Query("SELECT * FROM user") fun getUser(): LiveData<List<LoginActivity.User>>
    @Query("SELECT * FROM company") fun getCompany(): LiveData<List<LoginActivity.Company>>
    @Query("DELETE FROM company") suspend fun clearCompany()
    @Query("DELETE FROM user") suspend fun clearUser()
}