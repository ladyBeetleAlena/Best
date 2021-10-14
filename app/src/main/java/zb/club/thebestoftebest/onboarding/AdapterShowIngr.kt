package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_show_ingr.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Ingredients

class AdapterShowIngr (var listIngredient:List<Ingredients>): RecyclerView.Adapter<AdapterShowIngr.AdapterForRecipeShowHolder>() {

        inner class AdapterForRecipeShowHolder (itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ):AdapterShowIngr.AdapterForRecipeShowHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_show_ingr, parent, false)
            return AdapterForRecipeShowHolder(itemView)
        }

        override fun onBindViewHolder(holder: AdapterForRecipeShowHolder, position: Int) {
            val currentItem = listIngredient[position]
            holder.itemView.textViewshowprod.setText(currentItem.product)
            holder.itemView.textViewweight.setText((currentItem.quantity ).toString())

        }

        override fun getItemCount(): Int {
            return listIngredient.size
        }
        fun setData(ingredient: List<Ingredients>){
            this.listIngredient = ingredient
            notifyDataSetChanged()
        }
    }
