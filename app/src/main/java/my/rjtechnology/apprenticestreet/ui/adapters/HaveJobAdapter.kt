package my.rjtechnology.apprenticestreet.ui.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.models.LearningOutcome

class HaveJobAdapter(private val context: LifecycleOwner, private val data: List<LearningOutcome>,private val contexts:Context) :
    RecyclerView.Adapter<HaveJobAdapter.ViewHolder>() {

    var onItemClick:((LearningOutcome)->Unit)?=null
    //  var onDeleteClick:((AppiledProgress)->Unit)?=null
    var pos = 0
    public
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.have_job_card_view, parent, false)
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
        private val learningOutcomeText:TextView = itemView.findViewById(R.id.learningOutcomeDesc)
        private val isDone:ImageView = itemView.findViewById(R.id.isDoneImage)
        //  val delete: ImageView =itemView.findViewById(R.id.deleteView)

        fun bind(item: LearningOutcome, index: Int) {
            learningOutcomeText.setText(item.desc.toString())
            if (item.progress) {
                isDone.setImageResource(R.drawable.ic_green_check)
            } else {
                isDone.setImageResource(R.drawable.ic_grey_check_foreground)
            }


//            delete.setOnClickListener{
//                pos = index
//                onDeleteClick?.invoke(item)
//            }
        }
    }
}