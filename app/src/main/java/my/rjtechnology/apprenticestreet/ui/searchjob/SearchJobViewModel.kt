package my.rjtechnology.apprenticestreet.ui.searchjob

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import my.rjtechnology.apprenticestreet.repo.JobRepository

class SearchJobViewModel(application: Application) : AndroidViewModel(application) {
    var query = ""
    val industryCount = MutableLiveData(0)
    val locationCount = MutableLiveData(0)
    val minSalaryPerMonth = MutableLiveData(0)
    val jobRepo = JobRepository(application)
}
