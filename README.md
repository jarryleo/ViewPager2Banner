# ViewPager2Banner
使用ViewPager2实现的banner效果

用法：   
``` 
        //普通的RecyclerView的adapter
        val adapter = BannerAdapter()
        adapter.data = imgRes
        banner.setAdapter(adapter)
        //设置条目与容器的左右边距
        banner.addItemDecoration(MarginItemDecoration(50))
        //设置条目的缩放和左右缩进间隔
        banner.setPageTransformer(MultiplePagerScaleInTransformer(100,0.2f))
        //设置圆点指示器
        banner.addItemDecoration(IndicatorDecoration())
```

引用：
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.jarryleo:ViewPager2Banner:v1.0'
	}
