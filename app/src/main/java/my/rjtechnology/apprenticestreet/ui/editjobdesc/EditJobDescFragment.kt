package my.rjtechnology.apprenticestreet.ui.editjobdesc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.rjtechnology.apprenticestreet.Constants
import my.rjtechnology.apprenticestreet.databinding.FragmentEditJobDescBinding

class EditJobDescFragment : Fragment() {
    private lateinit var viewModel: EditJobDescViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditJobDescBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment())[EditJobDescViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.set(
                Constants.JOB_DESC_KEY,
                viewModel.jobDesc.trim()
            )
    }
}
