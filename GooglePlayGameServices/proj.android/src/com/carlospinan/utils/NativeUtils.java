package com.carlospinan.utils;

import tv.ouya.console.api.OuyaController;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.carlospinan.gpgscocos2dx.R;
import com.google.android.gms.games.Games;

/**
 * 
 * @author Carlos Eduardo Piñan Indacochea
 * @version 1.0.0
 * @date 2014/02/25
 * @update 2014/02/25
 * @reference http://www.cocos2d-x.org/forums/6/topics/28296?r=28417
 * @description Class to support Google Play Game Services and Ouya cocos2d-x
 *              integration.
 */
public class NativeUtils {

	// Important variables
	private static Context context = null;
	private static UtilActivity app = null;
	private static NativeUtils instance = null;

	// ID's
	private static final int REQUEST_ACHIEVEMENTS = 10000;
	private static final int REQUEST_LEADERBOARDS = 10001;
	private static final int REQUEST_LEADERBOARD = 10002;

	// TAG
	private static final String TAG = "ANDROID_TAG";

	/**
	 * Singleton
	 */
	public static NativeUtils sharedInstance() {
		if (instance == null)
			instance = new NativeUtils();
		return instance;
	}

	/**
	 * Configura los datos iniciales para comunicar los eventos de aquí a
	 * cocos2d-x.
	 */
	public static void configure(Context context) {
		NativeUtils.context = context;
		NativeUtils.app = (UtilActivity) NativeUtils.context;

		if (ConfigUtils.IS_OUYA_APP) {
			OuyaController.init(NativeUtils.context);
		}
	}

	/**
	 * 
	 * @param message
	 */
	public static void displayAlert(final String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setNeutralButton(
				context.getResources().getString(android.R.string.ok), null);
		builder.create().show();
	}

	/*
	 * Google play games services methods. Requirements:
	 * google-play-services_lib as library.
	 */

	/**
	 * Check if user is Signed In.
	 */
	public static boolean isSignedIn() {
		return app.getSignedIn();
	}

	/**
	 * Google Play Games Services Sign In
	 */
	public static void gameServicesSignIn() {
		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (!isSignedIn())
					app.signInGooglePlay();
			}
		});
	}

	/**
	 * Google Play Games Services Sign Out
	 */
	public static void gameServicesSignOut() {
		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn())
					app.signOutGooglePlay();
			}
		});
	}

	/**
	 * Submit a score in a leaderboard.
	 * 
	 * @param leaderboardID
	 * @param score
	 */
	public static void submitScore(final String leaderboardID, final long score) {
		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn()) {
					Log.d(TAG, "Submit score " + score + " to " + leaderboardID);

					Games.Leaderboards.submitScore(app.getCustomApiClient(),
							leaderboardID, score);

				} else {
					String message = context.getResources().getString(
							R.string.fail_submit_score_leaderboard);
					message = message.replace("{score}", score + "");
					message = message.replace("{leaderboardID}", leaderboardID);
					displayAlert(message);
				}
			}
		});

	}

	/**
	 * Unlock an achievement.
	 * 
	 * @param achievementId
	 */
	public static void unlockAchievement(final String achievementID) {
		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn()) {
					Log.d(TAG, "Try to unlock achievement " + achievementID);
					Games.Achievements.unlock(app.getCustomApiClient(),
							achievementID);
				} else {
					String message = context.getResources().getString(
							R.string.fail_unlock_achievement);
					message = message.replace("{achievementID}", achievementID);
					displayAlert(message);
				}
			}
		});
	}

	/**
	 * Increment the achievement in numSteps.
	 * 
	 * @param achievementId
	 * @param numSteps
	 */
	public static void incrementAchievement(final String achievementID,
			final int numSteps) {
		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn())
					Games.Achievements.increment(app.getCustomApiClient(),
							achievementID, numSteps);
				else {
					String message = context.getResources().getString(
							R.string.fail_increment_achievement);
					message = message.replace("{achievementID}", achievementID);
					message = message.replace("{numSteps}", numSteps + "");
					displayAlert(message);
				}
			}
		});
	}

	/**
	 * Show all achievements.
	 */
	public static void showAchievements() {
		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn())
					app.startActivityForResult(Games.Achievements
							.getAchievementsIntent(app.getCustomApiClient()),
							REQUEST_ACHIEVEMENTS);
				else {
					String message = context.getResources().getString(
							R.string.fail_show_achievements);
					displayAlert(message);
				}

			}
		});

	}

	/**
	 * Show all leaderboard.
	 */
	public static void showLeaderboards() {

		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn())
					app.startActivityForResult(
							Games.Leaderboards.getAllLeaderboardsIntent(app
									.getCustomApiClient()),
							REQUEST_LEADERBOARDS);
				else {
					String message = context.getResources().getString(
							R.string.fail_show_leaderboards);
					displayAlert(message);
				}
			}
		});

	}

	/**
	 * Show single leaderboard.
	 */
	public static void showLeaderboard(final String leaderboardID) {

		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn())
					app.startActivityForResult(
							Games.Leaderboards.getLeaderboardIntent(
									app.getCustomApiClient(), leaderboardID),
							REQUEST_LEADERBOARD);
				else {
					String message = context.getResources().getString(
							R.string.fail_show_leaderboard);
					message = message.replace("{leaderboardID}", leaderboardID);
					displayAlert(message);
				}
			}
		});

	}

	/**
	 * 
	 * @param key
	 * @param app_state
	 */
	public static void inCloudSaveOrUpdate(final int key, final byte[] app_state) {

		if (!ConfigUtils.GOOGLE_PLAY_IN_CLOUD_SAVE)
			return;

		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn())
					app.inCloudSaveOrUpdate(key, app_state);
				else {
					String message = context.getResources().getString(
							R.string.gamehelper_alert);
					displayAlert(message);
				}
			}
		});
	}

	/**
	 * 
	 * @param key
	 */
	public static void inCloudLoad(final int key) {

		if (!ConfigUtils.GOOGLE_PLAY_IN_CLOUD_SAVE)
			return;

		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (isSignedIn())
					app.inCloudLoad(key);
				else {
					String message = context.getResources().getString(
							R.string.gamehelper_alert);
					displayAlert(message);
				}
			}
		});
	}

	// AdMob

	/*
	 * Show and Hide AdView
	 */

	public static void showAd() {
		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				app.showAd();
			}
		});
	}

	public static void hideAd() {
		app.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				app.hideAd();
			}
		});
	}

	/*
	 * Ouya support Note: For Ouya support need uncomment the <category
	 * android:name="tv.ouya.intent.category.GAME" /> in the AndroidManifest.xml
	 */

	/*
	 * Native call
	 */

	public static native void notifyInCloudSavingOrUpdate();

	public static native void notifyInCloudLoad();

}
