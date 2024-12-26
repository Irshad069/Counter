package com.counterapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.counterapp.R
import com.counterapp.databinding.FragmentHomeBinding
import com.counterapp.model.CounterModel
import com.counterapp.viewmodel.CounterEvent
import com.counterapp.viewmodel.CounterViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CounterViewModel by activityViewModels()

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.counterFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { counterModel ->
                    Log.e(TAG, "collected counter: ${counterModel.count}")
                    binding.counterDisplay.text = counterModel.count.toString()
                }
        }

        binding.apply {
            toolBar.tvTitle.text = getString(R.string.home)
            incrementButton.setOnClickListener {
                viewModel.onEvent(CounterEvent.Increment)
            }
            decrementButton.setOnClickListener {
                viewModel.onEvent(CounterEvent.Decrement)
            }
            resetButton.setOnClickListener {
                viewModel.onEvent(CounterEvent.Reset)
            }
            viewCount.setOnClickListener {
                val currentCount = viewModel.getCurrentCounter()
                val action = HomeFragmentDirections.actionHomeFragmentToCounterFragment(
                    CounterModel(currentCount)
                )
                findNavController().navigate(action)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}