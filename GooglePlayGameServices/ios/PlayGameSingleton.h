//
//  ControllerSingleLeaderboard.h
//  GooglePlayGameServices
//
//  Created by NSS on 4/7/14.
//
//

#ifndef __PLAY_GAME_SINGLETON_H__
#define __PLAY_GAME_SINGLETON_H__

/*
 Copyright (c) 2014 Carlos Eduardo Piñan Indacochea
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

class PlayGameSingleton {
public:
    static PlayGameSingleton& sharedInstance();
    ~PlayGameSingleton();
    PlayGameSingleton();
    
    void showSingleLeaderboard(const char* leaderBoardID);
    void finishSingleLeaderboard();
    
    void showLeaderboards();
    void finishLeaderboards();
    
    void submitScore(long score, const char* leaderBoardID);
    
    void showAchievements();
    void finishAchievements();
    
    void revealAchievement(const char* achievementID);
    void unlockAchievement(const char* achievementID);
    void incrementAchievement(int numSteps, const char* achievementID);
    
    void authenticate();
    void signOut();
    void trySilentAuthentication();
    bool isSignedIn();
    
    void initAd();
    void showAd();
    void hideAd();
    
private:
    void* rootController;
};

#endif
