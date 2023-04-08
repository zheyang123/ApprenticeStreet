package my.rjtechnology.apprenticestreet.ui.searchjob

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchJobViewModel : ViewModel() {
    var query = ""
    val industryCount = MutableLiveData(0)
    val locationCount = MutableLiveData(0)
    val minSalaryPerMonth = MutableLiveData(0)
}
