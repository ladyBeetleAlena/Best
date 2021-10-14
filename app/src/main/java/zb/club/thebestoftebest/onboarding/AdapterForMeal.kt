package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.meals_row.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Meal

class AdapterForMeal (var meaList: MutableList<Meal>, val listener : AdapterForMeal.OnItemMealClickListener): RecyclerView.Adapter<AdapterForMeal.MealHolder>() {
    inner class MealHolder(iteView: View): RecyclerView.ViewHolder(iteView), View.OnClickListener{
        init {
            itemView.imageViewBinMeal.setOnClickListener ( this )
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.deleteMealClick(position)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meals_row, parent, false)
        return MealHolder(itemView)
    }

    override fun onBindViewHolder(holder: MealHolder, position: Int) {
        val currentItem = meaList[position]
        holder.itemView.textViewMealAdd.setText(currentItem.meal)
    }

    override fun getItemCount(): Int {
        return meaList.size
    }

    fun setData(tagList: List<Meal>){
        this.meaList = tagList as MutableList<Meal>
        notifyDataSetChanged()
    }

    fun getMealByPosition(position: Int): Meal{
        return meaList[position]
    }


    interface OnItemMealClickListener{
        fun deleteMealClick(position: Int)
    }
}