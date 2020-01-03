package cn.leo.library.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import cn.leo.library.annotation.Align.BOTTOM
import cn.leo.library.annotation.Align.LEFT
import cn.leo.library.annotation.Align.RIGHT
import cn.leo.library.annotation.Align.TOP
import cn.leo.library.annotation.IndicatorAlign
import cn.leo.library.support.dp
import kotlin.math.max

/**
 * @author : Jarry Leo
 * @date : 2018/11/19 16:27
 */
@Suppress("UNUSED", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class DrawableIndicatorDecoration(
    //选中/非选中图片
    private var selectedDrawable: Drawable,
    private var unselectedDrawable: Drawable,
    //是否是无限循环
    private val isInfinite: Boolean = true,
    //指示器位置，可以用 ALIGN_BOTTOM or ALIGN_RIGHT 来表示右下
    @IndicatorAlign
    private var align: Int = BOTTOM,
    //间距
    private var space: Int = 6.dp,
    //竖向边距
    private var verticalMargin: Int = 16.dp,
    //横向边距
    private var horizontalMargin: Int = 16.dp
) : ItemDecoration() {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * RecyclerView 的每次滚动都会调用
     */
    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        //banner宽高
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
        val unselectedBounds = unselectedDrawable.bounds
        val selectedBounds = selectedDrawable.bounds
        unselectedBounds.right = unselectedDrawable.intrinsicWidth
        unselectedBounds.bottom = unselectedDrawable.intrinsicHeight
        selectedBounds.right = selectedDrawable.intrinsicWidth
        selectedBounds.bottom = selectedDrawable.intrinsicHeight
        //指示器总体宽度
        val indicatorWidth = unselectedBounds.width() * itemCount +
                space * (itemCount - 1) + selectedBounds.width() - unselectedBounds.width()
        val indicatorHeight = max(unselectedBounds.height(), selectedBounds.height())
        //指示器位置
        var indicatorLeft = (width - indicatorWidth) / 2
        if (align and LEFT == LEFT) {
            indicatorLeft = horizontalMargin
        }
        if (align and RIGHT == RIGHT) {
            indicatorLeft = width - indicatorWidth - horizontalMargin
        }
        var indicatorTop = (height - indicatorHeight) / 2
        if (align and TOP == TOP) {
            indicatorTop = verticalMargin
        }
        if (align and BOTTOM == BOTTOM) {
            indicatorTop = height - indicatorHeight - verticalMargin
        }
        //绘制指示器
        for (i in 0 until itemCount) {
            val left = i * (unselectedBounds.width() + space) + indicatorLeft
            c.save()
            if (centerItemPosition == i) {
                val x = left.toFloat() -
                        (selectedBounds.width() - unselectedBounds.width()) / 2f
                val y = indicatorTop - selectedBounds.height() / 2f
                c.translate(x, y)
                selectedDrawable.draw(c)
            } else {
                val x = left.toFloat()
                val y = indicatorTop - unselectedBounds.height() / 2f
                c.translate(x, y)
                unselectedDrawable.draw(c)
            }
            c.restore()
        }
    }

    //修复无限循环的banner条目位置
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

    fun setSpace(space: Int) {
        this.space = space.dp
    }

    fun setVerticalPadding(verticalPadding: Int) {
        this.verticalMargin = verticalPadding.dp
    }

    fun setHorizontalPadding(horizontalPadding: Int) {
        this.horizontalMargin = horizontalPadding.dp
    }

    fun setAlign(@IndicatorAlign align: Int) {
        this.align = align
    }

}