package cn.leo.library

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.os.PowerManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * @author : ling luo
 * @date : 2019-12-25
 */
@Suppress("UNUSED", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class ViewPager2Banner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var mCurrentPosition = RecyclerView.NO_POSITION
    private var mWrapperAdapter: WrapperAdapter<*>? = null
    private val mAutoSwitchRunnable = AutoSwitchRunnable()
    /**
     * 自动滚动间隔
     */
    var interval = 3000L
    private var mIsAutoSwitch = true
    private val mViewPager2 by lazy { ViewPager2(context) }

    init {
        mViewPager2.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mViewPager2.offscreenPageLimit = 1
        mViewPager2.registerOnPageChangeCallback(PageFixCallback())
        addView(mViewPager2)
    }

    inner class PageFixCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            mCurrentPosition = position
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                if (getWrapperCount() > 1 && mCurrentPosition == getWrapperCount() - 1) {
                    setCurrentItem(1, false)
                } else if (mCurrentPosition == 0) {
                    setCurrentItem(getWrapperCount() - 2, false)
                }
            }
        }
    }

    inner class AutoSwitchRunnable : Runnable {
        override fun run() {
            if (mIsAutoSwitch && isVisible()) {
                setCurrentItem(getNextPosition())
            }
            nextPage()
        }
    }

    fun startAutoSwitch() {
        mIsAutoSwitch = true
        nextPage()
    }

    fun stopAutoSwitch() {
        mIsAutoSwitch = false
        removeCallbacks(mAutoSwitchRunnable)
    }

    private fun nextPage() {
        removeCallbacks(mAutoSwitchRunnable)
        postDelayed(mAutoSwitchRunnable, interval)
    }

    fun getNextPosition(): Int {
        return mCurrentPosition + 1
    }

    inner class WrapperAdapter<VH : RecyclerView.ViewHolder>
        (private val adapter: RecyclerView.Adapter<VH>) :
        RecyclerView.Adapter<VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return adapter.onCreateViewHolder(parent, viewType)
        }

        override fun getItemViewType(position: Int): Int {
            return adapter.getItemViewType(fixPosition(position))
        }

        override fun getItemCount(): Int {
            return if (adapter.itemCount > 1) {
                adapter.itemCount + 2
            } else {
                adapter.itemCount
            }
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            adapter.onBindViewHolder(holder, fixPosition(position))
        }

        fun getRealCount() = adapter.itemCount

        fun getRealPosition(): Int {
            return fixPosition(mCurrentPosition)
        }

        private fun fixPosition(position: Int): Int {
            var realPosition = position
            if (adapter.itemCount > 1) {
                if (position > 0 && position < itemCount - 1) {
                    realPosition = position - 1
                } else if (position == 0) {
                    realPosition = adapter.itemCount - 1
                } else if (position == itemCount - 1) {
                    realPosition = 0
                }
            }
            println("position = $position  realPosition = $realPosition")
            return realPosition
        }
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        mWrapperAdapter = WrapperAdapter(adapter)
        mViewPager2.adapter = mWrapperAdapter
        mCurrentPosition = 1
        startAutoSwitch()
        post { setCurrentItem(1, false) }
    }

    fun getPosition() = mWrapperAdapter?.getRealPosition() ?: RecyclerView.NO_POSITION

    fun getCount() = mWrapperAdapter?.getRealCount() ?: 0

    private fun getWrapperCount() = mWrapperAdapter?.itemCount ?: 0

    fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? = mViewPager2.adapter

    fun addItemDecoration(decor: RecyclerView.ItemDecoration) {
        mViewPager2.addItemDecoration(decor)
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration, index: Int) {
        mViewPager2.addItemDecoration(decor, index)
    }

    fun setPageTransformer(transformer: ViewPager2.PageTransformer) {
        mViewPager2.setPageTransformer(transformer)
    }

    fun getViewPager2() = mViewPager2

    fun setOrientation(@ViewPager2.Orientation orientation: Int) {
        mViewPager2.orientation = orientation
    }

    @ViewPager2.Orientation
    fun getOrientation() = mViewPager2.orientation

    fun setCurrentItem(item: Int, smoothScroll: Boolean = true) {
        mViewPager2.setCurrentItem(item, smoothScroll)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.actionMasked
        if (action == MotionEvent.ACTION_DOWN) {
            stopAutoSwitch()
        } else if (action == MotionEvent.ACTION_UP ||
            action == MotionEvent.ACTION_CANCEL ||
            action == MotionEvent.ACTION_OUTSIDE
        ) {
            startAutoSwitch()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isVisible(): Boolean {
        if (!mViewPager2.isShown) {
            return false
        }
        val pm = context
            .getSystemService(Context.POWER_SERVICE) as PowerManager
        val isScreenOn = pm.isInteractive
        if (!isScreenOn) {
            return false
        }
        val rect = Rect()
        val visibility: Boolean = mViewPager2.getLocalVisibleRect(rect)
        val visibleArea = rect.width() * rect.height()
        val area = width * height
        val v = visibleArea * 1f / area
        //可见面积小于50%不滚动
        return if (v < 0.5f) {
            false
        } else {
            visibility
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            mCurrentItem = mCurrentPosition
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            mCurrentPosition = state.mCurrentItem
            super.onRestoreInstanceState(state.superState)
            setCurrentItem(mCurrentPosition, false)
            startAutoSwitch()
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    class SavedState : BaseSavedState {
        var mCurrentItem = 0

        constructor(source: Parcel) : super(source) {
            readValues(source)
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            readValues(source)
        }

        constructor(superState: Parcelable?) : super(superState)

        private fun readValues(source: Parcel) {
            mCurrentItem = source.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(mCurrentItem)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}