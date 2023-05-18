package my.rjtechnology.apprenticestreet.ui.companyTrainee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.rjtechnology.apprenticestreet.models.Trainee

class CompanyTraineeViewModel: ViewModel(){

    val trainees = arrayListOf<Trainee>()
    var id = ""
    var traineeRam = MutableLiveData<Trainee>()

}