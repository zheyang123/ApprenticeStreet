package my.rjtechnology.apprenticestreet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.databinding.ItemFilterSimpleBinding
import my.rjtechnology.apprenticestreet.models.SimpleFilter

class SimpleFilterAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val data: List<SimpleFilter>,
    private val onClick: (SimpleFilter) -> Unit) : RecyclerView.Adapter<SimpleFilterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = data[position]
        holder.binding.model = filter
        holder.binding.card.setOnClickListener { onClick(filter) }
    }

    class ViewHolder(val binding: ItemFilterSimpleBinding) : RecyclerView.ViewHolder(binding.root)
}
