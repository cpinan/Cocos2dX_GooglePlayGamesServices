package com.carlospinan.utils;

/**
 * 
 * @author Carlos Eduardo Piñan Indacochea
 * @version 1.0.0
 * @date 2014/02/25
 * @update 2014/02/25
 * 
 */
public class ConfigUtils {

	public static final boolean IS_OUYA_APP = false;
	// Need include google-play-services_lib
	public static final boolean USE_GOOGLE_PLAY_GAME_SERVICES = true;

	// Nota: USE_GOOGLE_PLAY_GAME_SERVICES debe estar habilitado para que
	// funcione GOOGLE_PLAY_IN_CLOUD_SAVE y habilitar en el AndroidManifest.
	public static final boolean GOOGLE_PLAY_IN_CLOUD_SAVE = false;

	// Use ADMOB
	public static final boolean USE_AD_MOB = true;
	public static final boolean AD_MOB_DEBUG = false;
	public static final boolean AD_MOB_ENABLE_BANNER = false;
	public static final boolean AD_MOB_ENABLE_FULLSCREEN = true;

}
