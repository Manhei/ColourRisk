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
	//��Ļ����
	private WakeLock wakeLock;
	//��Ϸ����ı�ǣ�Ĭ��Ϊ����(true)������OnMain()��֮ǰ�г�ʼ��
	private boolean orientation = true;
	//ˢ��Ƶ�ʣ�ÿ����Ϸ����ˢ�µĴ���
	public int FPS = 30;
	//�����Ƿ���ʾFPS
	public boolean isFPSShow = false;
	//�������֧��FPS
	public int minFPS = 15;
	//�󶨵���Ϸ����View
	private MainView mainView;
	
	public int ScreenWidth;//��Ļ���
	public int ScreenHeight;//��Ļ�߶�
	
	/**************	��Ϸ�ص����� *****************/
	//��Ϸ��ʼʱ(OnCreate)�ص����������롢��ʼ����Դ������
	public abstract void OnLoad();
	//��Ϸ������ɺ�(OnStrat)�ص���������Ϸ��ʼǰ�����ü�����
	public abstract void OnMain();
	//��Ϸ��ʼ��(OnResume)�ص�������ʼ��Ϸ�Ĳ���
	public abstract void OnGameResumed();
	//��Ϸ��ͣ��(OnPause)�ص���������Ϸ��ͣʱ��Ϸ���ݵı���
	public abstract void OnGamePause();
	//��Ϸ����ͣ�ָ�ʱ(OnRestart)�ص���������Ϸ���ݵ����¸�ֵ
	public abstract void OnGameRestart();
	//��Ϸ����ʱ(OnDestroy)�ص���������Ϸ��Դ�Ļ��գ����رճ���
	public abstract void Destroy();
	/**************	��Ϸ�ص����� *****************/
	
	//���ú����������OnMain()��֮ǰ�г�ʼ��
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);//���ر���
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		  WindowManager.LayoutParams.FLAG_FULLSCREEN);//����ȫ��
		
		//��ȡ��Ļʵ�ʿ��
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
		
		//��������
		if (orientation) {
			if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
				  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				 }		
		}
		
		
		//����Ϸview
		setContentView(mainView);
		
		//������Ļ����
		
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
