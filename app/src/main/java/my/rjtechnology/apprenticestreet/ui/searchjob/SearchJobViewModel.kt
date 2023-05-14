package my.rjtechnology.apprenticestreet.ui.searchjob

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.rjtechnology.apprenticestreet.models.JobExt
import my.rjtechnology.apprenticestreet.repo.JobRepository

class SearchJobViewModel(
    application: Application, onDoing: () -> Unit = {}, onDone: () -> Unit = {}
) : AndroidViewModel(application) {

    var query = ""
    val industries = MutableLiveData<List<String>>(listOf())
    val locations = MutableLiveData<List<String>>(listOf())
    val minSalaryPerMonth = MutableLiveData(0)
    val jobRepo = JobRepository(application)
    val filteredJobs = MutableLiveData<List<JobExt>>(listOf())
    var nextJobKey = ""

    init {
        viewModelScope.launch {
            jobRepo.init(
                viewModelScope,
                onDoing = onDoing,
                onDone = {
                    nextJobKey = it
                    onDone()
                }
            )
        }
    }

    fun refreshJobs(onDone: () -> Unit = {}) {
        jobRepo.getAll(viewModelScope) {
            nextJobKey = it
            onDone()
        }
    }

    fun getNextJobs(onDone: () -> Unit = {}) {
        jobRepo.getAll(viewModelScope, nextJobKey) {
            if (it != "") nextJobKey = it
            onDone()
        }
    }

    fun syncFilteredJobs() {
        filteredJobs.value = jobRepo.allJobs.value?.filter { job ->
            (industries.value?.isEmpty() == true ||
                industries.value?.contains(job.job.industry) == true) &&
                (locations.value?.isEmpty() == true ||
                locations.value?.contains(job.job.location) == true) &&
                (job.job.minSalary ?: 0) >= (minSalaryPerMonth.value ?: 0)
        }
    }
}
