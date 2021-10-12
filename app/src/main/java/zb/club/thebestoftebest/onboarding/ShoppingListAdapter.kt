package zb.club.thebestoftebest.onboarding

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shoppinglistrow.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.ShoppList
import kotlin.math.roundToInt

class ShoppingListAdapter(var listShopping: MutableList<ShoppList>, val checkListener: CheckedProductListener):
    RecyclerView.Adapter<ShoppingListAdapter.ShoppinglistHolder>() {

    inner class ShoppinglistHolder(itemView: View): RecyclerView.ViewHolder(itemView) {  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppinglistHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.shoppinglistrow, parent, false)
        return ShoppinglistHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShoppinglistHolder, position: Int) {
        val currentItem = listShopping[position]
        holder.itemView.textViewProduktShop.setText(currentItem.product)
        holder.itemView.textViewQuantShop.setText(currentItem.quantity.roundToInt().toString())


        if(currentItem.check){holder.itemView.checkBoxShop.isChecked = true
            holder.itemView.textViewProduktShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.textViewQuantShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG}else
        {holder.itemView.checkBoxShop.isChecked = false
            holder.itemView.textViewProduktShop.paintFlags = 0
            holder.itemView.textViewQuantShop.paintFlags =0}
        holder.itemView.checkBoxShop.setOnCheckedChangeListener (CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){holder.itemView.textViewProduktShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.itemView.textViewQuantShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                checkListener.onCheckedProduct(currentItem)

            } else{
                holder.itemView.textViewProduktShop.paintFlags = 0
                holder.itemView.textViewQuantShop.paintFlags =0
                checkListener.unChekedProduct(currentItem)
            }
        })

    }


    override fun getItemCount(): Int {
      return listShopping.size
    }

    fun setData(list: List<ShoppList>){
        this.listShopping = list as MutableList<ShoppList>
        notifyDataSetChanged()
    }
}