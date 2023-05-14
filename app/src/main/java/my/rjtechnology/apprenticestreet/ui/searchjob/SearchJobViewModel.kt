package my.rjtechnology.apprenticestreet.ui.searchjob

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.rjtechnology.apprenticestreet.models.JobExt
import my.rjtechnology.apprenticestreet.repo.JobRepository

class SearchJobViewModel(
    application: Application,
    nextJobKey: String = "",
    onDoing: () -> Unit = {},
    onDone: () -> Unit = {}
) : AndroidViewModel(application) {

    val query = MutableLiveData("")
    val industries = MutableLiveData(listOf<String>())
    val locations = MutableLiveData(listOf<String>())
    val minSalaryPerMonth = MutableLiveData(0)
    val jobRepo = JobRepository(application)

    private val _filteredJobs = MutableLiveData(listOf<JobExt>())
    val filteredJobs: LiveData<List<JobExt>> get() = _filteredJobs

    var nextJobKey = nextJobKey
        private set

    init {
        viewModelScope.launch {
            jobRepo.init(
                viewModelScope,
                onDoing = onDoing,
                onDone = {
                    this@SearchJobViewModel.nextJobKey = it
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
        _filteredJobs.value = jobRepo.allJobs.value?.filter { job ->
            query.value!!.lowercase().replace("\\s+".toRegex(), "") in
                job.job.title.lowercase().replace("\\s+".toRegex(), "") &&
                (industries.value?.isEmpty() == true ||
                industries.value?.contains(job.job.industry) == true) &&
                (locations.value?.isEmpty() == true ||
                locations.value?.contains(job.job.location) == true) &&
                (job.job.minSalary ?: 0) >= (minSalaryPerMonth.value ?: 0)
        }
    }
}
