package com.manhei.MHgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class MHgameScreen {

	//逻辑函数
	public abstract void logic();
	
	//初始化函数
	public abstract void load();
	
	//初始化完成函数
	public abstract void loaded();
	
	//销毁函数
	public abstract void destroy();
	
	//绘图函数
	public abstract void draw(Canvas canvas);
	
	//触摸屏按下
	public abstract void TouchDown(MotionEvent event);
	
	//触摸屏抬起
	public abstract void TouchUp(MotionEvent event);
	
	//触摸屏移动
	public abstract void TouchMove(MotionEvent event);
	
	//绘制图像资源
	public Bitmap loadBitmap(Context context, int id) {
		return BitmapFactory.decodeResource(context.getResources(), id);
	}
}
