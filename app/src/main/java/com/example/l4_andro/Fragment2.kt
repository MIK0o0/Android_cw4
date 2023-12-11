package com.example.l4_andro

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.l4_andro.databinding.Fragment2Binding


class Fragment2 : Fragment() {
    private lateinit var _binding: Fragment2Binding
    lateinit var radioGr: RadioGroup
    lateinit var rad1: RadioButton
    lateinit var rad2: RadioButton
    lateinit var rad3: RadioButton
    lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment2Binding.inflate(inflater, container, false)
//        val imageResIds = listOf(R.drawable.baseline_emoji_people_24, R.drawable.bird, R.drawable.fish)
//        val imagePagerAdapter = ImagePagerAdapter(requireActivity(), imageResIds)
//        val viewPager = _binding.imageViewPager
//        viewPager.adapter = imagePagerAdapter
//        saveButton = _binding.saveButtonTab2
//        saveButton.setOnClickListener { _ ->
//            val data = requireActivity().getSharedPreferences("L4_preferences", Context.MODE_PRIVATE)
//            val editor = data.edit()
//            val selectedImageResId = imageResIds[viewPager.currentItem]
//            editor.putInt("image", selectedImageResId)
//            editor.apply()
//        }
        radioGr = _binding.editRadio
        rad1 = _binding.editImg1
        rad2 = _binding.editImg2
        rad3 = _binding.editImg3
        saveButton = _binding.saveButtonTab2
        saveButton.setOnClickListener {_->
            val data = requireActivity().getSharedPreferences("L4_preferences", Context.MODE_PRIVATE)
            val editor = data.edit()
            when(radioGr.checkedRadioButtonId){
                rad1.id -> editor.putInt("image", R.drawable.baseline_emoji_people_24)
                rad2.id -> editor.putInt("image", R.drawable.bird)
                rad3.id -> editor.putInt("image", R.drawable.fish)
            }
            editor.apply()
            Toast.makeText(requireContext(), "Image saved", Toast.LENGTH_SHORT).show()
        }
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = requireActivity().getSharedPreferences("L4_preferences", Context.MODE_PRIVATE)
        when(data.getInt("image", R.drawable.baseline_emoji_people_24)){
            R.drawable.baseline_emoji_people_24 -> rad1.isChecked = true
            R.drawable.bird -> rad2.isChecked = true
            R.drawable.fish -> rad3.isChecked = true
        }
    }

    class ImageItemFragment(private val imageResId: Int) : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val imageView = ImageView(context)
            imageView.setImageResource(imageResId)
            return imageView
        }
    }

    class ImagePagerAdapter(fa: FragmentActivity, private val imageResIds: List<Int>) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = imageResIds.size

        override fun createFragment(position: Int): Fragment {
            return ImageItemFragment(imageResIds[position])
        }
    }
}