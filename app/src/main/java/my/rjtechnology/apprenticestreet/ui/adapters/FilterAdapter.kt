package my.rjtechnology.apprenticestreet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.databinding.ItemFilterBinding
import my.rjtechnology.apprenticestreet.models.Filter

class FilterAdapter(private val lifecycleOwner: LifecycleOwner, private val data: List<Filter>) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = data[position]
        holder.binding.model = filter

        holder.binding.card.setOnClickListener {
            filter.isSelected.value = !filter.isSelected.value!!
        }
    }

    class ViewHolder(val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root)
}
