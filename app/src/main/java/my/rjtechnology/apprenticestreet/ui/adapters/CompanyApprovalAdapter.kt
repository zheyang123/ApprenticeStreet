package my.rjtechnology.apprenticestreet.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.models.CompanyApproval

class CompanyApprovalAdapter (private val context: LifecycleOwner, private val data: List<CompanyApproval>) :
    RecyclerView.Adapter<CompanyApprovalAdapter.ViewHolder>() {

    var onItemClick:((CompanyApproval)->Unit)?=null
  //  var onDeleteClick:((AppiledProgress)->Unit)?=null
    var pos = 0
    public
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.company_approval_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item,position)
        holder.itemView.setOnClickListener{

            onItemClick?.invoke(item)

        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val traineeName: TextView = itemView.findViewById(R.id.traineeName)
        private val appliedJob: TextView = itemView.findViewById(R.id.jobName)
        val profilePicture: ImageView =itemView.findViewById(R.id.profileImage1)

        fun bind(item: CompanyApproval, index: Int) {
            traineeName.text = item.traineeName
            appliedJob.text= item.job
            profilePicture.setImageBitmap(item.profileImage)
            //companyStatus.text=item.status
//            delete.setOnClickListener{
//                pos = index
//                onDeleteClick?.invoke(item)
//            }
        }
    }

}