package my.rjtechnology.apprenticestreet.ui.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.rjtechnology.apprenticestreet.models.AppiledProgress

class ProgressViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    private val _companyName = MutableLiveData<List<String>>().apply {
        value = listOf("item1", "item2", "item3")
    }
    val appliedJobList = ArrayList<AppiledProgress>()

    val text: LiveData<String> = _text
    val companyName: LiveData<List<String>> = _companyName
    val haveJob:Boolean = false
}