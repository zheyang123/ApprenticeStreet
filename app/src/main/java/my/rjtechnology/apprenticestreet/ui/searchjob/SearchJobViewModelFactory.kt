package my.rjtechnology.apprenticestreet.ui.searchjob

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchJobViewModelFactory(
    private val application: Application,
    private val nextJobKey: String = "",
    private val onDoing: () -> Unit = {},
    private val onDone: () -> Unit = {}
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchJobViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return SearchJobViewModel(application, nextJobKey, onDoing, onDone) as T

        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}
