package com.armand.notesapp.ui.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.armand.notesapp.R
import com.armand.notesapp.databinding.FragmentDetailBinding
import com.armand.notesapp.utils.ExtensionFunctions.setActionBar

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)

        binding.toolbarDetail.setActionBar(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> findNavController().navigate(R.id.action_detailFragment_to_updateFragment)
            R.id.action_delete -> confirmDeleteNote()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun confirmDeleteNote(){
        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Delete this Note?")
                .setMessage("R U Sure Dude?")
                .setPositiveButton("Yeah") {_, _ ->
                    findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                    Toast.makeText(it, "Succesfully delete this Note", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Noo") {_, _ -> }
                .setNeutralButton("Cancel ") {_, _ -> }
                .show()
        }
    }
}