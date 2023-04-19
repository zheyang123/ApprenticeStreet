package my.rjtechnology.apprenticestreet.ui.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class FilterlessArrayAdapter<T>(context: Context, resource: Int, private val items: Array<out T>) :
    ArrayAdapter<T>(context, resource, items) {

    override fun getFilter(): Filter {
        return NullFilter()
    }

    private inner class NullFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            results.count = items.size
            results.values = items
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }
    }
}
