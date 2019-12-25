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
