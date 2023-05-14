package my.rjtechnology.apprenticestreet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.databinding.ItemJobBinding
import my.rjtechnology.apprenticestreet.models.JobExt

class JobAdapter : ListAdapter<JobExt, JobAdapter.ViewHolder>(Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item.isLast = position == itemCount - 1

        item.learningOutcomesText = item.learningOutcomes.map { it.desc }.fold("") { acc, text ->
            "$acc•  $text${System.getProperty("line.separator")}${System.getProperty("line.separator")}"
        }.trimEnd()

        holder.binding.model = item
    }

    override fun getItemId(position: Int) = getItem(position).job.id.hashCode().toLong()
    class ViewHolder(val binding: ItemJobBinding) : RecyclerView.ViewHolder(binding.root)

    private class Comparator : DiffUtil.ItemCallback<JobExt>() {
        override fun areItemsTheSame(oldItem: JobExt, newItem: JobExt) = oldItem === newItem
        override fun areContentsTheSame(oldItem: JobExt, newItem: JobExt) = oldItem == newItem
    }
}
