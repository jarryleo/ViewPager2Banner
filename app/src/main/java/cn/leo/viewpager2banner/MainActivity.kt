package cn.leo.viewpager2banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.leo.library.decoration.IndicatorDecoration
import cn.leo.library.decoration.MarginItemDecoration
import cn.leo.library.transformer.MultiplePagerScaleInTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val imgRes =
        arrayListOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = BannerAdapter()
        adapter.data = imgRes
        banner.setAdapter(adapter)
        //设置条目与容器的左右边距
        banner.addItemDecoration(MarginItemDecoration(50))
        //设置条目的缩放和左右缩进间隔
        banner.setPageTransformer(MultiplePagerScaleInTransformer(100,0.2f))
        //设置圆点指示器
        banner.addItemDecoration(IndicatorDecoration())
    }
}
