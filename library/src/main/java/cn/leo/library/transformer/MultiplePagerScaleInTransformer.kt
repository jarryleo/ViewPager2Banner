package cn.leo.library.transformer

import android.os.Build
import android.view.View
import androidx.annotation.Px
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * Created by wangpeiyuan on 2019-12-03.
 */
@Suppress("UNUSED", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class MultiplePagerScaleInTransformer(
    @param:Px private val margin: Int,
    private val scale: Float = 1f
) : ViewPager2.PageTransformer {
    private var mViewPager2: ViewPager2? = null
    override fun transformPage(
        page: View,
        position: Float
    ) {
        mViewPager2 = mViewPager2 ?: requireViewPager(page)
        val offset = position * margin
        if (mViewPager2?.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(mViewPager2!!) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = offset
            } else {
                page.translationX = -offset
            }
            page.scaleY = 1 - scale * abs(position)
        } else {
            page.translationY = -offset
            page.scaleX = 1 - scale * abs(position)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            page.elevation = 1 - scale * abs(position)
        }
    }

    private fun requireViewPager(page: View): ViewPager2 {
        val parent = page.parent
        val parentParent = parent.parent
        if (parent is RecyclerView && parentParent is ViewPager2) {
            return parentParent
        }
        throw IllegalStateException(
            "Expected the page view to be managed by a ViewPager2 instance."
        )
    }

}