package my.rjtechnology.apprenticestreet.ui.traineeDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.rjtechnology.apprenticestreet.models.LearningOutcome
import my.rjtechnology.apprenticestreet.models.Trainee

class TraineeDetailViewModel: ViewModel(){
    val traneeDetailList = ArrayList<LearningOutcome>()
        //var trainee = Trainee();
    val trainee: MutableLiveData<Trainee> = MutableLiveData()
}