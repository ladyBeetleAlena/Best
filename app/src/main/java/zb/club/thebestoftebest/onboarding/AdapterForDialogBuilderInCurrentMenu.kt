package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.selected_recipe_show.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.CurrentMenu

class AdapterForDialogBuilderInCurrentMenu(val listenerInCurrentDialog:ListenerInCurrentDialog, var listCurrentMenu: List<CurrentMenu>): RecyclerView.Adapter<AdapterForDialogBuilderInCurrentMenu.HolderForAdapterInBuilder>() {
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
    fun setData(currentMenu: List<CurrentMenu>){
        this.listCurrentMenu = currentMenu
        notifyDataSetChanged()
    }

    fun getCurrentMenuItemAtPosition(position: Int): CurrentMenu {
        return listCurrentMenu[position]

    }
    interface ListenerInCurrentDialog{
        fun onItemClic(position: Int)
    }

}