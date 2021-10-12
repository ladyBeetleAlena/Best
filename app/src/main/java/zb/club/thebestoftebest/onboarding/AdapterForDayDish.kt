package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_day_menu_for_recycler.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.dishbyDate
import zb.club.thebestoftebest.readymenu.TodayFragmentDirections

class AdapterForDayDish(var listDish: List<dishbyDate>): RecyclerView.Adapter<AdapterForDayDish.DishDayHolder>() {
    inner class DishDayHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishDayHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_day_menu_for_recycler, parent, false)
        return DishDayHolder(itemView)
    }

    override fun onBindViewHolder(holder: DishDayHolder, position: Int) {
     val currentItem = listDish[position]
           if (listDish.isEmpty()){holder.itemView.textViewDishTitle.setText("Hello!")}
        holder.itemView.textViewDishTitle.setText(currentItem.titleRecipe.toString())
        holder.itemView.imageViewDish.setImageURI(currentItem.picture.toUri())
        holder.itemView.textViewAdalt.setText("${currentItem.adultServing.toString()} standart portions")
        holder.itemView.textViewChild.setText("${currentItem.childrenServings.toString()} changed portions")
        holder.itemView.textViewMealDish.setText(currentItem.meal)
        holder.itemView.setOnClickListener {
           val action = TodayFragmentDirections.actionTodayFragmentToRecipeInMenuFragment(currentItem)
            holder.itemView.findNavController().navigate(action)



        }
    }

    override fun getItemCount(): Int {

        return listDish.size
    }

    fun setData(dishList: List<dishbyDate>){
        this.listDish = dishList as MutableList<dishbyDate>
        notifyDataSetChanged()
    }
}