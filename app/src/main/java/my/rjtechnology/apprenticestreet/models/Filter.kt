package my.rjtechnology.apprenticestreet.models

import androidx.lifecycle.MutableLiveData

data class Filter(
    val iconRes: Int,
    val label: String = "",
    val isSelected: MutableLiveData<Boolean> = MutableLiveData(false)
)
