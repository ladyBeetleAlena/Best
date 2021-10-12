package zb.club.thebestoftebest.onboarding

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}