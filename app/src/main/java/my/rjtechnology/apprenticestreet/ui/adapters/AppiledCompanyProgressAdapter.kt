package my.rjtechnology.apprenticestreet.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.R

class AppiledCompanyProgressAdapter(private val context: LifecycleOwner, private val data: List<String>) :
    RecyclerView.Adapter<AppiledCompanyProgressAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.progress_card_view, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.pgCradViewCompanyName)

            fun bind(item: String) {
                textView.text = item
            }
        }
    }

