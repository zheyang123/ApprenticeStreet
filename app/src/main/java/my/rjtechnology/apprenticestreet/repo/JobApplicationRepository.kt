package my.rjtechnology.apprenticestreet.repo

import android.content.Context
import androidx.annotation.WorkerThread
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
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

        val companyId = Firebase.database.reference
            .child("jobs")
            .child(jobApplication.jobId)
            .child("job")
            .child("companyId")
            .get()
            .await()
            .getValue<String>()!!

        Firebase.database.reference
            .child("jobApplications")
            .child(companyId)
            .child(jobApplication.jobId)
            .setValue(jobApplication)
    }
}
