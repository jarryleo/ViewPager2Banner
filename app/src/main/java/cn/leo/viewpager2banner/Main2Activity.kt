package cn.leo.viewpager2banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main2.*

class Main2Activity : AppCompatActivity() {
    private val imgRes =
        arrayListOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        vp2.adapter = BannerAdapter().apply { data = imgRes }
        vp2.orientation = ViewPager2.ORIENTATION_VERTICAL

        srl.setEnableLoadMore(true)
        srl.setOnRefreshListener {
            srl.finishRefresh()
        }
        srl.setOnLoadMoreListener {
            srl.finishLoadMore()
        }
    }

}
