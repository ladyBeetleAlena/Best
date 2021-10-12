package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.selected_recipe_show.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Menu

class AdapterForDialogBuilderInMenu (val listenerInCurrentDialog: AdapterForDialogBuilderInMenu.ListenerInCurrentDialog, var listCurrentMenu: List<Menu>): RecyclerView.Adapter<AdapterForDialogBuilderInMenu.HolderForAdapterInBuilder>() {
    inner class HolderForAdapterInBuilder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)



        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listenerInCurrentDialog.onItemClic(position)}


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderForAdapterInBuilder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.selected_recipe_show, parent, false)
        return HolderForAdapterInBuilder(itemView)
    }

    override fun onBindViewHolder(holder: HolderForAdapterInBuilder, position: Int) {
        holder.itemView.titleRecipeInSelect.setText(listCurrentMenu[position].titleRecipe)
        holder.itemView.textViewcountAdultinSelect.setText(listCurrentMenu[position].adultServing.toString())
        holder.itemView.textViewCountChildInSelect.setText(listCurrentMenu[position].childrenServings.toString())
    }

    override fun getItemCount(): Int {
        return listCurrentMenu.size
    }
    fun setData(currentMenu: List<Menu>){
        this.listCurrentMenu = currentMenu
        notifyDataSetChanged()
    }

    fun getCurrentMenuItemAtPosition(position: Int): Menu {
        return listCurrentMenu[position]

    }
    interface ListenerInCurrentDialog{
        fun onItemClic(position: Int)
    }

}