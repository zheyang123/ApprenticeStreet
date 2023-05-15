package my.rjtechnology.apprenticestreet.repo

import android.content.Context
import androidx.annotation.WorkerThread
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import my.rjtechnology.apprenticestreet.AppDatabase
import my.rjtechnology.apprenticestreet.models.JobApplication

class JobApplicationRepository(context: Context) {
    private val dao = AppDatabase.get(context).jobApplicationDao()

    @WorkerThread suspend fun insert(jobApplication: JobApplication) {
        dao.insert(jobApplication)

        Firebase.database.reference
            .child("jobApplications")
            .child(jobApplication.userId)
            .child(jobApplication.jobId)
            .setValue(jobApplication)
    }
}
