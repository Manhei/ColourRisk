package com.manhei.MHgame;

import com.manhei.colourrisk.MainActivity;
import com.manhei.colourrisk.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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
	
	//绘制图像资源
	public Bitmap loadBitmap(Context context, int id) {
		return BitmapFactory.decodeResource(context.getResources(), id);
	}
}
