package com.manhei.colourrisk.screen;

import org.loon.framework.android.game.core.graphics.Screen;
import org.loon.framework.android.game.core.graphics.Touch;
import org.loon.framework.android.game.core.graphics.device.LGraphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.manhei.MHgame.MHgameActivity;
import com.manhei.MHgame.MHgameImage;
import com.manhei.MHgame.MHgameScreen;
import com.manhei.colourrisk.MainActivity;
import com.manhei.colourrisk.R;

public class MenuScreen extends MHgameScreen {

	//图片对象
	MHgameImage bitmap;
	

	//图片显示与否的标志
	boolean isShow = false;
	
	//是否选中图片的标志
	boolean isTouchInBitmap = false;
	
	//是否运动的标志
	boolean isRunning = false;
	
	//图片的方向向量
	VN vn;
	
	
	@Override
	public void load() {
		bitmap = new MHgameImage(MainActivity.instance, R.drawable.images, 0 , 0);
	}

	@Override
	public void loaded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas) {
//		Paint paint = new Paint();
//		paint.setTextSize(44);
//		paint.setColor(Color.WHITE);
//		canvas.drawText("I'm ComeBack!!!", x, y, paint);
//		canvas.drawText("I'm ComeBack!!!", x1, y1, paint);
//		canvas.drawText("I'm ComeBack!!!", x2, y2, paint);
//		canvas.drawText("I'm ComeBack!!!", x3, y3, paint);
		if (isShow) {
			bitmap.drawImage(canvas);
		}
//		canvas.drawBitmap(bitmap,
//				x1, y1, paint);
//		canvas.drawBitmap(bitmap,
//				x2, y2, paint);
//		canvas.drawBitmap(bitmap,
//				x3, y3, paint);
	}

	@Override
	public void logic() {
		if (vn != null && isRunning) {
			bitmap.setLeft(bitmap.getLeft() + vn.x);
			bitmap.setTop(bitmap.getTop() + vn.y);
			//接触上边界或者下边界
			if (bitmap.getTop() <= 0 || (bitmap.getTop() + bitmap.getHeight()) >= MainActivity.instance.ScreenHeight) {
				vn.y = -vn.y;
			}
			//接触左边界或者右边界
			else if (bitmap.getLeft() <= 0 || (bitmap.getLeft() + bitmap.getWidth()) >= MainActivity.instance.ScreenWidth) {
				vn.x = -vn.x;
			}
		}
	}

	@Override
	public void TouchDown(MotionEvent event) {
		if (!isShow) {
				bitmap.setLeft( (int) event.getX());
				bitmap.setTop( (int) event.getY());
				vn = new VN((int)(Math.random()*10-5), (int)(Math.random()*10-5));
				isShow = true;
				isRunning = true;
			}
		else {
			int temx = (int) event.getX();
			int temy = (int) event.getY();
			if (temx > bitmap.getLeft() && temx < (bitmap.getLeft() + bitmap.getWidth())
					&& temy > bitmap.getTop() && temy < (bitmap.getTop() + bitmap.getHeight())) {
				isRunning = false;
				isTouchInBitmap = true;
			}
		}
	}

	@Override
	public void TouchUp(MotionEvent event) {
		if (isShow && !isRunning) {
			isRunning = true;
			isTouchInBitmap = false;
			vn.set((int)(Math.random()*10-5), (int)(Math.random()*10-5));
		}
	}

	@Override
	public void TouchMove(MotionEvent event) {
		if (isTouchInBitmap) {
			bitmap.setLeft( (int) event.getX() - bitmap.getWidth() / 2);
			bitmap.setTop( (int) event.getY() - bitmap.getHeight() / 2);
			if (bitmap.getLeft() < 0) {
				bitmap.setLeft(0);
			}
			if(bitmap.getLeft() + bitmap.getWidth() > MainActivity.instance.ScreenWidth){
				bitmap.setLeft( MainActivity.instance.ScreenWidth - bitmap.getWidth());
			}
			if (bitmap.getTop() < 0) {
				bitmap.setTop(0);
			}
			if(bitmap.getTop() + bitmap.getHeight() > MainActivity.instance.ScreenHeight) {
				bitmap.setTop( MainActivity.instance.ScreenHeight - bitmap.getHeight());
			}
		}
	}


	class VN {
		public int x;
		public int y;
		
		VN(int x, int y) {
			set(x, y);
		}
		
		public void set(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
	}
}
