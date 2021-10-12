package zb.club.thebestoftebest.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.DataToCurrentMenu
import zb.club.thebestoftebest.data.dishbyDate
import java.text.SimpleDateFormat

class AdapterForCurrentMenuRecycler(var listener: OnItemCurrentClickListener, var dataWithCurrentMenu: MutableList<DataToCurrentMenu>, val context: Context):RecyclerView.Adapter<AdapterForCurrentMenuRecycler.AdapterForCurrentMenuHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    inner class AdapterForCurrentMenuHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)



        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)}


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterForCurrentMenuHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return AdapterForCurrentMenuHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterForCurrentMenuHolder, position: Int) {
        holder.itemView.textViewDayInCell.text = dataWithCurrentMenu[position].day.day.toString()
        if (dataWithCurrentMenu[position].day.calendarDay.equals(0L)){holder.itemView.textViewDateInCell.visibility = View.INVISIBLE} else {val sdf = SimpleDateFormat("dd/M/yyyy")
        val calendarDate = sdf.format(dataWithCurrentMenu[position].day.calendarDay)
        holder.itemView.textViewDateInCell.text = calendarDate.toString()}
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.itemView.recyclerDishInCell.layoutManager = layoutManager
        val adapterDish = AdapterForCurrentMenuCell(dataWithCurrentMenu[position].dish as MutableList<dishbyDate>, holder.itemView.recyclerDishInCell.context)
        holder.itemView.recyclerDishInCell.setRecycledViewPool(viewPool)
        holder.itemView.recyclerDishInCell.adapter = adapterDish


    }
    interface OnItemCurrentClickListener {
        fun onItemClick(position: Int)
    }
    fun getCurrentMenuAtPosition(position: Int): DataToCurrentMenu {
        return dataWithCurrentMenu[position]

    }

    override fun getItemCount(): Int {
      return dataWithCurrentMenu.size
    }

    fun setData(dayDate: List<DataToCurrentMenu>){
        this.dataWithCurrentMenu= dayDate as MutableList<DataToCurrentMenu>
        notifyDataSetChanged()
    }
}