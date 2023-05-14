package my.rjtechnology.apprenticestreet.ui.postjob

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostJobViewModel : ViewModel() {
    var showSalaries = MutableLiveData(false)
    var jobDescWordCount = MutableLiveData(0)
}
