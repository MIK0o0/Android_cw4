package com.example.l4_andro

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.l4_andro.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var invitation: TextView
    lateinit var authorName: TextView
    lateinit var authorSurname: TextView
    lateinit var imageMain: ImageView
    lateinit var setting: TextView



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
        val view = FragmentMainBinding.inflate(inflater, container, false)
        invitation=view.invitationText
        authorName=view.authorNameTxt
        authorSurname=view.authorSurnameTxt
        imageMain=view.mainImage
        setting=view.additionalSetting

        return view.root
    }
    fun applyData(){
        var basePhotoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val dir2 = Environment.getExternalStorageDirectory()
        val dir3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val dir4 = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val directoryInfo = "dir2: $dir2\ndir3: $dir3\ndir4: $dir4"

        val data: SharedPreferences = requireActivity().getSharedPreferences("L4_preferences", Context.MODE_PRIVATE)
        invitation.text = data.getString("invitation", "Fragment to start on")
        authorName.text = data.getString("authorName", "Mikołaj")
        authorSurname.text = data.getString("authorSurname", "Czyżyk")
        imageMain.setImageResource(data.getInt("image", R.drawable.baseline_emoji_people_24))

        val data2: SharedPreferences = requireActivity().getSharedPreferences("additional", Context.MODE_PRIVATE)
        setting.text = directoryInfo

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyData()

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onResume() {
        super.onResume()
        applyData()
    }
}