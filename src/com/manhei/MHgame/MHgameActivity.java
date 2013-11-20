package com.manhei.MHgame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public abstract class MHgameActivity extends Activity{
	private final String TAG = "MGgameActivity";
	//屏幕常亮
	private WakeLock wakeLock;
	//游戏方向的标记，默认为横屏(true)，需在OnMain()及之前中初始化
	private boolean orientation = true;
	//刷新频率，每秒游戏界面刷新的次数
	public int FPS = 30;
	//设置是否显示FPS
	public boolean isFPSShow = false;
	//设置最低支持FPS
	public int minFPS = 15;
	//绑定的游戏界面View
	private MainView mainView;
	
	public int ScreenWidth;//屏幕宽度
	public int ScreenHeight;//屏幕高度
	
	/**************	游戏回调过程 *****************/
	//游戏开始时(OnCreate)回调，负责载入、初始化资源及变量
	public abstract void OnLoad();
	//游戏载入完成后(OnStrat)回调，负责游戏开始前的设置及操作
	public abstract void OnMain();
	//游戏开始后(OnResume)回调，负责开始游戏的操作
	public abstract void OnGameResumed();
	//游戏暂停后(OnPause)回调，负责游戏暂停时游戏数据的保存
	public abstract void OnGamePause();
	//游戏由暂停恢复时(OnRestart)回调，负责游戏数据的重新赋值
	public abstract void OnGameRestart();
	//游戏结束时(OnDestroy)回调，负责游戏资源的回收，并关闭程序
	public abstract void Destroy();
	/**************	游戏回调过程 *****************/
	
	//设置横屏与否，需在OnMain()及之前中初始化
	public boolean setOrientation(boolean b) {
		this.orientation = b;
		return orientation;
	}
	
	public void setFPS(int i) {
		this.FPS = i;
	}
	
	public void setShowFPS(boolean b) {
		this.isFPSShow = b;
	}
	
	public void setMinFPS(int i) {
		this.minFPS = i;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(TAG, "MHgame is start!");
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		  WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
		
		//获取屏幕实际宽高
				WindowManager windowManager = getWindowManager();
				Display display = windowManager.getDefaultDisplay();
				ScreenWidth = display.getWidth();
				ScreenHeight = display.getHeight();
				Log.i(TAG, "Screen Width is " + ScreenWidth + " \nScreen Height is " + ScreenHeight);

		this.wakeLock = ((PowerManager)getSystemService(POWER_SERVICE)).
				newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "MHgameActivity");

		mainView = new MainView(this);
		System.gc();
		this.OnLoad();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "MHgame is destroy!");
		this.Destroy();
	}

	@Override
	protected void onPause() {
		this.wakeLock.release();
		super.onPause();
		this.OnGamePause();
	}

	@Override
	protected void onResume() {
		
		//横屏设置
		if (orientation) {
			if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
				  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				 }		
		}
		
		
		//绑定游戏view
		setContentView(mainView);
		
		//设置屏幕常亮
		
		this.wakeLock.acquire();
		super.onResume();
		this.OnGameResumed();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.OnMain();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		this.OnGameRestart();
	}
	
	public void setScreen(MHgameScreen screen) {
		mainView.setScreen(screen);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mainView.getCurScreen().TouchDown(event);
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			mainView.getCurScreen().TouchUp(event);
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			mainView.getCurScreen().TouchMove(event);
		}
		return true;
	}
	
	
}
