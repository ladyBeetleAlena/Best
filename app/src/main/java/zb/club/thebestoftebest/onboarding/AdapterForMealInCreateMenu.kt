package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_line.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Meal

class AdapterForMealInCreateMenu (private var listenerMeal: AdapterForMealInCreateMenu.OnItemMealClickListener, private var mealList: MutableList<Meal>): RecyclerView.Adapter<AdapterForMealInCreateMenu.MealViewHolder>() {
    inner class MealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listenerMeal.onItemMealClick(position)}



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):AdapterForMealInCreateMenu.MealViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_line, parent, false)
        return MealViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterForMealInCreateMenu.MealViewHolder, position: Int) {
        val currentItem = mealList[position]
        holder.itemView.textViewProductInLine.setText("Let's plan ${currentItem.meal}")


    }

    override fun getItemCount(): Int {
        return mealList.size
    }
    fun setData(mealList: List<Meal>){
        this.mealList = mealList as MutableList<Meal>
        notifyDataSetChanged()
    }

    interface OnItemMealClickListener{
        fun onItemMealClick(position: Int)
    }
    fun getMealAtPosition(position: Int): Meal{
        return mealList[position]
    }
}