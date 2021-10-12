package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_line.view.*
import zb.club.thebestoftebest.R

class AdapterForMealInCreateMenu (private var listenerMeal: AdapterForMealInCreateMenu.OnItemMealClickListener, private var mealList: MutableList<String>): RecyclerView.Adapter<AdapterForMealInCreateMenu.MealViewHolder>() {
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
        holder.itemView.textViewProductInLine.setText("Let's plan ${currentItem}")


    }

    override fun getItemCount(): Int {
        return mealList.size
    }
    fun setData(mealList: List<String>){
        this.mealList = mealList as MutableList<String>
        notifyDataSetChanged()
    }

    interface OnItemMealClickListener{
        fun onItemMealClick(position: Int)
    }
    fun getMealAtPosition(position: Int): String {
        return mealList[position]
    }
}