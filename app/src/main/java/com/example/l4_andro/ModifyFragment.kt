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
import androidx.fragment.app.activityViewModels
import com.example.l4_andro.Data.DataItem
import com.example.l4_andro.databinding.FragmentModifyBinding

class ModifyFragment : Fragment() {
    val viewModel: MyViewModel by activityViewModels()
    private var modifyItem: DataItem? = null
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
        viewModel.sharedItem.observe(viewLifecycleOwner) { itemData ->
            modifyItem = itemData
            mapItemDataToView(modifyItem!!)
        }
        saveButton.setOnClickListener {
            updateDataItem()
            viewModel.saveItem(modifyItem!!)
            requireActivity().onBackPressed()
        }
        cancelButton.setOnClickListener { requireActivity().onBackPressed() }
    }
    private fun mapItemDataToView(modifyItem: DataItem){
        editName.setText(modifyItem.text_name)
        editSpec.setText(modifyItem.text_spec)
        editStrength.progress = modifyItem.item_strength
        editDanger.isChecked = modifyItem.dangerous
        when (modifyItem.item_type) {
            "Bird" -> _binding.addTypeBird.isChecked = true
            "Fish" -> _binding.addTypeFish.isChecked = true
            "Mammal" -> _binding.addTypeMammal.isChecked = true
        }
    }

    private fun updateDataItem(){
        modifyItem?.setName(editName.text.toString())
        modifyItem?.setSpec(editSpec.text.toString())
        modifyItem?.setStrength(editStrength.progress)
        modifyItem?.setType(when (editType.checkedRadioButtonId) {
            _binding.addTypeBird.id -> "Bird"
            _binding.addTypeFish.id -> "Fish"
            _binding.addTypeMammal.id -> "Mammal"
            else -> "Bird"
        })
        modifyItem?.setDanger(editDanger.isChecked)
    }
}