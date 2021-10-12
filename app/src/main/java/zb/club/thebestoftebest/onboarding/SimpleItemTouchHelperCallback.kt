package zb.club.thebestoftebest.onboarding

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class SimpleItemTouchHelperCallback(adapter: AdapterForRecipeInCreateMenu) : ItemTouchHelper.Callback() {
    private var mAdapter: ItemTouchHelperAdapter? = null

    fun SimpleItemTouchHelperCallback(adapter: ItemTouchHelperAdapter?) {
        mAdapter = adapter
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        mAdapter!!.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }





    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        mAdapter!!.onItemDismiss(viewHolder.adapterPosition)
    }
}