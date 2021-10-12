package zb.club.thebestoftebest.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recipe_for_menu_create.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Recipe

class AdapterForRecipeInCreateMenu(
    private val checkedListener: CheckedRecipeListener,
    private val context: Context,
    private var recipeList: MutableList<Recipe>
) : RecyclerView.Adapter<AdapterForRecipeInCreateMenu.ForRecipeViewHolder>() {

    var checkedRecipe: ArrayList<Recipe> = ArrayList()




    inner class ForRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForRecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recipe_for_menu_create,
            parent,
            false
        )
        return ForRecipeViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ForRecipeViewHolder, position: Int) {

        val recipe = recipeList[position]
        holder.itemView.textViewRecipeTitleInMenuCreateItem.setText(recipeList[position].title)
        holder.itemView.imageViewRecipeInCreateMenu.setImageURI(recipeList[position].picture?.toUri())
        holder.itemView.floatingActionButtonAddToMenu.setOnClickListener {
           checkedRecipe.clear()
           checkedRecipe.add(recipe)
           checkedListener.onCheckedRecipes(checkedRecipe)}



    }


    override fun getItemCount(): Int {
        return recipeList.size
    }



    fun setData(recipeList: List<Recipe>) {
        this.recipeList = recipeList as MutableList<Recipe>
        notifyDataSetChanged()
    }






}
