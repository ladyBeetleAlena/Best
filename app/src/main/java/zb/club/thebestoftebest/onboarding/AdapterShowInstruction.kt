package zb.club.thebestoftebest.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.show_instruction.view.*
import zb.club.thebestoftebest.R
import zb.club.thebestoftebest.data.Instructions

class AdapterShowInstruction(var listInstruction:List<Instructions>): RecyclerView.Adapter<AdapterShowInstruction.AdapterForInstShowHolder>() {

    inner class AdapterForInstShowHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):AdapterShowInstruction.AdapterForInstShowHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.show_instruction, parent, false)
        return AdapterForInstShowHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterForInstShowHolder, position: Int) {
        val currentItem = listInstruction[position]
        if (listInstruction.size > 1){holder.itemView.textViewStep.setText("Step ${position+1}.")
       }
        holder.itemView.textViewTextInst.setText(currentItem.text)
            if(currentItem.picture.isNullOrEmpty()){holder.itemView.imageViewStepInst.visibility = View.INVISIBLE} else {holder.itemView.imageViewStepInst.setImageURI(currentItem.picture.toUri())}

    }

    override fun getItemCount(): Int {
        return listInstruction.size
    }
    fun setData(ist: List<Instructions>){
        this.listInstruction= ist
        notifyDataSetChanged()
    }
}