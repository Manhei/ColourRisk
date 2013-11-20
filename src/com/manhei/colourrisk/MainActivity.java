package com.manhei.colourrisk;

import com.manhei.MHgame.MHgameActivity;
import com.manhei.colourrisk.screen.MenuScreen;

public class MainActivity extends MHgameActivity {
	public static MainActivity instance;
	@Override
	public void OnLoad() {

	}

	@Override
	public void OnMain() {
		instance = this;
		setFPS(30);
		setShowFPS(true);
		
		setScreen(new MenuScreen());
	}

	@Override
	public void OnGameResumed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnGamePause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnGameRestart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Destroy() {
		// TODO Auto-generated method stub
		
	}

}
