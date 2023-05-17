package my.rjtechnology.apprenticestreet.ui.companyTrainee

import androidx.lifecycle.ViewModel
import my.rjtechnology.apprenticestreet.models.Trainee

class CompanyTraineeViewModel: ViewModel(){
   val trainee = Trainee()
    val trainees = arrayListOf<Trainee>()
    var id = ""
}