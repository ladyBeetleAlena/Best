package zb.club.thebestoftebest.onboarding

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shoppinglistrow.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.ShoppingListWithDay
import kotlin.math.roundToInt

class AdapterForDayCurrentMenu(var dayCurrentShoppingList: List<ShoppingListWithDay>, val checkListener: ChekedProductWithDay): RecyclerView.Adapter<AdapterForDayCurrentMenu.DayCurrentMenuViewHolder>() {


    inner class DayCurrentMenuViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayCurrentMenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.shoppinglistrow, parent, false)
        return DayCurrentMenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DayCurrentMenuViewHolder, position: Int) {
        val currentItem = dayCurrentShoppingList[position]
        holder.itemView.textViewProduktShop.setText(currentItem.product)
        holder.itemView.textViewQuantShop.setText(currentItem.quantity.roundToInt().toString())

        if(currentItem.check == true){holder.itemView.checkBoxShop.isChecked = true
            holder.itemView.textViewProduktShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.textViewQuantShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG}else
        {holder.itemView.checkBoxShop.isChecked = false
            holder.itemView.textViewProduktShop.paintFlags = 0
            holder.itemView.textViewQuantShop.paintFlags =0}
        holder.itemView.checkBoxShop.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){holder.itemView.textViewProduktShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.itemView.textViewQuantShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                checkListener.onCheckedProduct(currentItem)} else{
                holder.itemView.textViewProduktShop.paintFlags = 0
                holder.itemView.textViewQuantShop.paintFlags =0
                checkListener.unChekedProduct(currentItem)

            }
        })


    }

    override fun getItemCount(): Int {
       return dayCurrentShoppingList.size
    }
    fun setData(dateList: List<ShoppingListWithDay>){
        this.dayCurrentShoppingList = dateList as MutableList<ShoppingListWithDay>
        notifyDataSetChanged()
    }
}