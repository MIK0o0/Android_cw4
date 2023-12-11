package com.example.l4_andro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.l4_andro.Data.DataItem
import com.example.l4_andro.databinding.FragmentShowBinding

private lateinit var myViewModel: MyViewModel

class ShowFragment : Fragment() {
    val viewModel: MyViewModel by activityViewModels()
    private lateinit var _binding: FragmentShowBinding
    lateinit var showName: TextView
    lateinit var showSpec: TextView
    lateinit var showStrength: ProgressBar
    lateinit var showType: ImageView
    lateinit var showDanger: CheckBox
    lateinit var returnButton: Button
    lateinit var editButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowBinding.inflate(inflater, container, false)
        showName=_binding.showName
        showSpec=_binding.showSpec
        showStrength=_binding.showStrengthBar
        showType=_binding.showType
        editButton = _binding.editButton
        showDanger=_binding.showDangerous
        returnButton=_binding.showReturnButton
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sharedItem.observe(viewLifecycleOwner) { itemData ->
            mapItemDataToView(itemData)
        }
        editButton.setOnClickListener {
            findNavController().navigate(R.id.action_show_frag_to_modifyFragment)
        }
        returnButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun mapItemDataToView(itemData: DataItem) {
        showName.text = itemData.text_name
        showSpec.text = itemData.text_spec
        showDanger.isChecked = itemData.dangerous
        showStrength.progress = itemData.item_strength
        when (itemData.item_type) {
            "Bird" -> showType.setImageResource(R.drawable.bird)
            "Fish" -> showType.setImageResource(R.drawable.fish)
            "Mammal" -> showType.setImageResource(R.drawable.baseline_emoji_people_24)
            else -> showType.setImageResource(R.drawable.bird)
        }
    }
}