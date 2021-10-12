package zb.club.thebestoftebest.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.cookbook.viewmodelcookbook.ViewModelAddRecipe
import zb.club.thebestoftebest.data.Recipe
import zb.club.thebestoftebest.databinding.FragmentAllRecipeBinding
import zb.club.thebestoftebest.onboarding.AdapterForAllRecipe


class AllRecipeFragment : Fragment(),AdapterForAllRecipe.OnItemLongClickListener {
    private val recipeList = mutableListOf<Recipe>()
    private lateinit var model: ViewModelAddRecipe
    private lateinit var adapter: AdapterForAllRecipe
    private var originalMode : Int? = null
    var recipeId: Long = 0
    var newrecipe: Recipe? = null
    var emptyRecipe = Recipe(0, "new recipe", null, null, null, false)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAllRecipeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_recipe, container, false)







       model = ViewModelProvider(this).get(ViewModelAddRecipe::class.java)



       binding.lifecycleOwner = this
        binding.allRecipeViewModel = model
       adapter = AdapterForAllRecipe( this, recipeList  )
       val recyclerView = binding.recyclerAllRec
        recyclerView.adapter = adapter
       val layoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
       layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        model.allRecipe.observe(viewLifecycleOwner, { recipeList -> adapter.setData(recipeList) })



        binding.floatingActionButton.setOnClickListener {





            model.saveRe(emptyRecipe)


            model.status.observe(viewLifecycleOwner,  { status ->



                   recipeId = model.recipeNumber.value!!
                   model.getNumberAsync(recipeId!!)
                   model.liveRec.observe(viewLifecycleOwner, {recipe -> chahge(recipe)}
                  )

            })






        }




        return binding.root
    }


    fun chahge(recipe:Recipe){
        newrecipe= recipe
        val action =
            newrecipe?.let { it1 ->
                AllRecipeFragmentDirections.actionAllRecipeFragmentToAddRecipeFragment2(
                    it1
                )
            }
        if (action != null) {
            findNavController().navigate(action)
        }}


    override fun onItemLongClick(position: Int) {
            val clickedItem: Recipe =  adapter.getRecipeAtPosition(position)
            var builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("What do you want todo with recipe'${clickedItem.title}'?").setNegativeButton("Edit"){ dialog, wich ->
                val actionToEditRecipe =  AllRecipeFragmentDirections.actionAllRecipeFragmentToAddRecipeFragment2(clickedItem)
                findNavController().navigate(actionToEditRecipe)


            }.setPositiveButton("Delete" ){dialog, wich ->


                model.delRecipe(clickedItem)

            }

            builder.show()

    }

    override fun onButtonClickListenet(position: Int) {
        val clickedItem: Recipe =  adapter.getRecipeAtPosition(position)
        model.delRecipe(clickedItem)
    }

    override fun onButtonEditClickListenet(position: Int) {
        val clickedItem: Recipe =  adapter.getRecipeAtPosition(position)
        val actionToEditRecipe =  AllRecipeFragmentDirections.actionAllRecipeFragmentToAddRecipeFragment2(clickedItem)
        findNavController().navigate(actionToEditRecipe)
    }


}