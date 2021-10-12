package zb.club.thebestoftebest.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.DayWithDishMenu
import zb.club.thebestoftebest.data.ListForDate

class AdaptorForMenuRecycler(val listenerMenu: OnItemMenuClickListener, var dataWithCurrentMenu: MutableList<DayWithDishMenu>, val context: Context):
    RecyclerView.Adapter<AdaptorForMenuRecycler.AdapterForMenuHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    inner class AdapterForMenuHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)



        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listenerMenu.onItemClick(position)}


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptorForMenuRecycler.AdapterForMenuHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return AdapterForMenuHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterForMenuHolder, position: Int) {
        holder.itemView.textViewDayInCell.text = "day ${dataWithCurrentMenu[position].day.toString()}"
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.itemView.recyclerDishInCell.layoutManager = layoutManager
        val adapterDish = AdaptorForMenuCell(dataWithCurrentMenu[position].dishForDate as MutableList<ListForDate>, holder.itemView.recyclerDishInCell.context)
        holder.itemView.recyclerDishInCell.setRecycledViewPool(viewPool)
        holder.itemView.recyclerDishInCell.adapter = adapterDish
        holder.itemView.textViewDateInCell.visibility = View.INVISIBLE

    }

    override fun getItemCount(): Int {
        return dataWithCurrentMenu.size
    }
    interface OnItemMenuClickListener {
        fun onItemClick(position: Int)
    }
    fun getMenuAtPosition(position: Int): DayWithDishMenu {
        return dataWithCurrentMenu[position]

    }
    fun setData(dayDate: List<DayWithDishMenu>){
        this.dataWithCurrentMenu= dayDate as MutableList<DayWithDishMenu>
        notifyDataSetChanged()
    }
}