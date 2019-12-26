package cn.leo.library.decoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by wangpeiyuan on 2019-12-03.
 */
class MarginItemDecoration(@param:Px private val margin: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val linearLayoutManager = requireLinearLayoutManager(parent)
        if (linearLayoutManager.orientation == LinearLayoutManager.VERTICAL) {
            outRect.top = margin
            outRect.bottom = margin
        } else {
            outRect.left = margin
            outRect.right = margin
        }
    }

    private fun requireLinearLayoutManager(parent: RecyclerView): LinearLayoutManager {
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            return layoutManager
        }
        throw IllegalStateException("The layoutManager must be LinearLayoutManager")
    }

}