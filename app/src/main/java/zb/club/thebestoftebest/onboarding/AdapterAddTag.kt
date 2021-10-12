package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.meals_row.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.RecipeByTag

class AdapterAddTag(var tagList: MutableList<RecipeByTag>, val listener : AdapterAddTag.OnItemMealClickListener): RecyclerView.Adapter<AdapterAddTag.TagHolder>() {
    inner class TagHolder(iteView: View):RecyclerView.ViewHolder(iteView), View.OnClickListener{
        init {
            itemView.imageViewBinMeal.setOnClickListener ( this )
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.deleteMealClick(position)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meals_row, parent, false)
        return TagHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        val currentItem = tagList[position]
       holder.itemView.textViewMealAdd.setText(currentItem.tag)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    fun setData(tagList: List<RecipeByTag>){
        this.tagList = tagList as MutableList<RecipeByTag>
        notifyDataSetChanged()
    }

    fun getMealByPosition(position: Int): RecipeByTag {
        return tagList[position]
    }


    interface OnItemMealClickListener{
        fun deleteMealClick(position: Int)
    }
}