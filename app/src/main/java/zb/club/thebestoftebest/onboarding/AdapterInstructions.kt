package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_instruction.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Instructions

class AdapterInstructions (var instructionList: MutableList<Instructions>, val listener : AdapterInstructions.InstructinListwener): RecyclerView.Adapter<AdapterInstructions.InstructinHolder>() {
    inner class InstructinHolder(iteView: View): RecyclerView.ViewHolder(iteView), View.OnClickListener{
        init {
            itemView.imageViewbinInst.setOnClickListener ( this )
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.deleteInstructionClick(position)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterInstructions.InstructinHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        return InstructinHolder(itemView)
    }

    override fun onBindViewHolder(holder: InstructinHolder, position: Int) {
        val currentItem = instructionList[position]
        if(instructionList.size > 1){
        holder.itemView.textViewItemsNumber.setText("${position+1}.")}
        holder.itemView.textViewInstruction.setText(currentItem.text)
        if(currentItem.picture !=null) {holder.itemView.imageViewInstruction.setImageURI(currentItem.picture.toUri())
            holder.itemView.imageViewInstruction.visibility = View.VISIBLE
        } else {holder.itemView.imageViewInstruction.visibility = View.INVISIBLE}
    }

    override fun getItemCount(): Int {
        return instructionList.size
    }

    fun setData(instList: List<Instructions>){
        this.instructionList = instList as MutableList<Instructions>
        notifyDataSetChanged()
    }

    fun getInstByPosition(position: Int): Instructions {
        return instructionList[position]
    }


    interface InstructinListwener{
        fun deleteInstructionClick(position: Int)
    }
}