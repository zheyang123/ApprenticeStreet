package my.rjtechnology.apprenticestreet.ui.traineeDetail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.rjtechnology.apprenticestreet.CompanyMainActivityViewModel
import my.rjtechnology.apprenticestreet.R
import my.rjtechnology.apprenticestreet.databinding.FragmentCompanyApprovalBinding
import my.rjtechnology.apprenticestreet.databinding.FragmentDashboardBinding
import my.rjtechnology.apprenticestreet.databinding.FragmentTraineeDetailBinding
import my.rjtechnology.apprenticestreet.models.Trainee
import my.rjtechnology.apprenticestreet.ui.adapters.CompanyApprovalAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.TraineeDetailAdapter
import my.rjtechnology.apprenticestreet.ui.adapters.traineeListAdapter
import my.rjtechnology.apprenticestreet.ui.companyApproval.CompanyApprovalViewModel
import my.rjtechnology.apprenticestreet.ui.companyTrainee.CompanyTraineeViewModel


class TraineeDetailFragment : Fragment() {
    private lateinit var viewModel: TraineeDetailViewModel
    private var _binding: FragmentTraineeDetailBinding? = null
    private val binding get() = _binding!!
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var bit: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
   // private lateinit var mViewModel: CompanyTraineeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTraineeDetailBinding.inflate(inflater, container, false)
        val bundle = arguments

        val trainee = bundle?.getParcelable<Trainee>("trainee")
        viewModel = ViewModelProvider(requireParentFragment())[TraineeDetailViewModel::class.java]
        binding.viewModel=viewModel

        val imageRef = storageRef.child("UserProfilePics").child(trainee!!.id)

        // Download the image as a byte array
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            // Image download successful, handle the byte array
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            binding.profileImg.setImageBitmap(bitmap)
            // bit = bitmap
        }

            // 在此处使用获取到的 ID 值进行后续操作
            //viewModel.trainee = it
            binding.textView9.text= trainee?.name
            binding.ageTextView.text =  trainee?.age.toString()
            binding.jobTextView.text = trainee?.job




        val layoutManager = LinearLayoutManager(activity)

        var progressAdapter: TraineeDetailAdapter = TraineeDetailAdapter(viewLifecycleOwner,trainee!!.learningOutcome)

        binding.traineeDetailRecycle.layoutManager = layoutManager
        binding.traineeDetailRecycle.setHasFixedSize(true)
        binding.traineeDetailRecycle.adapter = progressAdapter

        progressAdapter.onCheckClick ={
            Toast.makeText(context,progressAdapter.pos.toString(),Toast.LENGTH_SHORT).show()
            it.progress = !it.progress
            var database = Firebase.database.reference
            database.child("traineeJob").child(trainee.id).child("learningOutcome").child(progressAdapter.pos.toString()).child("progress").setValue(it.progress)
            progressAdapter.notifyDataSetChanged()
        }




        // Inflate the layout for this fragment
        _binding = FragmentTraineeDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}