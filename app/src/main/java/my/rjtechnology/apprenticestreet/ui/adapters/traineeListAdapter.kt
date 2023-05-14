package my.rjtechnology.apprenticestreet.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.models.AppiledProgress

class traineeListAdapter(private val context: LifecycleOwner, private val data: List<AppiledProgress>) :
    RecyclerView.Adapter<traineeListAdapter.ViewHolder>() {

    var onItemClick:((AppiledProgress)->Unit)?=null
    var onDeleteClick:((AppiledProgress)->Unit)?=null
    var pos = 0
    public
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tranee_list_card_view, parent, false)
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
        private val traineeName: TextView = itemView.findViewById(R.id.pgCardViewTraineeName)
        private val traineeAge: TextView = itemView.findViewById(R.id.pgCradViewStatus)
        private val traineeJob: TextView = itemView.findViewById(R.id.pgCardViewJob)
        val profileImage:ImageView =itemView.findViewById(R.id.profileImage)
        val delete:ImageView =itemView.findViewById(R.id.deleteView)

        fun bind(item:AppiledProgress,index: Int) {
           // companyName.text = item.companyName
            //companyStatus.text=item.status
            delete.setOnClickListener{
                pos = index
                onDeleteClick?.invoke(item)
            }
        }
    }
}