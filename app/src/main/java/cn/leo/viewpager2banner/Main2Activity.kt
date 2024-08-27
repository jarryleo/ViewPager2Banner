package cn.leo.viewpager2banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import cn.leo.viewpager2banner.databinding.ActivityMain2Binding
import com.google.android.material.snackbar.Snackbar

class Main2Activity : AppCompatActivity() {
    private val imgRes =
        arrayListOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four)

    private val viewBinding by lazy { ActivityMain2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)

        viewBinding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        viewBinding.content.vp2.adapter = BannerAdapter().apply { data = imgRes }
        viewBinding.content.vp2.orientation = ViewPager2.ORIENTATION_VERTICAL

        viewBinding.content.srl.setEnableLoadMore(true)
        viewBinding.content.srl.setOnRefreshListener {
            viewBinding.content.srl.finishRefresh()
        }
        viewBinding.content.srl.setOnLoadMoreListener {
            viewBinding.content.srl.finishLoadMore()
        }
    }

}
