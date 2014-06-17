/*
 * Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.example.games.basegameutils;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.carlospinan.utils.ConfigUtils;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Example base class for games. This implementation takes care of setting up
 * the GamesClient object and managing its lifecycle. Subclasses only need to
 * override the @link{#onSignInSucceeded} and @link{#onSignInFailed} abstract
 * methods. To initiate the sign-in flow when the user clicks the sign-in
 * button, subclasses should call @link{#beginUserInitiatedSignIn}. By default,
 * this class only instantiates the GamesClient object. If the PlusClient or
 * AppStateClient objects are also wanted, call the BaseGameActivity(int)
 * constructor and specify the requested clients. For example, to request
 * PlusClient and GamesClient, use BaseGameActivity(CLIENT_GAMES | CLIENT_PLUS).
 * To request all available clients, use BaseGameActivity(CLIENT_ALL).
 * Alternatively, you can also specify the requested clients via
 * 
 * @link{#setRequestedClients , but you must do so before @link{#onCreate} gets
 *                            called, otherwise the call will have no effect.
 * 
 * @author Bruno Oliveira (Google)
 */
public abstract class BaseGameActivity extends Cocos2dxActivity implements
		GameHelper.GameHelperListener {

	// The game helper object. This class is mainly a wrapper around this
	// object.
	protected GameHelper mHelper;

	// We expose these constants here because we don't want users of this class
	// to have to know about GameHelper at all.
	public static final int CLIENT_GAMES = GameHelper.CLIENT_GAMES;
	public static final int CLIENT_APPSTATE = GameHelper.CLIENT_APPSTATE;
	public static final int CLIENT_PLUS = GameHelper.CLIENT_PLUS;
	public static final int CLIENT_ALL = GameHelper.CLIENT_ALL;

	// Requested clients. By default, that's just the games client.
	protected int mRequestedClients = CLIENT_GAMES;

	private final static String TAG = "BaseGameActivity";
	protected boolean mDebugLog = false;

	/** Constructs a BaseGameActivity with default client (GamesClient). */
	protected BaseGameActivity() {
		super();
	}

	/**
	 * Constructs a BaseGameActivity with the requested clients.
	 * 
	 * @param requestedClients
	 *            The requested clients (a combination of CLIENT_GAMES,
	 *            CLIENT_PLUS and CLIENT_APPSTATE).
	 */
	protected BaseGameActivity(int requestedClients) {
		super();
		if (ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			setRequestedClients(requestedClients);
	}

	/**
	 * Sets the requested clients. The preferred way to set the requested
	 * clients is via the constructor, but this method is available if for some
	 * reason your code cannot do this in the constructor. This must be called
	 * before onCreate or getGameHelper() in order to have any effect. If called
	 * after onCreate()/getGameHelper(), this method is a no-op.
	 * 
	 * @param requestedClients
	 *            A combination of the flags CLIENT_GAMES, CLIENT_PLUS and
	 *            CLIENT_APPSTATE, or CLIENT_ALL to request all available
	 *            clients.
	 */
	protected void setRequestedClients(int requestedClients) {
		mRequestedClients = requestedClients;
	}

	public GameHelper getGameHelper() {
		if (mHelper == null) {
			mHelper = new GameHelper(this, mRequestedClients);
			mHelper.enableDebugLog(mDebugLog);
		}
		return mHelper;
	}

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		if (ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES) {
			if (mHelper == null) {
				getGameHelper();
			}
			mHelper.setup(this);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			mHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		//if (ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
		//	mHelper.onStop();
	}

	@Override
	protected void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		if (ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			mHelper.onActivityResult(request, response, data);
	}

	protected GoogleApiClient getApiClient() {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return null;
		return mHelper.getApiClient();
	}

	protected boolean isSignedIn() {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return false;
		return mHelper.isSignedIn();
	}

	protected void beginUserInitiatedSignIn() {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return;
		mHelper.beginUserInitiatedSignIn();
	}

	protected void signOut() {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return;
		mHelper.signOut();
	}

	protected void showAlert(String message) {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return;
		mHelper.makeSimpleDialog(message).show();
	}

	protected void showAlert(String title, String message) {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return;
		mHelper.makeSimpleDialog(title, message).show();
	}

	protected void enableDebugLog(boolean enabled) {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return;
		mDebugLog = true;
		if (mHelper != null) {
			mHelper.enableDebugLog(enabled);
		}
	}

	@Deprecated
	protected void enableDebugLog(boolean enabled, String tag) {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return;
		Log.w(TAG, "BaseGameActivity.enabledDebugLog(bool,String) is "
				+ "deprecated. Use enableDebugLog(boolean)");
		enableDebugLog(enabled);
	}

	protected String getInvitationId() {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return null;
		return mHelper.getInvitationId();
	}

	protected void reconnectClient() {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return;
		mHelper.reconnectClient();
	}

	protected boolean hasSignInError() {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return false;
		return mHelper.hasSignInError();
	}

	protected GameHelper.SignInFailureReason getSignInError() {
		if (!ConfigUtils.USE_GOOGLE_PLAY_GAME_SERVICES)
			return null;
		return mHelper.getSignInError();
	}
}
