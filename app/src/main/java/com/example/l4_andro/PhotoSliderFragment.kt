package com.example.l4_andro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.l4_andro.Photos.Photo
import com.example.l4_andro.Photos.PhotoRepo

class PhotoSliderFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var photoUrls: MutableList<Photo>
    private lateinit var photoRepo: PhotoRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoRepo = PhotoRepo.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        photoUrls = photoRepo.getSharedList()!!

        val adapter = PhotoSliderAdapter(photoUrls)
        viewPager.adapter = adapter

        // Retrieve the photo path from the arguments
        val photoPath = arguments?.getString("path")
        // Find the position of the photo in the list
        val position = photoUrls.indexOfFirst { it.uripath == photoPath }
        // Set the current item of the ViewPager to this position
        if (position != -1) {
            viewPager.setCurrentItem(position, false)
        }
    }

}
