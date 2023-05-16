package my.rjtechnology.apprenticestreet.ui.progress


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.rjtechnology.apprenticestreet.ui.searchjob.SearchJobViewModel

class ProgressViewModelFactory(
    private val application: Application,
    private val onDone: (String) -> Unit = {}
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return ProgressViewModel(application, onDone) as T

        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}
