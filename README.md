# CleverBanner
轮播图，无限循环，无限滑动，有指示点（实心），可以自由设置

* step1：在根目录的build.gradle文件添加<br>
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}<br>
* step2：为app的build.gradle恩建添加依赖<br>
dependencies {
	        compile 'com.github.Biligle:CleverBanner:v1.0'
	}
* step3：在你的布局中引用<br>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    tools:context="com.wgl.cleverbanner.MainActivity">

    <com.wgl.cleverbannerlibrary.CleverBanner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>

</RelativeLayout><br>
* 以上是最简单的引用，当然可以根据要求改变属性<br>
palaceIcon:占位图
errorIcon：加载出错的图
margin： 指示点间距
selected_color：指示点选中颜色
unSelected_color：指示点未选中颜色
bigger_height：指示点选中变大
indicator_background_color ：指示点背景颜色
indicator_alpha： 指示点背景透明度
time：轮播时间（毫秒）
isDisplay：true: 自动播放
isIndicator：true:有指示点
