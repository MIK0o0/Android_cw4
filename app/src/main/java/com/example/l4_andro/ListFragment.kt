package com.example.l4_andro

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.l4_andro.Data.DataItem
import com.example.l4_andro.Data.DataRepo
import com.example.l4_andro.Data.MyDB
import com.example.l4_andro.databinding.FragmentListBinding
import com.example.l4_andro.databinding.ListItemBinding
import com.example.l4_andro.databinding.DialogLayoutBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

val humanoids = arrayOf("Bird", "Fish", "Mammal")

class ListFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _binding: FragmentListBinding
    private lateinit var dataRepo: DataRepo
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataRepo = DataRepo.getInstance()
//        dataRepo = DataRepo.getInstance(requireContext())
        adapter = MyAdapter(dataRepo.getData()!!)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater,container,false)
        val recView = _binding.recView
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = adapter

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("addNewItem", this) { _, _ ->
            adapter.data = dataRepo.getData()!!
            adapter.notifyDataSetChanged()
        }
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_item_add -> {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    inner class MyAdapter(var data: MutableList<DataItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private val viewModel: MyViewModel by activityViewModels()
        inner class MyViewHolder(viewBinding : ListItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val txt1: TextView = viewBinding.itemTitle
            val txt2: TextView = viewBinding.itemSpec1
            val img: ImageView = viewBinding.itemImg
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var currData = data[position]
//            viewModel.updateItem(currData)
//            println(viewModel.sharedItem.value?.toString())
            holder.txt1.text = currData.text_name
            holder.txt2.text = if (currData.text_spec=="Default specification"){
                (currData.item_type + " " + currData.text_spec +" "+ currData.item_strength)
                }else{currData.text_spec}
            holder.itemView.setOnClickListener {
                viewModel.updateItem(currData)
//                parentFragmentManager.setFragmentResult("msgtochild", bundleOf(
//                    "name" to currData.text_name,
//                    "spec" to currData.text_spec,
//                    "strength" to currData.item_strength,
//                    "danger" to currData.dangerous,
//                    "type" to currData.item_type
//                    )
//                )
                findNavController().navigate(R.id.action_listFragment_to_showFragment)
            }
            holder.itemView.setOnLongClickListener {
                val view = DialogLayoutBinding.inflate(layoutInflater).root
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("Delete Dialog")
                    .setPositiveButton("Accept") { dialog, which ->
                        if (dataRepo.deleteItem(currData)) {
                            data = dataRepo.getData()!!
                            notifyDataSetChanged()
                            true
                        }else false
                        Toast.makeText(requireActivity(), "deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }
                builder.setView(view)
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
                true
            }

            when (currData.item_type) {
                "Bird" -> holder.img.setImageResource(R.drawable.bird)
                "Fish" -> holder.img.setImageResource(R.drawable.fish)
                "Mammal" -> holder.img.setImageResource(R.drawable.baseline_emoji_people_24)
                else -> holder.img.setImageResource(R.drawable.bird)
            }

            if (currData.item_strength > 50) {
                holder.itemView.setBackgroundColor(resources.getColor(R.color.red))
            } else {
                holder.itemView.setBackgroundColor(resources.getColor(R.color.green))
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}