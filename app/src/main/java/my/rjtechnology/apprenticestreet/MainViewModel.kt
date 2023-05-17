package my.rjtechnology.apprenticestreet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
}
