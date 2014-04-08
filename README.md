Cocos2dx Google Play Game Services
Mit License - http://opensource.org/

V.1.0.0

Android Google Play Game Services with Cocos2d-x Support for Achievements and Leaderboards.
v.1.0.1

Bugs fixed by vivekporwal - https://github.com/vivekporwal

Submit score to play services was not working properly before because of call to bad function signature in JNIHelper.cpp which is corrected now.
'long score' was taking garbage value as not converted to jlong is also fixed.
incrementAchievement : function call done for Boolean instead of Integer is corrected now.
Version for Cocos2d-x v3.0 by howlryu - https://github.com/howlryu

https://github.com/howlryu/Cocos2dX_GooglePlayGamesServices
V.1.0.2

IOS Leaderboards, Achievements and Little demo implemented.
Merge android project and IOS project to manage some platforms with the same code.
On Android Side modify the "gamehelper_ids" xml to update the app id and on the IOS side update the "PlayGameConstants.h" to make changes.
Leaderboards ID's and Achievements ID's are in the "PlayGameConstants.h" file
Created "PlayGameSingleton.h" for IOS side.
Check IOS folder to watch the files.
Don't forget add google-play-services_lib library. The version used for this template is: 4242000

Android Support with cocos2d-x for Google Play Games Services.

This is the first version for the Cocos2d-x Template to use Google Play Games Services with Android.

Package created: com.carlospinan.utils

ConfigUtils.java : Contains some global constants to identify if the services is available.
NativeUtils.java : Allow communication with C++
UtilActivity.java : This is the Class to extends from BaseGameActivity and implements some methods.
This sources in the future will support Google Play Games Services for IOS and Ouya support (Just android)

I will add Facebook SDK, Twitter SDK and some support for IAB (In App Billing) but maybe I will work with the Paypal SDK.

Important C++ Clases:

JNIHelpers: Allows communication from C++ to Java. NativeUtils: Have some methods to communicate with Google Play Games Services.

Currently just work Achievements and Leaderboards.

Android notes:

Modify the file build_native.sh and change the NDK_ROOT for your NDK PATH and find COCOS2DX_ROOT and change for your COCOS2DX PATH

After this changes put in your terminal (or Cygwin):

./build_native.sh in the proj.android directory.

Note: I recommend use first: "./build_native.sh clean" without quotes to clean the previous obj

If you want to implement this use: NativeUtils.h in your C++ class and with your Game Logic call the methods.

In this version the current methods are:

static bool isSignedIn();
static void signIn();
static void signOut();
static void submitScore(const char* leaderboardID, long score);
static void unlockAchievement(const char* achievementID);
static void incrementAchievement(const char* achievementID, int numSteps);
static void showAchievements();
static void showLeaderboards();
static void showLeaderboard(const char* leaderboardID);
In the next version I will add the multiplayer support and I'm working on the Ouya integration and IOS support for Google Play Game Services.

IOS Side explanation V 1.0.2:

If you have some problem just contact me to:

carlos.pinan@gmail.com
