package cn.leo.library.decoration

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * @author : Jarry Leo
 * @date : 2018/11/19 16:27
 */
@Suppress("UNUSED", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class IndicatorDecoration(private val isInfinite: Boolean = true) : ItemDecoration() {
    @Align
    private var mAlign = ALIGN_BOTTOM

    @IntDef(
        value = [ALIGN_LEFT, ALIGN_RIGHT, ALIGN_TOP, ALIGN_BOTTOM],
        flag = true
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Align

    private var mSelectedSize = dip2px(7f)
    private var mUnselectedSize = dip2px(7f)
    private var mSelectedColor = Color.RED
    private var mUnselectedColor = Color.WHITE
    private var mSpace = dip2px(6f)
    private var mVerticalPadding = dip2px(16f)
    private var mHorizontalPadding = dip2px(16f)
    private val mPaint =
        Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * RecyclerView 的每次滚动都会调用，适合做滑动动画
     */
    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        //宽高
        val width = parent.measuredWidth
        val height = parent.measuredHeight
        //条目个数
        val itemCount = if (isInfinite) {
            layoutManager!!.itemCount - 2
        } else {
            layoutManager!!.itemCount
        }
        //当前显示的所有条目
        val childCount = layoutManager.childCount
        val centerX = width / 2
        val centerY = height / 2
        val rect = Rect()
        //中心位置的条目position
        var centerItemPosition = 0
        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            layoutManager.getDecoratedBoundsWithMargins(child!!, rect)
            if (rect.contains(centerX, centerY)) {
                centerItemPosition = layoutManager.getPosition(child)
                break
            }
        }
        if (isInfinite) {
            centerItemPosition = fixPosition(centerItemPosition, layoutManager.itemCount)
        }
        //指示器总体宽度
        val indicatorWidth = mUnselectedSize * itemCount + mSpace * (itemCount - 1) +
                mSelectedSize - mUnselectedSize
        //指示器位置
        var indicatorLeft = (width - indicatorWidth) / 2
        if (mAlign and ALIGN_LEFT == ALIGN_LEFT) {
            indicatorLeft = mVerticalPadding
        }
        if (mAlign and ALIGN_RIGHT == ALIGN_RIGHT) {
            indicatorLeft = width - indicatorWidth - mVerticalPadding
        }
        var indicatorTop = (height - mUnselectedSize) / 2
        if (mAlign and ALIGN_TOP == ALIGN_TOP) {
            indicatorTop = mHorizontalPadding
        }
        if (mAlign and ALIGN_BOTTOM == ALIGN_BOTTOM) {
            indicatorTop = height - mUnselectedSize - mVerticalPadding
        }
        //绘制指示器
        for (i in 0 until itemCount) {
            var r = mUnselectedSize
            if (centerItemPosition == i) { //选中条目指示器颜色
                mPaint.color = mSelectedColor
                r = mSelectedSize
            } else {
                mPaint.color = mUnselectedColor
            }
            val left = i * (mUnselectedSize + mSpace) + indicatorLeft
            val cx = left + mUnselectedSize / 2
            val cy = indicatorTop + mUnselectedSize / 2
            c.drawCircle(cx.toFloat(), cy.toFloat(), r / 2f, mPaint)
        }
    }

    private fun fixPosition(position: Int, count: Int): Int {
        var realPosition = position
        if (count > 1) {
            if (position > 0 && position < count - 1) {
                realPosition = position - 1
            } else if (position == 0) {
                realPosition = count - 3
            } else if (position == count - 1) {
                realPosition = 0
            }
        }
        return realPosition
    }

    fun setSelectedSize(selectedSize: Int) {
        mSelectedSize = dip2px(selectedSize.toFloat())
    }

    fun setUnselectedSize(unselectedSize: Int) {
        mUnselectedSize = dip2px(unselectedSize.toFloat())
    }

    fun setSelectedColor(selectedColor: Int) {
        mSelectedColor = selectedColor
    }

    fun setUnselectedColor(unselectedColor: Int) {
        mUnselectedColor = unselectedColor
    }

    fun setSpace(space: Int) {
        mSpace = dip2px(space.toFloat())
    }

    fun setVerticalPadding(verticalPadding: Int) {
        mVerticalPadding = dip2px(verticalPadding.toFloat())
    }

    fun setHorizontalPadding(horizontalPadding: Int) {
        mHorizontalPadding = dip2px(horizontalPadding.toFloat())
    }

    fun setAlign(@Align align: Int) {
        mAlign = align
    }

    private fun dip2px(dpValue: Float): Int {
        val scale =
            Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    companion object {
        /**
         * 待完善：
         * 指示器设置支持drawable文件和图片
         */
        const val ALIGN_LEFT = 1
        const val ALIGN_RIGHT = 1 shl 1
        const val ALIGN_TOP = 1 shl 2
        const val ALIGN_BOTTOM = 1 shl 3
    }
}