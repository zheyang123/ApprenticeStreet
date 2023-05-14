package my.rjtechnology.apprenticestreet.repo

import android.content.Context
import android.widget.Toast
import androidx.annotation.WorkerThread
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import my.rjtechnology.apprenticestreet.AppDatabase
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.models.JobExt

class JobRepository(private val context: Context) {
    private val jobDao = AppDatabase.get(context).jobDao()
    val allJobs = jobDao.getAll()

    @WorkerThread suspend fun init(
        scope: CoroutineScope, onDoing: () -> Unit = {}, onDone: (String) -> Unit = {}
    ) {
        if (jobDao.hasItem()) return
        onDoing()
        getAll(scope, onDone = onDone)
    }

    fun getAll(scope: CoroutineScope, nextKey: String = "", onDone: (String) -> Unit = {}) {
        Firebase.database.reference
            .child("jobs")
            .orderByKey()
            .startAfter(nextKey)
            .limitToFirst(32)
            .get()
            .addOnSuccessListener { snapshot ->
                scope.launch {
                    val jobs = snapshot.children.map { child -> child.getValue<JobExt>()!! }

                    if (jobs.isEmpty()) {
                        onDone("")
                        return@launch
                    }

                    if (nextKey == "") jobDao.clear()
                    jobDao.insertAll(jobs)

                    onDone(jobs.last().job.id)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, R.string.fetch_job_failed_err_msg, Toast.LENGTH_LONG)
                    .show()
            }
    }
}
