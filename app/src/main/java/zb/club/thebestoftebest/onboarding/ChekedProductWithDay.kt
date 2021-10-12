package zb.club.thebestoftebest.onboarding

import zb.club.thebestoftebest.data.ShoppingListWithDay

interface ChekedProductWithDay {
    fun onCheckedProduct(checkedProduct: ShoppingListWithDay)
    fun unChekedProduct(unchekedProduct: ShoppingListWithDay)
}