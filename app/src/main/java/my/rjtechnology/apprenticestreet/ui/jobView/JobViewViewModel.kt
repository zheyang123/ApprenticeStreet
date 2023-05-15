package my.rjtechnology.apprenticestreet.ui.jobView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.rjtechnology.apprenticestreet.AppDatabase
import my.rjtechnology.apprenticestreet.models.JobApplication
import my.rjtechnology.apprenticestreet.models.JobExt
import my.rjtechnology.apprenticestreet.repo.JobApplicationRepository

class JobViewViewModel(application: Application) : AndroidViewModel(application) {
    val job = MutableLiveData<JobExt>()
    private val jobAppRepo = JobApplicationRepository(application)
    private val loginDao = AppDatabase.get(application).loginDao()

    fun apply(job: JobExt, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            jobAppRepo.insert(
                JobApplication(userId = loginDao.getUser().id, jobId = job.job.id)
            )

            onDone()
        }
    }
}
