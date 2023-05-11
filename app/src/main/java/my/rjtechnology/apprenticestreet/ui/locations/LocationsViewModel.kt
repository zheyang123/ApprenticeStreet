package my.rjtechnology.apprenticestreet.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.models.Filter

class LocationsViewModel(application: Application) : AndroidViewModel(application) {
    val filters = listOf(
        Filter(R.drawable.location_johor, application.resources.getString(R.string.johor)),
        Filter(R.drawable.location_kedah, application.resources.getString(R.string.kedah)),
        Filter(R.drawable.location_kelantan, application.resources.getString(R.string.kelantan)),
        Filter(R.drawable.location_kuala_lumpur, application.resources.getString(R.string.kuala_lumpur)),
        Filter(R.drawable.location_melaka, application.resources.getString(R.string.melaka)),
        Filter(R.drawable.location_negeri_sembilan, application.resources.getString(R.string.negeri_sembilan)),
        Filter(R.drawable.location_pahang, application.resources.getString(R.string.pahang)),
        Filter(R.drawable.location_penang, application.resources.getString(R.string.penang)),
        Filter(R.drawable.location_perak, application.resources.getString(R.string.perak)),
        Filter(R.drawable.location_perlis, application.resources.getString(R.string.perlis)),
    )
}
