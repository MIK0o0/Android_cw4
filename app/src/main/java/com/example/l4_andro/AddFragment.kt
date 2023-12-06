package com.example.l4_andro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.core.os.bundleOf
import com.example.l4_andro.Data.DataItem
import com.example.l4_andro.Data.DataRepo
import com.example.l4_andro.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    lateinit var _binding: FragmentAddBinding
    lateinit var addName: EditText
    lateinit var addSpec: EditText
    lateinit var addStrength: SeekBar
    lateinit var addType: RadioGroup
    lateinit var addDanger: CheckBox

    lateinit var saveButton: Button
    lateinit var cancelButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        addName = _binding.addName
        addSpec = _binding.addSpec
        addStrength = _binding.addStrengthBar
        addType = _binding.addRadio
        addDanger = _binding.addDanger
        saveButton = _binding.addSaveButton
        cancelButton = _binding.addCancelButton
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var race: String = "Bird"
        addType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                _binding.addTypeBird.id -> race = "Bird"
                _binding.addTypeFish.id -> race = "Fish"
                _binding.addTypeMammal.id -> race = "Mammal"
            }
        }

        //going back without changes
        cancelButton.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                "addNewItem", bundleOf(
                    "toAdd" to false
                )
            )
            requireActivity().onBackPressed()
        }

        fun createItem(): DataItem {
            val name: String = if (addName.text.toString() == "") {
                "Default name"
            } else {
                addName.text.toString()
            }
            val spec: String = if (addSpec.text.toString() == "") {
                "Default spec"
            } else {
                addSpec.text.toString()
            }
            return DataItem(name, spec, addStrength.progress, race, addDanger.isChecked)
        }

        saveButton.setOnClickListener {
            if (DataRepo.getInstance(requireContext()).addItem(createItem())) {
                parentFragmentManager.setFragmentResult("addNewItem", Bundle.EMPTY)
                requireActivity().onBackPressed()
            }
        }



//        companion object {
//            @JvmStatic
//            fun newInstance(param1: String, param2: String) =
//                AddFragment().apply {
//                    arguments = Bundle().apply {
//
//                    }
//                }
//        }
    }
}