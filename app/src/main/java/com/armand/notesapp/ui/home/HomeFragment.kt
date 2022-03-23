package com.armand.notesapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Query
import com.armand.notesapp.NotesViewModel
import com.armand.notesapp.R
import com.armand.notesapp.data.entity.Notes
import com.armand.notesapp.databinding.FragmentHomeBinding
import com.armand.notesapp.utils.ExtensionFunctions.setActionBar


class HomeFragment : Fragment(),SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding as FragmentHomeBinding

    private  val homeViewModel by viewModels<NotesViewModel> ()
    private val  homeAdapter by lazy { HomeAdapter() }

    private var _currentData: List<Notes>? = null
    private val currentData get() = _currentData as List<Notes>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbarHome.setActionBar(requireActivity())

        binding.fabAdd.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        setupRecycleView()
    }

    private fun setupRecycleView() {
        binding.rvNotes.apply {
            homeViewModel.getAllData().observe(viewLifecycleOwner) {
                checkDataIsEmpty(it)
                homeAdapter.setData(it)

            }
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }

    }

    private fun checkDataIsEmpty(data: List<Notes>) {
        binding.apply {
            if (data.isEmpty()) {
                imgNoData.visibility = View.VISIBLE
                rvNotes.visibility = View.INVISIBLE
            } else {
                imgNoData.visibility = View.INVISIBLE
                rvNotes.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)

        val searchView = menu.findItem(R.id.menu_search)
        val actionView = searchView.actionView as? SearchView
        actionView?.setOnQueryTextListener(this)



    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_priority_high -> homeViewModel.sortByHighPriority().observe(this) {
                homeAdapter.setData(
                    it
                )
            }
            R.id.menu_priority_low -> homeViewModel.sortByLowPriority().observe(this) {
                homeAdapter.setData(
                    it
                )
            }
            R.id.menu_delete_all -> homeViewModel.deleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun confirmDeleteAll(){
        AlertDialog.Builder(requireContext())
            .setTitle("No Notes!")
            .setMessage("There is no Notes in your phone!")
            .setPositiveButton("OK") { _, _ ->
                homeViewModel.deleteAllData()
                Toast.makeText(context, "Successfully Delete All Notes", Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton("No") { _, _ -> }
            .setNeutralButton("Cancel") { _, _ -> }
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            homeViewModel.searchByQuery(query).observe(this) {
                homeAdapter.setData(it)
            }
        }
          return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null){
            homeViewModel.searchByQuery(newText).observe(this) {
                homeAdapter.setData(it)
            }
        }
        return true

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
