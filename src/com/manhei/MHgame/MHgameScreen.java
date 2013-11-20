package com.manhei.MHgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class MHgameScreen {

	//�߼�����
	public abstract void logic();
	
	//��ʼ������
	public abstract void load();
	
	//��ʼ����ɺ���
	public abstract void loaded();
	
	//���ٺ���
	public abstract void destroy();
	
	//��ͼ����
	public abstract void draw(Canvas canvas);
	
	//����������
	public abstract void TouchDown(MotionEvent event);
	
	//������̧��
	public abstract void TouchUp(MotionEvent event);
	
	//�������ƶ�
	public abstract void TouchMove(MotionEvent event);
	
	//����ͼ����Դ
	public Bitmap loadBitmap(Context context, int id) {
		return BitmapFactory.decodeResource(context.getResources(), id);
	}
}
