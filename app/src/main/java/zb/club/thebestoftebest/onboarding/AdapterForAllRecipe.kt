package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_off_all.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.cookbook.AllRecipeFragmentDirections
import zb.club.thebestoftebest.data.Recipe

class AdapterForAllRecipe(private val listener: OnItemLongClickListener, private var recipeList: MutableList<Recipe> ): RecyclerView.Adapter<AdapterForAllRecipe.ViewHolderForAllRec>() {



    inner class ViewHolderForAllRec(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnLongClickListener, View.OnClickListener {
        init {
            itemView.setOnLongClickListener(this)
            itemView.imageButtonDeleteRecipe.setOnClickListener(this)
            itemView.imageButtonEdit.setOnClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
            listener.onItemLongClick(position)}
          return true


        }

        override fun onClick(v: View?) {
            when(v){
            itemView.imageButtonEdit ->{val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onButtonEditClickListenet(position)}}
            itemView.imageButtonDeleteRecipe ->{val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onButtonClickListenet(position)}}


            }

        }
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
        fun onButtonClickListenet(position: Int)
        fun onButtonEditClickListenet(position: Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterForAllRecipe.ViewHolderForAllRec {

            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.one_off_all, parent, false)
        return ViewHolderForAllRec(itemView)


    }


    override fun onBindViewHolder(holder: AdapterForAllRecipe.ViewHolderForAllRec, position: Int) {
        val currentItem = recipeList[position]
        holder.itemView.pictureFoodAllRec.setImageURI(currentItem.picture?.toUri())
        holder.itemView.pictureFoodAllRec.setBackgroundResource(R.drawable.shapeimage)
        holder.itemView.TitleRecipeAllRec.setText(currentItem.title.toString())

        holder.itemView.pictureFoodAllRec.clipToOutline = true

        holder.itemView.setOnClickListener {  val action = AllRecipeFragmentDirections.actionAllRecipeFragmentToAddRecipeFragment2(currentItem)
            holder.itemView.findNavController().navigate(action) }
    }



    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun setData(recipe: List<Recipe>){
        this.recipeList = recipe as MutableList<Recipe>
        notifyDataSetChanged()
    }
    fun getRecipeAtPosition(position: Int): Recipe {
        return recipeList[position]
    }

}



