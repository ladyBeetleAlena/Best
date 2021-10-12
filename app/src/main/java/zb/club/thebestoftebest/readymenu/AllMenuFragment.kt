package zb.club.thebestoftebest.readymenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.databinding.FragmentAllMenuBinding
import zb.club.thebestoftebest.onboarding.AllMenuAdapter


class AllMenuFragment : Fragment(),AllMenuAdapter.OnItemClickListener{
    private lateinit var model:ViewModelReadyMenu
    private val menuList = mutableListOf<String>()
    private lateinit var adapterMenu: AllMenuAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAllMenuBinding =
                  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_all_menu, container, false)

        model = ViewModelProvider(this).get(ViewModelReadyMenu::class.java)
        binding.lifecycleOwner = this
        binding.viewModelsAllMenu = model
        model.allRecipe.observe(viewLifecycleOwner,{if (it.isEmpty()){binding.floatingActionButtonAddNewMenu.visibility = View.INVISIBLE}else{binding.floatingActionButtonAddNewMenu.visibility=View.VISIBLE} })
        binding.floatingActionButtonAddNewMenu.setOnClickListener{
           it.findNavController().navigate(R.id.action_todayFragment_to_startPlanningFragment)
        }

        adapterMenu = AllMenuAdapter(menuList, this)
        val recyclerView = binding.recyclerAllmenu
        recyclerView.adapter = adapterMenu
        val layoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        model.getMenuTitle.observe(viewLifecycleOwner, { menuList -> if(menuList.size > 0){adapterMenu.setData(
            menuList) }})

        return binding.root

    }

    override fun OnItemClick(position: Int) {
        val clickedItem: String =  adapterMenu.getTitlByPositiom(position)
        val goToShowMenu = TodayFragmentDirections.actionTodayFragmentToShowMenuByTitleFragment(clickedItem)
        findNavController().navigate(goToShowMenu)
    }

    override fun OnItemLongClick(position: Int):Boolean {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val clickedItem: String =  adapterMenu.getTitlByPositiom(position)
        builder.setTitle("What do you want to do with '${clickedItem}'?")
        builder.setNegativeButton("Applay  to curent menu "){ dialog, wich -> val applayMenu = TodayFragmentDirections.actionTodayFragmentToApplyMenuFragment(clickedItem)
            findNavController().navigate(applayMenu)
        }

        builder.setNeutralButton("Delete menu "){
                dialog, wich -> model.deleteMenuByTitle(clickedItem)




        }

        val dialog = builder.create()
        dialog.show()

       return true
    }

    override fun deleteClick(position: Int) {
        val clickedItem: String =  adapterMenu.getTitlByPositiom(position)
        model.deleteMenuByTitle(clickedItem)
    }


}