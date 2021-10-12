package zb.club.thebestoftebest.onboarding

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shoppinglistrow.view.checkBoxShop
import kotlinx.android.synthetic.main.shoppinglistrow.view.textViewProduktShop
import kotlinx.android.synthetic.main.shoppinglistrow.view.textViewQuantShop
import kotlinx.android.synthetic.main.thingitem.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.ShoppingAdding

class ShoppingAddingListAdapter(var listShopping: MutableList<ShoppingAdding>, val checkListener: CheckedAddingListener, private val listener: ShoppingAddingListAdapter.OnItemClickListener):
    RecyclerView.Adapter<ShoppingAddingListAdapter.ShoppinglistHolder>() {

    inner class ShoppinglistHolder(itemView: View): RecyclerView.ViewHolder(itemView),   View.OnClickListener {
        init {
            itemView.imageViewBin.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)}


        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppinglistHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.thingitem, parent, false)
        return ShoppinglistHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShoppinglistHolder, position: Int) {
        val currentItem = listShopping[position]
        holder.itemView.textViewProduktShop.setText(currentItem.thing)
        if(currentItem.quantity.equals(0)){holder.itemView.textViewQuantShop.visibility = View.INVISIBLE}else{
            holder.itemView.textViewQuantShop.visibility = View.VISIBLE
        holder.itemView.textViewQuantShop.setText(currentItem.quantity.toString())}


        if(currentItem.check == true){holder.itemView.checkBoxShop.isChecked = true
            holder.itemView.textViewProduktShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.textViewQuantShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG}else
        {holder.itemView.checkBoxShop.isChecked = false
            holder.itemView.textViewProduktShop.paintFlags = 0
            holder.itemView.textViewQuantShop.paintFlags =0}
        holder.itemView.checkBoxShop.setOnCheckedChangeListener (CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){holder.itemView.textViewProduktShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.itemView.textViewQuantShop.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                checkListener.onCheckedThing(currentItem)

            } else{
                holder.itemView.textViewProduktShop.paintFlags = 0
                holder.itemView.textViewQuantShop.paintFlags =0
                checkListener.unChekedThing(currentItem)
            }
        })

    }
    fun getItemAtPosition(position: Int): ShoppingAdding {
        return listShopping[position]
    }

    override fun getItemCount(): Int {
        return listShopping.size
    }

    fun setData(list: List<ShoppingAdding>){
        this.listShopping = list as MutableList<ShoppingAdding>
        notifyDataSetChanged()
    }
}