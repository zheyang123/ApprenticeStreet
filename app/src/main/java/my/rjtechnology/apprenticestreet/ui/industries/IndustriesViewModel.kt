package my.rjtechnology.apprenticestreet.ui.industries

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.models.Filter

class IndustriesViewModel(application: Application) : AndroidViewModel(application) {
    val filters = listOf(
        Filter(R.drawable.industry_art_and_media, application.resources.getString(R.string.art_media)),
        Filter(R.drawable.industry_construction, application.resources.getString(R.string.construction)),
        Filter(R.drawable.industry_engineering, application.resources.getString(R.string.engineering)),
        Filter(R.drawable.industry_healthcare, application.resources.getString(R.string.healthcare)),
        Filter(R.drawable.industry_hotel, application.resources.getString(R.string.hotel)),
        Filter(R.drawable.industry_info_tech, application.resources.getString(R.string.information_technology)),
        Filter(R.drawable.industry_manufacturing, application.resources.getString(R.string.manufacturing)),
        Filter(R.drawable.industry_restaurant, application.resources.getString(R.string.restaurant)),
        Filter(R.drawable.industry_service, application.resources.getString(R.string.services)),
    )
}
