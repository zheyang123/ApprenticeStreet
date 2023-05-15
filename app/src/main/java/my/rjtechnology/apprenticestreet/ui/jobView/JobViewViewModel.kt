package my.rjtechnology.apprenticestreet.ui.jobView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.rjtechnology.apprenticestreet.models.JobApplication
import my.rjtechnology.apprenticestreet.models.JobExt
import my.rjtechnology.apprenticestreet.repo.JobApplicationRepository

class JobViewViewModel(application: Application) : AndroidViewModel(application) {
    val job = MutableLiveData<JobExt>()
    val jobAppRepo = JobApplicationRepository(application)

    fun apply(job: JobExt) {
        viewModelScope.launch {
            jobAppRepo.insert(JobApplication(userId = "dont know put what leh!!!", jobId = job.job.id))
        }
    }
}
