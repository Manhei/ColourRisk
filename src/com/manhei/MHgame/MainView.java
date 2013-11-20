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
	
	//��ʵ��FPS
	private int realFPS;
	
	//֡��С��15������֡��
	private int contentFPS;
	
	//��ǰ��ʾ��Screen
	private MHgameScreen curScreen;
	
	//�Ƿ���ʾ������ʾ
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
			/*****  ��ʼ��ͼ  *****/
			//����
			canvas.drawColor(Color.BLACK);
			//�滭FPS
			if (context.isFPSShow) {
				
				if (curScreen != null) {
					curScreen.draw(canvas);
				}
				Paint paint = new Paint();
				paint.setColor(Color.YELLOW);
				paint.setTextSize(32);
				canvas.drawText("FPS:" + realFPS, context.ScreenWidth/800f * 50, context.ScreenHeight/480f * 50, paint);
			}
			/*****  ������ͼ  *****/
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
				//���߼�����
				logic();
				//����ͼ����
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
									builder.setTitle("��ʾ")
									.setMessage("���Ļ�����֧�ִ���Ϸ��������豸!")
									.setPositiveButton("�˳���Ϸ", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											context.finish();
										}
									})
									.setNegativeButton("������Ϸ", new DialogInterface.OnClickListener() {
										
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
