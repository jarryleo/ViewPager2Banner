package cn.leo.library.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by wangpeiyuan on 2019-12-03.
 */
class MarginItemDecoration(private val marginPx: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val linearLayoutManager = requireLinearLayoutManager(parent)
        if (linearLayoutManager.orientation == LinearLayoutManager.VERTICAL) {
            outRect.top = marginPx
            outRect.bottom = marginPx
        } else {
            outRect.left = marginPx
            outRect.right = marginPx
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