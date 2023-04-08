package my.rjtechnology.apprenticestreet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.databinding.ItemJobBinding
import my.rjtechnology.apprenticestreet.models.Job

class JobAdapter : RecyclerView.Adapter<JobAdapter.ViewHolder>() {
    // TODO: Fetch jobs from database -> list of jobs
    private val data = List(5) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.job = Job(position == data.lastIndex)
    }

    class ViewHolder(val binding: ItemJobBinding) : RecyclerView.ViewHolder(binding.root)
}
