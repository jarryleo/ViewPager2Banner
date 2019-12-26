package cn.leo.library.support

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cn.leo.library.ViewPager2Banner
import cn.leo.library.decoration.MarginItemDecoration

/**
 * @author : ling luo
 * @date : 2019-12-26
 * banner配置拓展
 */

fun ViewPager2Banner.config(config: BannerConfig.() -> Unit) {
    val bannerConfig = BannerConfig()
    config(bannerConfig)
    setOrientation(bannerConfig.orientation)
    bannerConfig.adapter?.let {
        setAdapter(it)
    }
    bannerConfig.transformer?.let {
        setPageTransformer(it)
    }

    if (bannerConfig.itemMargin != 0) {
        addItemDecoration(MarginItemDecoration(bannerConfig.itemMargin))
    }
    bannerConfig.indicator?.let {
        addItemDecoration(it)
    }
}

class BannerConfig {
    var itemMargin: Int = 0
    var adapter: RecyclerView.Adapter<*>? = null
    var indicator: RecyclerView.ItemDecoration? = null
    var transformer: ViewPager2.PageTransformer? = null
    var orientation: Int = ViewPager2.ORIENTATION_HORIZONTAL
}