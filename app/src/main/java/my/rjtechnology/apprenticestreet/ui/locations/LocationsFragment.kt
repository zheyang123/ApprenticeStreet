package my.rjtechnology.apprenticestreet.ui.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.rjtechnology.apprenticestreet.Constants
import my.rjtechnology.apprenticestreet.databinding.FragmentLocationsBinding
import my.rjtechnology.apprenticestreet.ui.adapters.FilterAdapter

class LocationsFragment : Fragment() {
    private lateinit var viewModel: LocationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLocationsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment())[LocationsViewModel::class.java]
        binding.locationList.adapter = FilterAdapter(viewLifecycleOwner, viewModel.filters)
        binding.locationList.setHasFixedSize(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.set(
                Constants.LOCATION_COUNT_KEY,
                viewModel.filters.count { it.isSelected.value == true }
            )
    }
}
