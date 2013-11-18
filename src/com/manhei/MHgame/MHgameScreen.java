package com.manhei.MHgame;

import com.manhei.colourrisk.MainActivity;
import com.manhei.colourrisk.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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
	
	//����ͼ����Դ
	public Bitmap loadBitmap(Context context, int id) {
		return BitmapFactory.decodeResource(context.getResources(), id);
	}
}
