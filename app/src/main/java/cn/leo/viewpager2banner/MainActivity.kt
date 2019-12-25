package cn.leo.viewpager2banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import cn.leo.library.decoration.IndicatorDecoration
import cn.leo.library.decoration.MarginItemDecoration
import cn.leo.library.transformer.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val imgRes =
        arrayListOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four)

    private val textRes by lazy { resources.getStringArray(R.array.text_banner) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = BannerAdapter()
        adapter.data = imgRes
        banner.setAdapter(adapter)
        //设置条目与容器的左右边距
        banner.addItemDecoration(MarginItemDecoration(50))
        //设置条目的缩放和左右缩进间隔（左右条目缩小漏出效果）
        //banner.setPageTransformer(MultiplePagerScaleInTransformer(100,0.2f))
        //层叠渐变效果
        banner.setPageTransformer(DepthPageTransformer())
        //下沉渐变效果
        //banner.setPageTransformer(ZoomOutPageTransformer())
        //设置圆点指示器
        banner.addItemDecoration(IndicatorDecoration())

        //文字上下滚动广告
        val textBannerAdapter = TextBannerAdapter()
        textBannerAdapter.data = textRes.toList()
        textBanner.setAdapter(textBannerAdapter)
        textBanner.setOrientation(ViewPager2.ORIENTATION_VERTICAL)
    }
}
