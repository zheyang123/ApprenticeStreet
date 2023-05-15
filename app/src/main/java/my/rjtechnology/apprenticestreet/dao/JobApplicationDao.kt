package my.rjtechnology.apprenticestreet.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import my.rjtechnology.apprenticestreet.models.JobApplication

@WorkerThread @Dao interface JobApplicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(jobApplication: JobApplication)
}
