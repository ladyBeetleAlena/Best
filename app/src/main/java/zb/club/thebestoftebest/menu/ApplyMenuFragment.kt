package zb.club.thebestoftebest.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Menu
import zb.club.thebestoftebest.data.MenuTemp
import zb.club.thebestoftebest.data.Recipe
import zb.club.thebestoftebest.databinding.FragmentApplyMenuBinding
import java.util.*


class ApplyMenuFragment : Fragment() {
    val model: ViewModelMenu by activityViewModels()
    private val args by navArgs<ApplyMenuFragmentArgs>()
    lateinit var adultEdit: TextInputEditText
    lateinit var childEdit:TextInputEditText
    lateinit var childAdultEdit: TextInputEditText
    lateinit var title: TextView
    lateinit var newTitle:EditText
    lateinit var applyButton:Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentApplyMenuBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_apply_menu,
            container, false
        )
        binding.lifecycleOwner = this
        binding.viewModelMenu = model
        newTitle = binding.editTextTextNewTitle
        adultEdit =binding.adultPortApply
        childEdit = binding.childrenPortionApply
        childAdultEdit = binding.AdCHApply
        applyButton=  binding.buttonCreateApply
        model.getAllServing.observe(viewLifecycleOwner, {if(it.isNotEmpty()){
            adultEdit.setText(it[0].adultServitg.toString())
            childEdit.setText(it[0].childServing.toString())
            childAdultEdit.setText(it[0].k.toString())}
        })
        title=binding.textViewTitleapply
        newTitle.setText("${ args.menuTitle+.1 }")
        model.getWordsAsync(args.menuTitle)
        model.liveMenu.observe(viewLifecycleOwner, {
                menu -> transferToTemp(menu)

        })


       applyButton.setOnClickListener {

            val childNumber: Int = childEdit.text.toString().toInt()
            val adultNumber = adultEdit.text.toString().toInt()
            val childadult= childAdultEdit.text.toString()
             val direction = ApplyMenuFragmentDirections.actionApplyMenuFragmentToCreateMenuFragment(
                 adultNumber, childNumber, childadult, args.menuTitle)
           model.date.clear()
            findNavController().navigate(direction)

        }

        newTitle.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                model.tempMenu.observe(viewLifecycleOwner, {cahgeTitle(it, s)
                    }
               )

            }
        })

        return binding.root
    }

    private fun cahgeTitle(it: List<MenuTemp>?, s: Editable?) {
        var recipeList = ArrayList<Recipe>()
        model.setSelectedRecipes(recipeList)

        if (it != null) {
            applyButton.setText("Create '${it[0].menuTitle.toString()}'")
            for (i in it) {

                val itemToInsertTempMenu = MenuTemp(
                    i.id,
                    i.idRecipe,
                    i.titleRecipe,
                    0,
                    s.toString(),
                    i.day,
                    i.meal,
                    i.childrenServings,
                    i.adultServing,
                    i.childAdultK

                )
                model.updateTempMenu(itemToInsertTempMenu)
            }
        }}



    private fun transferToTemp(it: List<Menu>?) {
        model.clearTempMenu()
        if (it != null) {



            for (i in it) {

                val itemToInsertTempMenu = MenuTemp(
                    0,
                    i.idRecipe,
                    i.titleRecipe,
                    0,
                    args.menuTitle + ".1",
                    i.day,
                    i.meal,
                    i.childrenServings,
                    i.adultServing,
                    i.childAdultK

                )
                model.insertTempMenu(itemToInsertTempMenu)
            }
        }}
}