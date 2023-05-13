package my.rjtechnology.apprenticestreet.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.R

class AppiledCompanyProgressAdapter(private val context: LifecycleOwner, private val data: List<String>) :
    RecyclerView.Adapter<AppiledCompanyProgressAdapter.ViewHolder>() {

    var onItemClick:((String)->Unit)?=null
    var onDeleteClick:((String)->Unit)?=null
    var pos = 0
    public
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.progress_card_view, parent, false)
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
        private val textView: TextView = itemView.findViewById(R.id.pgCradViewCompanyName)
        val delete:ImageView =itemView.findViewById(R.id.deleteView)

        fun bind(item: String,index: Int) {

            textView.text = item
            delete.setOnClickListener{
                pos = index
                onDeleteClick?.invoke(item)
            }
        }
    }
}

