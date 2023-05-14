package my.rjtechnology.apprenticestreet.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import my.rjtechnology.apprenticestreet.models.Job
import my.rjtechnology.apprenticestreet.models.JobExt
import my.rjtechnology.apprenticestreet.models.LearningOutcome
import kotlin.streams.toList

@WorkerThread @Dao interface JobDao {
    @Transaction @Query("SELECT * FROM jobs") fun getAll(): LiveData<List<JobExt>>
    @Query("SELECT EXISTS(SELECT * FROM jobs)") suspend fun hasItem(): Boolean
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(vararg jobs: Job)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(job: Job)
    @Update(onConflict = OnConflictStrategy.REPLACE) suspend fun update(job: Job)
    @Delete suspend fun delete(job: Job)
    @Query("DELETE FROM jobs") suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg learningOutcomes: LearningOutcome)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(vararg learningOutcomes: LearningOutcome)

    @Transaction suspend fun insertAll(jobs: List<JobExt>) {
        insertAll(*jobs.parallelStream().map { it.job }.toList().toTypedArray())

        insertAll(
            *jobs.parallelStream()
                .flatMap { it.learningOutcomes.parallelStream() }
                .toList()
                .toTypedArray()
        )
    }

    @Transaction suspend fun insert(job: JobExt) {
        insert(job.job)
        insertAll(*job.learningOutcomes.toTypedArray())
    }
}
