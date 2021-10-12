package zb.club.thebestoftebest.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler_cell.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.dishbyDate

class AdapterForCurrentMenuCell(var dishInDay : MutableList<dishbyDate>, context: Context):RecyclerView.Adapter<AdapterForCurrentMenuCell.AdapterInCellHolder>() {

    inner class AdapterInCellHolder(itemView:View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInCellHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_cell, parent, false)
        return AdapterInCellHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterInCellHolder, position: Int) {
        holder.itemView.textViewDishInCell.text = dishInDay[position].titleRecipe
        holder.itemView.textViewAdultInCell.text = dishInDay[position].adultServing.toString()
        holder.itemView.textViewChildInCell.text = dishInDay[position].childrenServings.toString()

    }

    override fun getItemCount(): Int {
       return dishInDay.size
    }
    fun setData(dishInDate: MutableList<dishbyDate>){
        this.dishInDay = dishInDate
        notifyDataSetChanged()
    }
}