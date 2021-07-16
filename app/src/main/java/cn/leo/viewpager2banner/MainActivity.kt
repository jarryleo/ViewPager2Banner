package cn.leo.viewpager2banner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import cn.leo.library.annotation.Align
import cn.leo.library.decoration.DrawableIndicatorDecoration
import cn.leo.library.support.config
import cn.leo.library.support.dp
import cn.leo.library.transformer.MultiplePagerScaleInTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val imgRes =
        arrayListOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four)

    private val textRes by lazy { resources.getStringArray(R.array.text_banner) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //配置图片banner
        banner.config {
            adapter = BannerAdapter().apply { data = imgRes }
            //transformer = DepthPageTransformer() //层叠渐变效果
            //transformer = ZoomOutPageTransformer()//下沉渐变效果
            transformer = MultiplePagerScaleInTransformer(100, 0.2f)//左右条目缩小漏出效果
            itemMargin = 35.dp//条目间距
            interval = 5000L    //自动翻页时间间隔
            //orientation = ViewPager2.ORIENTATION_VERTICAL
            //设置圆点指示器
            /*indicator = DotIndicatorDecoration(
                align = Align.BOTTOM or Align.LEFT,
                horizontalMargin = 46.dp
            )*/
            //设置图片指示器
            indicator = DrawableIndicatorDecoration(
                align = Align.BOTTOM or Align.LEFT,
                horizontalMargin = 46.dp,
                selectedDrawable = getDrawable(R.drawable.shape_select)!!,
                unselectedDrawable = getDrawable(R.drawable.shape_unselect)!!
            )
        }
        //文字上下滚动广告
        val textBannerAdapter = TextBannerAdapter().apply { data = textRes.toList() }
        textBanner.config {
            adapter = textBannerAdapter
            orientation = ViewPager2.ORIENTATION_VERTICAL
        }

        textBannerAdapter.setOnItemClickListener { adapter, v, position ->
            startActivity(Intent(this, Main2Activity::class.java))
        }
    }
}
