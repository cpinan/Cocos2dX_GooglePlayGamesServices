package com.carlospinan.utils;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.google.android.gms.appstate.AppStateManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * 
 * @author Carlos Eduardo Piñan Indacochea
 * @version 1.0.0
 * @date 2014/02/25
 * @update 2014/02/25
 * 
 */
public class UtilActivity extends BaseGameActivity {

	/**
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		_init();
	}

	/**
	 * 
	 */
	private void _init() {
		NativeUtils.configure(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (!ConfigUtils.IS_OUYA_APP)
			super.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);

		/*
		 * // Get the player # int player =
		 * OuyaController.getPlayerNumByDeviceId(event.getDeviceId()); boolean
		 * handled = false;
		 * 
		 * // Handle the input switch (keyCode) { case OuyaController.BUTTON_O:
		 * // You now have the key pressed and the player # that pressed it //
		 * doSomethingWithKey(); handled = true; break; }
		 * 
		 * return handled || super.onKeyDown(keyCode, event);
		 */

	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if (!ConfigUtils.IS_OUYA_APP)
			return super.onGenericMotionEvent(event);
		/*
		 * // Get the player # int player =
		 * OuyaController.getPlayerNumByDeviceId(event.getDeviceId());
		 * 
		 * // Get all the axis for the event float LS_X =
		 * event.getAxisValue(OuyaController.AXIS_LS_X); float LS_Y =
		 * event.getAxisValue(OuyaController.AXIS_LS_Y); float RS_X =
		 * event.getAxisValue(OuyaController.AXIS_RS_X); float RS_Y =
		 * event.getAxisValue(OuyaController.AXIS_RS_Y); float L2 =
		 * event.getAxisValue(OuyaController.AXIS_L2); float R2 =
		 * event.getAxisValue(OuyaController.AXIS_R2);
		 * 
		 * // Do something with the input // updatePlayerInput(player, LS_X,
		 * LS_Y, RS_X, RS_Y, L2, R2);
		 */

		return true;
	}

	@Override
	public void onSignInFailed() {
		// Override in child class
	}

	@Override
	public void onSignInSucceeded() {
		// Override in child class
	}

	/**
	 * Get boolean to identify if user is signed in
	 * 
	 * @return
	 */
	public boolean getSignedIn() {
		return isSignedIn();
	}

	/**
	 * 
	 * @return
	 */
	public GoogleApiClient getCustomApiClient() {
		return getApiClient();
	}

	/**
	 * 
	 */
	public void signInGooglePlay() {
		beginUserInitiatedSignIn();
	}

	/**
	 * 
	 */
	public void signOutGooglePlay() {
		signOut();
	}

	// InCloud methods not work yet.

	/**
	 * 
	 * @param key
	 * @param app_state
	 */
	public void inCloudSaveOrUpdate(final int key, final byte[] app_state) {
		AppStateManager.update(getCustomApiClient(), key, app_state);
	}

	/**
	 * 
	 * @param key
	 */
	public void inCloudLoad(final int key) {
		AppStateManager.load(getCustomApiClient(), key);
	}

	/**
	 * 
	 */
	public void requestGameAndCloudSave() {
		setRequestedClients(BaseGameActivity.CLIENT_GAMES
				| BaseGameActivity.CLIENT_APPSTATE);
	}

	/*
	 * Cocos2d-x Library
	 */
	static {
		System.loadLibrary("game");
	}

}
