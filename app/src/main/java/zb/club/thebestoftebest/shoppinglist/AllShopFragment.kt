package zb.club.thebestoftebest.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.ShoppList
import zb.club.thebestoftebest.data.ShoppingListWithDay
import zb.club.thebestoftebest.databinding.FragmentAllShopBinding
import zb.club.thebestoftebest.onboarding.CheckedProductListener
import zb.club.thebestoftebest.onboarding.ShoppingListAdapter


class AllShopFragment : Fragment(), CheckedProductListener {
   var listShopping = mutableListOf<ShoppList>()
    private lateinit var model:ShoppingListViewModel
    private lateinit var adapter: ShoppingListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentAllShopBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_all_shop,
            container, false)

        model = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModelShoppingList= model









        adapter = ShoppingListAdapter( listShopping, this)
        val recycler = binding.shoppingrecycler
        recycler.adapter = adapter
        val layoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        recycler.layoutManager = layoutManager

        model.getShoppingList.observe(viewLifecycleOwner, {shoppingList -> if (shoppingList.isEmpty()){ model.shoppingListfromCurrentMenu.observe(viewLifecycleOwner, {shoppingTemp ->for (i in shoppingTemp){
            val shoppingToShowItem = ShoppList(
                0,
                i.product,
                i.quantity,
                false,
            )
            model.insertShoppingList(shoppingToShowItem)

        } })} else {adapter.setData(shoppingList)} })

       model.getSelectedProduct().observe(viewLifecycleOwner, {
           val insertpositiveCheck = ShoppList(it.id,
           it.product,
           it.quantity,
           true)
           model.updateShoppingList(insertpositiveCheck)
           model.getShoppingListWithDayAsync(it.product)


       })
        model.getUnSelectedProduct().observe(viewLifecycleOwner, {
            val insertpositiveCheck = ShoppList(it.id,
                it.product,
                it.quantity,
                false)
            model.updateShoppingList(insertpositiveCheck)
        })
        model.liveShoppingListWithDay.observe(viewLifecycleOwner, {for (i in it){
            val shoppingItemWithDay = ShoppingListWithDay(
                i.id,
                i.product,
                i.quantity,
                true,
                i.day
            )
            model.updateShoppingListWithDay(shoppingItemWithDay)
        } })





        return binding.root
    }




    override fun onCheckedProduct(checkedProduct: ShoppList) {
        model.setSelectedProduct(checkedProduct)

    }

    override fun unChekedProduct(unchekedProduct: ShoppList) {
        model.setunselectedProduct(unchekedProduct)
    }


}


