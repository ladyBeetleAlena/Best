package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_line.view.*
import zb.club.thebestoftebest.R

class AllMenuAdapter(
    private var menuList: MutableList<String>,
    private val listenerAllMenu: AllMenuAdapter.OnItemClickListener
): RecyclerView.Adapter<AllMenuAdapter.AllMenuViewHolder>()
{
    inner class AllMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener{
    init {
        itemView.setOnLongClickListener(this)
        itemView.setOnClickListener(this)
        itemView.imageButtonDeleRowProd.setOnClickListener(this)
    }

    override fun onLongClick(v: View?): Boolean {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION){
            listenerAllMenu.OnItemLongClick(position)}
        return true
    }

        override fun onClick(v: View?) {
            when(v) {  itemView->{val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listenerAllMenu.OnItemClick(position)}}


            itemView.imageButtonDeleRowProd-> {val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listenerAllMenu.deleteClick(position)}


        }

        }}}





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMenuAdapter.AllMenuViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_line, parent, false)
        return AllMenuViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: AllMenuAdapter.AllMenuViewHolder, position: Int) {
        val currentItem = menuList[position]
        holder.itemView.textViewProductInLine.setText(currentItem.toString())
    }


    override fun getItemCount(): Int {
        return menuList.size
    }

    fun setData(menuList: List<String>) {
        this.menuList = menuList as MutableList<String>
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun OnItemClick(position: Int)

        fun OnItemLongClick(position: Int): Boolean
        fun deleteClick(position: Int)
    }
    fun getTitlByPositiom(position: Int): String {
        return menuList[position]
    }


}