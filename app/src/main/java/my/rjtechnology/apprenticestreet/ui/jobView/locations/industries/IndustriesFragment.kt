package my.rjtechnology.apprenticestreet.ui.jobView.locations.industries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.rjtechnology.apprenticestreet.Constants
import my.rjtechnology.apprenticestreet.databinding.FragmentIndustriesBinding
import my.rjtechnology.apprenticestreet.ui.adapters.FilterAdapter

class IndustriesFragment : Fragment() {
    private var _binding: FragmentIndustriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: IndustriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustriesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment())[IndustriesViewModel::class.java]
        binding.industryList.adapter = FilterAdapter(viewLifecycleOwner, viewModel.filters)
        binding.industryList.setHasFixedSize(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.set(
                Constants.INDUSTRIES_KEY,
                viewModel.filters.filter { it.isSelected.value == true }.map { it.label }
            )

        _binding = null
    }
}
