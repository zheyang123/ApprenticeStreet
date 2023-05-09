package my.rjtechnology.apprenticestreet.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.rjtechnology.apprenticestreet.databinding.FragmentProgressBinding
import my.rjtechnology.apprenticestreet.ui.adapters.AppiledCompanyProgressAdapter

class progressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private lateinit var viewModel: ProgressViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val progressViewModel =
            ViewModelProvider(this).get(ProgressViewModel::class.java)

        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val companyRecycler:RecyclerView = binding.comapanyRecycle
        val layoutManager = LinearLayoutManager(activity)
        //layoutManager.setItemSpacing(10) // 设置 Item 间距

        companyRecycler.layoutManager = layoutManager
        companyRecycler.setHasFixedSize(true)
        viewModel = ViewModelProvider(requireParentFragment())[ProgressViewModel::class.java]
        companyRecycler.adapter=AppiledCompanyProgressAdapter(viewLifecycleOwner,viewModel.company)
        val textView: TextView = binding.textHome
        progressViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}