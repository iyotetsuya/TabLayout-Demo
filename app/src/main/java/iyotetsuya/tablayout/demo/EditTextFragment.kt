package iyotetsuya.tablayout.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import iyotetsuya.tablayout.demo.databinding.FragmentEditTextBinding

class EditTextFragment : Fragment() {
    companion object {
        private const val SAVE_TEXT = "save_text"
    }

    private var _binding: FragmentEditTextBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            replaceFragment(NextFragment.newInstance(0))
        }

        val desc = String.format(
            resources.getString(
                R.string.edit_text_fragment_desc,
                System.identityHashCode(this).toString()
            )
        )
        binding.desc.text = desc
        if (savedInstanceState != null) {
            val saveText = savedInstanceState.getString(SAVE_TEXT)
            val saveState = String.format(
                resources.getString(
                    R.string.edit_text_fragment_save_state,
                    saveText
                )
            )
            binding.saveState.text = saveState
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (_binding == null) return
        outState.putString(SAVE_TEXT, binding.input.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.child_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}