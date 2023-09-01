package iyotetsuya.tablayout.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import iyotetsuya.tablayout.demo.databinding.FragmentKeepStateBinding

class KeepFragment : Fragment() {
    private var _binding: FragmentKeepStateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKeepStateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val desc =
            getString(R.string.keep_fragment) + ": " + System.identityHashCode(this).toString()

        binding.textKeepFragment.text = desc
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}