package zb.club.thebestoftebest.shoppinglist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.ShoppingAdding
import zb.club.thebestoftebest.data.Things
import zb.club.thebestoftebest.databinding.FragmentAddingThingsShoppingBinding
import zb.club.thebestoftebest.onboarding.AdaptofForDialogInShopRecycler
import zb.club.thebestoftebest.onboarding.CheckedAddingListener
import zb.club.thebestoftebest.onboarding.ShoppingAddingListAdapter

class AddingThingsShoppingFragment : Fragment(), CheckedAddingListener, ShoppingAddingListAdapter.OnItemClickListener, AdaptofForDialogInShopRecycler.OnItemDialogClickListener{
    private lateinit var model:ShoppingListViewModel

    lateinit var shopAdding: ArrayList<ShoppingAdding>
    lateinit var adapter:ShoppingAddingListAdapter
    lateinit var adapterForDialog: AdaptofForDialogInShopRecycler
    lateinit var tings: ArrayList<Things>
    lateinit var arrayAdapterForThing: ArrayAdapter<String>
    lateinit var autocomplete: AutoCompleteTextView
    lateinit var quantAut: TextInputEditText
    var quantiti: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddingThingsShoppingBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_adding_things_shopping,
            container, false)

        model = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModelShoppingList= model
        tings = ArrayList<Things>()
        adapterForDialog = AdaptofForDialogInShopRecycler(this, tings)


        shopAdding = ArrayList<ShoppingAdding>()
        model.getStringThing.observe(viewLifecycleOwner, {listThing ->  arrayAdapterForThing = ArrayAdapter(requireContext(), R.layout.drop_down_product, listThing)
            arrayAdapterForThing.setNotifyOnChange(true)
            binding.thingAutuComplite.setAdapter(arrayAdapterForThing)})
        adapter = ShoppingAddingListAdapter(shopAdding, this, this)
        model.getAdding.observe(viewLifecycleOwner, {adapter.setData(it)  })
        binding.reciclerAddingShopping.adapter = adapter
        binding.reciclerAddingShopping.layoutManager = LinearLayoutManager(requireContext())

        binding.imageViewEdit.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
            val dialogview = LayoutInflater.from(it.rootView.context).inflate(R.layout.alert_dialog, null)
            val recyclerInDialog = dialogview.findViewById<RecyclerView>(R.id.reciclerDialog)
            recyclerInDialog.adapter = adapterForDialog
            recyclerInDialog.layoutManager = LinearLayoutManager(requireContext())
            model.getThings.observe(viewLifecycleOwner, {adapterForDialog.setData(it)})
            builder.setView(dialogview)
            val dialog = builder.create()
            dialog.show()
        }
       autocomplete = binding.thingAutuComplite
        autocomplete.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if(s.length > 0){ binding.imageButtonAddItem.visibility =View.VISIBLE} else { binding.imageButtonAddItem.visibility =View.INVISIBLE}
                }
            }
        })
        quantAut = binding.quantitything
        binding.imageButtonAddItem.setOnClickListener {
            addThing()

        }

        model.getSelectedThing().observe(viewLifecycleOwner, {
            val chekedThing = ShoppingAdding(
                it.id,
                it.thing,
                it.quantity,
                true
            )
            model.updateAddingShop(chekedThing)
        })

        model.getUnSelectedThing().observe(viewLifecycleOwner,{
            val chekedThing = ShoppingAdding(
                it.id,
                it.thing,
                it.quantity,
                false
            )
            model.updateAddingShop(chekedThing)})






        return binding.root
    }

    override fun onCheckedThing(checkeThing: ShoppingAdding) {
        model.setSelectedThing(checkeThing)
    }

    override fun unChekedThing(unchekedThing: ShoppingAdding) {
        model.setunselectedThing(unchekedThing)
    }

    override fun onItemClick(position: Int) {
        val item = adapter.getItemAtPosition(position)
        model.deleteAdding(item)
    }

    override fun onItemProductClick(position: Int) {
        val thingDel = adapterForDialog.getProductAtPosition(position)
        model.daletThing(thingDel)

    }
    fun addThing(){
        val thing = autocomplete.text.toString()

        val thingToInsert = Things(
            0, thing
        )
        model.insertThing(thingToInsert)

        if (quantAut.text?.isEmpty() == true){quantiti = 0} else
        {quantiti = quantAut.text.toString().toInt()}
        val shoppingAdding = ShoppingAdding(
            0, thing,quantiti, false
        )
        model.insertAddingShop(shoppingAdding)

        autocomplete.text.clear()
        quantAut.text?.clear()

    }


}