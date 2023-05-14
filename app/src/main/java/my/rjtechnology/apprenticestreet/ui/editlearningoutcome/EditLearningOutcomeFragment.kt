package my.rjtechnology.apprenticestreet.ui.editlearningoutcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import my.rjtechnology.apprenticestreet.databinding.FragmentEditLearningOutcomeBinding

class EditLearningOutcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditLearningOutcomeBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProvider(requireParentFragment())[EditLearningOutcomeViewModel::class.java]
        return binding.root
    }
}
