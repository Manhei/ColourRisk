package com.manhei.colourrisk.screen;

import org.loon.framework.android.game.core.graphics.Screen;
import org.loon.framework.android.game.core.graphics.Touch;
import org.loon.framework.android.game.core.graphics.device.LGraphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.manhei.MHgame.MHgameScreen;
import com.manhei.colourrisk.MainActivity;
import com.manhei.colourrisk.R;

public class MenuScreen extends MHgameScreen {
	
	int x = 200;
	int y = 200;
	int x1 = 400;
	int y1 = 250;
	int x2 = 100;
	int y2 = 160;
	int x3 = 650;
	int y3 = 400;
	
	Bitmap bitmap;

	@Override
	public void load() {
		bitmap = this.loadBitmap(MainActivity.instance, R.drawable.main);
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
		Paint paint = new Paint();
		paint.setTextSize(44);
		paint.setColor(Color.WHITE);
//		canvas.drawText("I'm ComeBack!!!", x, y, paint);
//		canvas.drawText("I'm ComeBack!!!", x1, y1, paint);
//		canvas.drawText("I'm ComeBack!!!", x2, y2, paint);
//		canvas.drawText("I'm ComeBack!!!", x3, y3, paint);
		
		canvas.drawBitmap(bitmap,
				x, y, paint);
		canvas.drawBitmap(bitmap,
				x1, y1, paint);
		canvas.drawBitmap(bitmap,
				x2, y2, paint);
		canvas.drawBitmap(bitmap,
				x3, y3, paint);
	}

	@Override
	public void logic() {
		if (x > MainActivity.instance.ScreenWidth) {
			x = 0;
		}
		else {
			x+=3;
		}
		if (x1 > MainActivity.instance.ScreenWidth) {
			x1 = 0;
		}
		else {
			x1+=5;
		}
		if (x2 > MainActivity.instance.ScreenWidth) {
			x2 = 0;
		}
		else {
			x2+=10;
		}
		if (x3 > MainActivity.instance.ScreenWidth) {
			x3 = 0;
		}
		else {
			x3+=1;
		}
	}

}
