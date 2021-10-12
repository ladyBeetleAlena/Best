package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.thing_for_delete.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Things

class AdaptofForDialogInShopRecycler(val listenerInDialog:OnItemDialogClickListener , private var thingList: MutableList<Things>): RecyclerView.Adapter<AdaptofForDialogInShopRecycler.DialogViewHolder>() {
    inner class DialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.imageViewDeleteThings.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listenerInDialog.onItemProductClick(position)
            }


        }}


    interface OnItemDialogClickListener {
        fun onItemProductClick(position: Int)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.thing_for_delete, parent, false)
        return DialogViewHolder(itemView)}

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        val currentItem = thingList[position]
        holder.itemView.textViewTingForDelete.setText(currentItem.thing)
    }



    override fun getItemCount(): Int {
            return thingList.size
        }

    fun setData(thngList: List<Things>) {
            this.thingList = thngList as MutableList<Things>
            notifyDataSetChanged()
        }



    fun getProductAtPosition(position: Int): Things {
            return thingList[position]
        }
    }

