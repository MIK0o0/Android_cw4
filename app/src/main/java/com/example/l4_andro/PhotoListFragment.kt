package com.example.l4_andro

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.l4_andro.Photos.PhotoRepo
import com.example.l4_andro.databinding.FragmentPhotoListBinding

class PhotoListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoListAdapter
    private var _binding: FragmentPhotoListBinding? = null
    private lateinit var photoRepo: PhotoRepo

    private var isSharedMemory = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isSharedMemory = it.getBoolean("isSharedMemory")
        }
        photoRepo = PhotoRepo.getInstance(requireContext())
        adapter = PhotoListAdapter(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        recyclerView = _binding!!.listView
        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)

        recyclerView.adapter = adapter
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataRepo = PhotoRepo.getInstance(requireContext())
        val photoList = dataRepo.getSharedList()

        if (photoList == null) {
            Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_LONG).show()
            requireActivity().onBackPressed()
        } else {
            adapter.submitList(photoList)
            recyclerView.adapter = adapter
        }
    }


}
