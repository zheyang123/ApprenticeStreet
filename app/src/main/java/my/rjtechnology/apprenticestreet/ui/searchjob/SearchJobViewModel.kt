package my.rjtechnology.apprenticestreet.ui.searchjob

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.rjtechnology.apprenticestreet.repo.JobRepository

class SearchJobViewModel(
    application: Application, onDoing: () -> Unit = {}, onDone: () -> Unit = {}
) : AndroidViewModel(application) {

    var query = ""
    val industryCount = MutableLiveData(0)
    val locationCount = MutableLiveData(0)
    val minSalaryPerMonth = MutableLiveData(0)
    val jobRepo = JobRepository(application)
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
}
