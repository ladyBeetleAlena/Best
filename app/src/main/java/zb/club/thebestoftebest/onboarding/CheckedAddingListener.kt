package zb.club.thebestoftebest.onboarding

import zb.club.thebestoftebest.data.ShoppingAdding

interface CheckedAddingListener {
    fun onCheckedThing(checkeThing: ShoppingAdding)
    fun unChekedThing(unchekedThing: ShoppingAdding)
}