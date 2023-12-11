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
import com.example.l4_andro.databinding.FragmentModifyBinding

class ModifyFragment : Fragment() {
    lateinit var _binding: FragmentModifyBinding
    lateinit var editName: EditText
    lateinit var editSpec: EditText
    lateinit var editStrength: SeekBar
    lateinit var editType: RadioGroup
    lateinit var editDanger: CheckBox
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
        _binding = FragmentModifyBinding.inflate(inflater, container, false)
        editName = _binding.addName
        editSpec = _binding.addSpec
        editStrength = _binding.addStrengthBar
        editType = _binding.addRadio
        editDanger = _binding.addDanger
        saveButton = _binding.addSaveButton
        cancelButton = _binding.addCancelButton
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("msgtoedit", viewLifecycleOwner) { _, bundle ->
            run {
                editName.setText(bundle.getString("name"))
                editSpec.setText(bundle.getString("spec"))
                editStrength.progress = bundle.getInt("strength")
                editDanger.isChecked = bundle.getBoolean("danger")
                when (bundle.getString("type")) {
                    "Bird" -> _binding.addTypeBird.isChecked = true
                    "Fish" -> _binding.addTypeFish.isChecked = true
                    "Mammal" -> _binding.addTypeMammal.isChecked = true
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModifyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}