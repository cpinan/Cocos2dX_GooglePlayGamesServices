package com.carlospinan.utils;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.carlospinan.GooglePlayGameServices.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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

	private AdView adView = null;
	private InterstitialAd interstitial = null;
	private FrameLayout adViewLayout = null;
	public static final String TAG = "UtilActivity";

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
		if (ConfigUtils.USE_AD_MOB) {
			_initAdMob();
		}
	}

	private void _initAdMob() {

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;

		// Create an ad.
		if (ConfigUtils.AD_MOB_ENABLE_BANNER) {
			adView = new AdView(this);
			adView.setAdSize(AdSize.SMART_BANNER);
			adView.setAdUnitId(getResources().getString(R.string.AD_UNIT_ID));
			adView.setLayoutParams(params);

			// Add the AdView to the view hierarchy. The view will have no size
			// until the ad is loaded.

			adViewLayout = new FrameLayout(this);
			adViewLayout.setLayoutParams(params);
			adViewLayout.addView(adView);

			if (ConfigUtils.AD_MOB_DEBUG) {
				// Create an ad request. Check logcat output for the hashed
				// device ID to
				// get test ads on a physical device.
				AdRequest adRequest = new AdRequest.Builder()
					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
					.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE").build();

				// Start loading the ad in the background.
				adView.loadAd(adRequest);
			}
			this.addContentView(adViewLayout, params);
		} 
		
		if (ConfigUtils.AD_MOB_ENABLE_FULLSCREEN) {
			interstitial = new InterstitialAd(this);
		    interstitial.setAdUnitId(getResources().getString(R.string.AD_UNIT_INTERSTITIAL_ID));
		}



		Log.d(TAG, "Init AdMob Android");
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

	public void showAd() {
		if (ConfigUtils.USE_AD_MOB && adView != null)
			adViewLayout.setVisibility(View.VISIBLE);
	}

	public void hideAd() {
		if (ConfigUtils.USE_AD_MOB && adView != null)
			adViewLayout.setVisibility(View.GONE);
	}
	
	public void preloadInterstitialAd() {
		if (ConfigUtils.USE_AD_MOB && interstitial != null) {
		    // Should be done on main thread
		    this.runOnUiThread(new Runnable() {
	    		  public void run() {
	    			  // Create ad request.
	    			  AdRequest adRequest = new AdRequest.Builder().build();
	    			    
	    			  interstitial.loadAd(adRequest);
	    		  }
	    	  });
		}
	}
	
	public void showInterstitialAd() {
		if (ConfigUtils.USE_AD_MOB && interstitial != null) {
			
			// Should be done on main thread
		    this.runOnUiThread(new Runnable() {
	    		  public void run() {
		   			  if (interstitial.isLoaded()) {		    				  
		   				  interstitial.show();
	    			  }
	    		  }
		    });
		    
		}
	}

	@Override
	protected void onPause() {
		if (ConfigUtils.USE_AD_MOB && adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ConfigUtils.USE_AD_MOB && adView != null) {
			adView.resume();
		}
	}

	@Override
	protected void onDestroy() {
		if (ConfigUtils.USE_AD_MOB && adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	/*
	 * Cocos2d-x Library
	 */
	static {
		System.loadLibrary("cocos2dcpp");
	}

}
