# CleverBanner
轮播图，无限循环，无限滑动，有指示点（实心），可以自由设置<br><br>
## 使用介绍
* **step1：在根目录的build.gradle文件添加**<br>
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```<br>
* **step2：为app的build.gradle恩建添加依赖**<br>
```
dependencies {
	        compile 'com.github.Biligle:CleverBanner:v1.0'
	}
```<br>
* **step3：在你的布局中引用**<br>
```
    <com.wgl.cleverbannerlibrary.CleverBanner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```<br>
* **以上是最简单的引用，当然可以根据要求改变属性**<br>
palaceIcon:占位图<br>
errorIcon：加载出错的图<br>
margin： 指示点间距<br>
selected_color：指示点选中颜色<br>
unSelected_color：指示点未选中颜色<br>
bigger_height：指示点选中变大<br>
indicator_background_color ：指示点背景颜色<br>
indicator_alpha： 指示点背景透明度<br>
time：轮播时间（毫秒）<br>
isDisplay：true: 自动播放<br>
isIndicator：true:有指示点<br>
