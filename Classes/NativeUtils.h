#ifndef __NATIVE_UTILS_H__
#define __NATIVE_UTILS_H__

#include "cocos2d.h"
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include "JNIHelpers.h"
#endif

class NativeUtils
{
public:
	/*
	* Google play game services methods
	*/
	static bool isSignedIn();
	static void signIn();
	static void signOut();
	static void submitScore(const char* leaderboardID, long score);
	static void unlockAchievement(const char* achievementID);
	static void incrementAchievement(const char* achievementID, int numSteps);
	static void showAchievements();
	static void showLeaderboards();
	static void showLeaderboard(const char* leaderboardID);
	
	/*
	* Ouya connection
	*/
	

};

#endif