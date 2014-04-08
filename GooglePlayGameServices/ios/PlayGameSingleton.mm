//
//  ControllerSingleLeaderboard.m
//  GooglePlayGameServices
//
//  Created by NSS on 4/7/14.
//
//

#import "PlayGameSingleton.h"
#import <GooglePlayGames/GooglePlayGames.h>
#import <GooglePlus/GooglePlus.h>

#include "ViewSingleLeaderboard.h"
#include "ViewLeaderboardPicker.h"
#include "ViewAchievements.h"

#include "cocos2d.h"

using namespace cocos2d;

#pragma mark - Declare Views
ViewSingleLeaderboard* viewSingleLeaderboard = 0;
ViewLeaderboardPicker* viewLeaderboardPicker = 0;
ViewAchievements* viewAchiemevents = 0;

#pragma mark - Destructor and Constructor
PlayGameSingleton::~PlayGameSingleton()
{
    rootController = 0;
}

PlayGameSingleton::PlayGameSingleton()
{
    
}

#pragma mark - Singleton
PlayGameSingleton& PlayGameSingleton::sharedInstance()
{
    static PlayGameSingleton instance;
    return instance;
}

#pragma mark - Single Leaderboard
void PlayGameSingleton::showSingleLeaderboard(const char* leaderBoardID)
{
    
    if(!isSignedIn())
    {
        authenticate();
        return;
    }
    
    if(!viewSingleLeaderboard)
        viewSingleLeaderboard = [[ViewSingleLeaderboard alloc] init];
    
    
    UIWindow *window =  [[UIApplication sharedApplication] keyWindow];
    
    if(!rootController)
        rootController = window.rootViewController;
    
    [((UIViewController *) rootController).view addSubview: viewSingleLeaderboard.view];
    
    /*
    // Set RootViewController to window
    if ([[UIDevice currentDevice].systemVersion floatValue] < 6.0)
    {
        // warning: addSubView doesn't work on iOS6
        [window addSubview: viewSingleLeaderboard.view];
    } else {
        // use this method on ios6
        // [window setRootViewController:viewSingleLeaderboard];
        [window addSubview: viewSingleLeaderboard.view];
    }
    */
    [viewSingleLeaderboard showLeaderboard:[NSString stringWithUTF8String:leaderBoardID]];
    
}

void PlayGameSingleton::finishSingleLeaderboard()
{
    // UIWindow *window =  [[UIApplication sharedApplication] keyWindow];
    // if (!rootController)
        //rootController = window.rootViewController;
    
    // if ([[UIDevice currentDevice].systemVersion floatValue] < 6.0) {
    [viewSingleLeaderboard.view removeFromSuperview];
    // }
    
    [viewSingleLeaderboard release];
    viewSingleLeaderboard = 0;
    
    // [window setRootViewController:(UIViewController *)rootController];
    
}

#pragma mark - Leaderboards Picker
void PlayGameSingleton::showLeaderboards()
{
    
    if(!isSignedIn())
    {
        authenticate();
        return;
    }
    
    if(!viewLeaderboardPicker)
        viewLeaderboardPicker = [[ViewLeaderboardPicker alloc] init];
    
    
    UIWindow *window =  [[UIApplication sharedApplication] keyWindow];
    
    if(!rootController)
        rootController = window.rootViewController;
    
    [((UIViewController *) rootController).view addSubview: viewLeaderboardPicker.view];

    [viewLeaderboardPicker showLeaderboards];
}

void PlayGameSingleton::finishLeaderboards()
{
    [viewLeaderboardPicker.view removeFromSuperview];
    [viewLeaderboardPicker release];
    viewLeaderboardPicker = 0;
}

#pragma mark - Submit score
void PlayGameSingleton::submitScore(long score, const char *leaderBoardID)
{
    
    if(!isSignedIn())
        return;
    
    GPGScore* myScore = [[GPGScore alloc] initWithLeaderboardId:[NSString stringWithUTF8String:leaderBoardID]];
    myScore.value = score;
    
    [myScore submitScoreWithCompletionHandler:^(GPGScoreReport *report, NSError *error) {
        if(error)
        {
            // Handle the error
        }
        else
        {
            // Analyze the report, if you'd like
            if(![report isHighScoreForLocalPlayerAllTime])
            {
                NSLog(@"%lld is a good score, but it's not enough to beat your all-time score of %@!",
                      report.reportedScoreValue, report.highScoreForLocalPlayerAllTime.formattedScore);
            }
        }
    }];
    
    CCLog("Score submitted: %lu", score);

}

#pragma mark - Achievements
void PlayGameSingleton::showAchievements()
{
    
    if(!isSignedIn())
    {
        authenticate();
        return;
    }
    
    if(!viewAchiemevents)
        viewAchiemevents = [[ViewAchievements alloc] init];
    
    
    UIWindow *window =  [[UIApplication sharedApplication] keyWindow];
    
    if(!rootController)
        rootController = window.rootViewController;
    
    [((UIViewController *) rootController).view addSubview: viewAchiemevents.view];
    
    [viewAchiemevents showAchievements];
}

void PlayGameSingleton::finishAchievements()
{
    [viewAchiemevents.view removeFromSuperview];
    [viewAchiemevents release];
    viewAchiemevents = 0;
}

#pragma mark - Manage achievements
void PlayGameSingleton::revealAchievement(const char *achievementID)
{
    
    if(!isSignedIn())
    {
        authenticate();
        return;
    }
    
    GPGAchievement* revealMe = [GPGAchievement achievementWithId:[NSString stringWithUTF8String:achievementID]];
    
    [revealMe revealAchievementWithCompletionHandler:^(GPGAchievementState state, NSError *error) {
        if(error)
        {
            // Handle the error
        }
        else
        {
            // Achievement revealed!
        }
    }];
}

void PlayGameSingleton::unlockAchievement(const char *achievementID)
{
    
    if(!isSignedIn())
    {
        authenticate();
        return;
    }
    
    // [GPGManager sharedInstance].achievementUnlockedToastPlacement = kGPGToastPlacementTop;
    // Specify our offset to be 20 points
    // [GPGManager sharedInstance].achievementUnlockedOffset = 20;
    
    GPGAchievement* unlockMe = [GPGAchievement achievementWithId:[NSString stringWithUTF8String:achievementID]];
    // unlockMe.showsCompletionNotification = NO;
    
    [unlockMe unlockAchievementWithCompletionHandler:^(BOOL newlyUnlocked, NSError *error) {
        if(error)
        {
            // Handle the error
            CCLog("Achievement Error");
        }
        else if (!newlyUnlocked)
        {
            // Achievement was already unlocked
            CCLog("Achievement Already Unlocked");
        }
        else
        {
            // Achievement unlocked!
            CCLog("Achievement Unlocked");
        }
        CCLog("Achievement %s", achievementID);
    }];
}

void PlayGameSingleton::incrementAchievement(int numSteps, const char *achievementID)
{
    
    if(!isSignedIn())
    {
        authenticate();
        return;
    }
    
    GPGAchievement* incrementMe = [GPGAchievement achievementWithId:[NSString stringWithUTF8String:achievementID]];
    
    [incrementMe incrementAchievementNumSteps:numSteps completionHandler:^(BOOL newlyUnlocked, int currentSteps, NSError *error) {
        if(error)
        {
            // Handle the error
        }
        else if (newlyUnlocked)
        {
            // Incremental achievement unlocked
        }
        else
        {
            // User has completed the steps total: currentSteps
        }
    }];
}

#pragma mark - Login configuration
void PlayGameSingleton::trySilentAuthentication()
{
    [[GPPSignIn sharedInstance] trySilentAuthentication];
}

void PlayGameSingleton::authenticate()
{
    [[GPPSignIn sharedInstance] authenticate];
}

bool PlayGameSingleton::isSignedIn()
{
    bool signedIn = [GPGManager sharedInstance].isSignedIn;
    CCLog("signedIn: %d", signedIn);
    return signedIn;
}

void PlayGameSingleton::signOut()
{
    if(isSignedIn())
    {
        [[GPGManager sharedInstance] signOut];
    }
}



