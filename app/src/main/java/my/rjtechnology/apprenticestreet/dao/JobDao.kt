package my.rjtechnology.apprenticestreet.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import my.rjtechnology.apprenticestreet.models.Job
import my.rjtechnology.apprenticestreet.models.JobExt

@WorkerThread @Dao interface JobDao {
    @Transaction @Query("SELECT * FROM jobs") fun getAll(): LiveData<List<JobExt>>
    @Insert suspend fun insertAll(vararg jobs: Job)
}
