package my.rjtechnology.apprenticestreet.ui.postjob

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.rjtechnology.apprenticestreet.AppDatabase
import my.rjtechnology.apprenticestreet.models.Job
import my.rjtechnology.apprenticestreet.models.JobExt
import my.rjtechnology.apprenticestreet.models.LearningOutcome
import my.rjtechnology.apprenticestreet.repo.JobRepository
import java.util.UUID

class PostJobViewModel(application: Application) : AndroidViewModel(application) {
    var jobTitle = ""
    var industry = ""
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

    private val jobRepo = JobRepository(application)
    private val loginDao = AppDatabase.get(application).loginDao()

    fun submit() {
        viewModelScope.launch {
            val jobId = UUID.randomUUID().toString()
            val company = loginDao.getCompany()

            jobRepo.insert(
                JobExt(
                    job = Job(
                        id = jobId,
                        title = jobTitle.trim(),
                        desc = jobDesc.value.toString(),
                        industry = industry,
                        companyId = company.id,
                        companyName = company.companyName,
                        location = location,
                        minSalary = minSalary.toIntOrNull(),
                        maxSalary = maxSalary.toIntOrNull()
                    ),
                    learningOutcomes = learningOutcome.value!!.map {
                        LearningOutcome(
                            id = UUID.randomUUID().toString(),
                            desc = it,
                            jobId = jobId
                        )
                    }
                )
            )
        }
    }
}
