package my.rjtechnology.apprenticestreet.ui.adapters

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


class TraineeDetailAdapter(private val context: LifecycleOwner, private val data: List<LearningOutcome>) :
    RecyclerView.Adapter<TraineeDetailAdapter.ViewHolder>() {

    var onItemClick:((LearningOutcome)->Unit)?=null
    var onCheckClick:((LearningOutcome)->Unit)?=null
    var pos = 0
    public
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trainee_details_card_view, parent, false)
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
        private val learningOutcome: CheckBox = itemView.findViewById(R.id.learningOurcomeCheckbox)
      //  val delete: ImageView =itemView.findViewById(R.id.deleteView)

        fun bind(item: LearningOutcome, index: Int) {
          learningOutcome.setText(item.desc)
            learningOutcome.isChecked = item.progress
            learningOutcome.setOnClickListener{
              pos = index
               onCheckClick?.invoke(item)

            }
        }
    }
}