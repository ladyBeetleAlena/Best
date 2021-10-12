package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_show_ingr.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Ingredients
import kotlin.math.roundToInt

class AdapterForRecipeInMenu(var listIngredient:List<Ingredients>, var adult: Int, var child:Int, var adChK :Double): RecyclerView.Adapter<AdapterForRecipeInMenu.AdapterForRecipeInMenuHolder>() {

    inner class AdapterForRecipeInMenuHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterForRecipeInMenuHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_show_ingr, parent, false)
        return AdapterForRecipeInMenuHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterForRecipeInMenuHolder, position: Int) {
        val currentItem = listIngredient[position]
        holder.itemView.textViewshowprod.setText(currentItem.product)
        holder.itemView.textViewweight.setText((currentItem.quantity*adult + currentItem.quantity*child*adChK).roundToInt().toString())

    }

    override fun getItemCount(): Int {
        return listIngredient.size
    }
    fun setData(ingredient: List<Ingredients>){
        this.listIngredient = ingredient
        notifyDataSetChanged()
    }
}