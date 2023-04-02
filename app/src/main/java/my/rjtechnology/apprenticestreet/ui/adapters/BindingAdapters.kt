package my.rjtechnology.apprenticestreet.ui.adapters

import android.util.TypedValue
import android.view.ViewGroup.MarginLayoutParams
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter

@BindingAdapter("marginBottom") fun setMarginBottom(view: CardView, value: Float) {
    (view.layoutParams as MarginLayoutParams).bottomMargin = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, value, view.resources.displayMetrics
    ).toInt()

    view.requestLayout()
}
