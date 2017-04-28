# CleverBanner
轮播图，无限循环，无限滑动，有指示点（实心），可以自由设置
#使用方法
##step1：在根目录的build.gradle文件添加
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
##step2：为app的build.gradle恩建添加依赖
dependencies {
	        compile 'com.github.Biligle:CleverBanner:v1.0'
	}
##step3：在你的布局中引用）
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

</RelativeLayout>
##以上是最简单的引用，当然可以根据要求改变属性
        placeIcon：<!--占位图-->
        errorIcon：<!--加载出错图-->
        selected_color：<!--选中颜色-->
        bigger_height：<!--选中的指示点变大-->
        unSelected_color：<!--未选中颜色-->
        time：<!--轮播时间间隔-->毫秒值
        isDisplay：<!--是否自动播放-->true:自动播放
        isIndicator：<!--是否有指示点-->true:有指示点
        margin：<!--指示点间距-->
        indicator_alpha：<!--指示点布局透明度-->
        indicator_background_color<!--指示点布局颜色-->
