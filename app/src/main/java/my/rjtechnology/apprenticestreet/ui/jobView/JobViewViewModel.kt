package my.rjtechnology.apprenticestreet.ui.jobView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.rjtechnology.apprenticestreet.models.JobExt

class JobViewViewModel : ViewModel() {
    val job = MutableLiveData<JobExt>()
}
