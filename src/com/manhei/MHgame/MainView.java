package com.manhei.MHgame;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback{

	private MHgameActivity context;
	private SurfaceHolder holder;
	private boolean MHgameRunning;
	
	//真实的FPS
	private int realFPS;
	
	//帧速小于15的连续帧数
	private int contentFPS;
	
	//当前显示的Screen
	private MHgameScreen curScreen;
	
	//是否显示性能提示
	private boolean isHint = true;
	
	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = (MHgameActivity) context;
		holder = getHolder();
		holder.addCallback(this);
	}
	
	public void setScreen(MHgameScreen screen) {
		if (curScreen != null) {
			curScreen.destroy();
		}
		curScreen = screen;
		curScreen.load();
		curScreen.loaded();
	}
	
	public MHgameScreen getCurScreen() {
		return curScreen;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		new drawThread().start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		MHgameRunning = true;
		realFPS = context.FPS;
		contentFPS = 0;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		MHgameRunning = false;
		curScreen.destroy();
	}
	
	public void draw() {
		Canvas canvas = holder.lockCanvas();
		if (canvas != null) {
			/*****  开始绘图  *****/
			//清屏
			canvas.drawColor(Color.BLACK);
			//绘画FPS
			if (context.isFPSShow) {
				
				if (curScreen != null) {
					curScreen.draw(canvas);
				}
				Paint paint = new Paint();
				paint.setColor(Color.YELLOW);
				paint.setTextSize(32);
				canvas.drawText("FPS:" + realFPS, context.ScreenWidth/800f * 50, context.ScreenHeight/480f * 50, paint);
			}
			/*****  结束绘图  *****/
		}
		if (canvas != null) {
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	class drawThread extends Thread {

		@Override
		public void run() {
			while (MHgameRunning) {
				long frameStartTime = System.currentTimeMillis();
				//主逻辑函数
				logic();
				//主绘图函数
				draw();
				if (System.currentTimeMillis() - frameStartTime < (1000/context.FPS)) {
					try {
						Thread.sleep(System.currentTimeMillis() - frameStartTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					realFPS = context.FPS;
					contentFPS = 0;
				}
				else {
					realFPS = (int) (1000 / (System.currentTimeMillis() - frameStartTime));
					if (realFPS < context.minFPS) {
						contentFPS++;
						Log.i("MainView", "contentFPS = " + contentFPS);
						if (contentFPS > 30) {
							Log.i("MainView", "contentFPS > 30 ");
							context.runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									MHgameRunning = false;
									AlertDialog.Builder builder = new Builder(context);
									builder.setTitle("提示")
									.setMessage("您的机器不支持此游戏，请更换设备!")
									.setPositiveButton("退出游戏", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											context.finish();
										}
									})
									.setNegativeButton("继续游戏", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											MHgameRunning = true;
											new drawThread().start();
											isHint = false;
										}
									});
									if (isHint) {
										builder.create().show();
									}
								}
							});
						}
					}
				}
			}
			super.run();
		}

		private void logic() {
			if (curScreen != null) {
				curScreen.logic();
			}
		}
		
	}
}
