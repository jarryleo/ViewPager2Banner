# ViewPager2Banner
使用ViewPager2实现的banner效果

可以实现不同的特殊效果，具体请看示例；可以竖向滚动！

![screenShot](image/screenShot.png)

基础用法：   
```
	banner.config {adapter = BannerAdapter().apply { data = imgRes }}
```

添加翻页效果和指示器：
``` 
        banner.config {
            adapter = BannerAdapter().apply { data = imgRes }
            transformer = DepthPageTransformer() //层叠渐变效果
            //transformer = ZoomOutPageTransformer()//下沉渐变效果
            //transformer = MultiplePagerScaleInTransformer(100,0.2f)//左右条目缩小漏出效果
            itemMargin = 25.dp()//条目间距
            //设置圆点指示器
            indicator = IndicatorDecoration(
                align = Align.BOTTOM or Align.LEFT,
                horizontalMargin = 46.dp()
            )
        }
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
		implementation 'androidx.viewpager2:viewpager2:1.0.0'
	        implementation 'com.github.jarryleo:ViewPager2Banner:v1.2'
	}
