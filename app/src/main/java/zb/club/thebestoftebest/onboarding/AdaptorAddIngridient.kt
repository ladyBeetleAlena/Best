package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_ingredient_with_bin.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Ingredients

class AdaptorAddIngridient(private var ingredientList: MutableList<Ingredients>, val listener : OnItemIngrClickListener): RecyclerView.Adapter<AdaptorAddIngridient.AddRecipeHolder>() {

    inner class AddRecipeHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
       init {
           itemView.binIngr.setOnClickListener ( this )
       }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.deleteIngClick(position)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddRecipeHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient_with_bin, parent, false)
        return AddRecipeHolder(itemView)
    }

    override fun onBindViewHolder(holder: AddRecipeHolder, position: Int) {
        val currentItem = ingredientList[position]
        holder.itemView.textViewingridientReady.setText(currentItem.product)
        holder.itemView.textViewWeightReady.setText(currentItem.quantity.toString())

    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setData(ingrList: List<Ingredients>){
        this.ingredientList = ingrList as MutableList<Ingredients>
        notifyDataSetChanged()
    }
    fun getIngrByPosition(position: Int): Ingredients{
        return ingredientList[position]
    }


    interface OnItemIngrClickListener{
        fun deleteIngClick(position: Int)
    }

}