package my.rjtechnology.apprenticestreet.ui.viewjobdesc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import my.rjtechnology.apprenticestreet.databinding.FragmentViewJobDescBinding

class ViewJobDescFragment : Fragment() {
    private var _binding: FragmentViewJobDescBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewJobDescBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[ViewJobDescViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val args: ViewJobDescFragmentArgs by navArgs()
        viewModel.jobDesc.value = args.jobDesc
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
