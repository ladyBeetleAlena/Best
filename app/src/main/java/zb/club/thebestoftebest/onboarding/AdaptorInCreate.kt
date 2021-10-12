package zb.club.thebestoftebest.onboarding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.selected_recipe_show.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.MenuTemp
import java.text.SimpleDateFormat

class AdaptorInCreate(private val listener: AdaptorInCreate.OnItemClickListener, var menuTemp: List<MenuTemp>  ): RecyclerView.Adapter<AdaptorInCreate.CreateMenuDayMealViewHolder>() {

    inner class CreateMenuDayMealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.imageViewBinRow.setOnClickListener(this)




        }

        override fun onClick(v: View?) {
            when(v){
                itemView ->  {val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position)}}

                itemView.imageViewBinRow -> {val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        listener.delete(position)

                    }}
            }



        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun delete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateMenuDayMealViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.selected_recipe_show, parent, false)
        return CreateMenuDayMealViewHolder(itemView)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: CreateMenuDayMealViewHolder, position: Int) {


        holder.itemView.titleRecipeInSelect.setText(menuTemp[position].titleRecipe)
        holder.itemView.textViewcountAdultinSelect.setText(menuTemp[position].adultServing.toString())
        holder.itemView.textViewCountChildInSelect.setText(menuTemp[position].childrenServings.toString())
        holder.itemView.textViewMealInSelect.setText(menuTemp[position].meal)
        if (menuTemp[position].calendarDay.equals(0L) == false ){holder.itemView.textViewShowDate.setText(
            SimpleDateFormat("dd/M/yyyy").format(menuTemp[position].calendarDay))} else
        {holder.itemView.textViewShowDate.visibility = View.INVISIBLE}


    }


    override fun getItemCount(): Int {
        return menuTemp.size
    }
    fun setData(menuTemp: List<MenuTemp>){
        this.menuTemp = menuTemp
        notifyDataSetChanged()
    }

    fun getCurrentMenuAtPosition(position: Int): MenuTemp {
        return menuTemp[position]

    }


}