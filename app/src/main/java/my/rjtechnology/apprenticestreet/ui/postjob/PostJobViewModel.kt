package my.rjtechnology.apprenticestreet.ui.postjob

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class PostJobViewModel : ViewModel() {
    var jobTitle = ""
    var location = ""
    val showSalaries = MutableLiveData(false)
    var minSalary = ""
    var maxSalary = ""
    val jobDesc = MutableLiveData("")
    val learningOutcome = MutableLiveData(listOf<String>())

    val jobDescWordCount = jobDesc.map {
        if (it.isEmpty()) {
            0
        } else {
            it.split("\\s+".toRegex()).size
        }
    }

    fun areSalariesValid() =
        minSalary == "" || maxSalary == "" || minSalary.toInt() <= maxSalary.toInt()
}
