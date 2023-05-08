package my.rjtechnology.apprenticestreet.repo

import android.content.Context
import androidx.annotation.WorkerThread
import my.rjtechnology.apprenticestreet.AppDatabase
import my.rjtechnology.apprenticestreet.models.JobExt
import kotlin.streams.toList

class JobRepository(context: Context) {
    private val jobDao = AppDatabase.get(context).jobDao()
    private val learningOutcomeDao = AppDatabase.get(context).learningOutcomeDao()
    val allJobs = jobDao.getAll()

    @WorkerThread suspend fun insertAll(jobs: List<JobExt>) {
        jobDao.insertAll(*jobs.parallelStream().map { it.job }.toList().toTypedArray())

        learningOutcomeDao.insertAll(
            *jobs.parallelStream()
                .flatMap { it.learningOutcomes.parallelStream() }
                .toList()
                .toTypedArray()
        )
    }
}
