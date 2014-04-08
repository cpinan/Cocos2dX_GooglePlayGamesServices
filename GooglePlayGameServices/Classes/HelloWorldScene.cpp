#include "HelloWorldScene.h"
#include "SimpleAudioEngine.h"
#include "PlayGameConstants.h"

#include "NativeUtils.h"

#include <cstdlib>
#include <ctime>

using namespace cocos2d;
using namespace CocosDenshion;

CCScene* HelloWorld::scene()
{
    // 'scene' is an autorelease object
    CCScene *scene = CCScene::create();
    
    // 'layer' is an autorelease object
    HelloWorld *layer = HelloWorld::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

// on "init" you need to initialize your instance
bool HelloWorld::init()
{
    //////////////////////////////
    // 1. super init first
    if ( !CCLayer::init() )
    {
        return false;
    }
    
    srand(time(0));

    /////////////////////////////
    // 2. add a menu item with "X" image, which is clicked to quit the program
    //    you may modify it.

    // add a "close" icon to exit the progress. it's an autorelease object
    CCMenuItemImage *pCloseItem = CCMenuItemImage::create(
                                        "CloseNormal.png",
                                        "CloseSelected.png",
                                        this,
                                        menu_selector(HelloWorld::menuCloseCallback) );
    pCloseItem->setPosition( ccp(CCDirector::sharedDirector()->getWinSize().width - 20, 20) );

    // create menu, it's an autorelease object
    CCMenu* pMenu = CCMenu::create(pCloseItem, NULL);
    pMenu->setPosition( CCPointZero );
    this->addChild(pMenu, 1);

    /////////////////////////////
    // 3. add your codes below...

    // add a label shows "Hello World"
    // create and initialize a label
    CCLabelTTF* pLabel = CCLabelTTF::create("Hello World", "Thonburi", 34);

    // ask director the window size
    CCSize size = CCDirector::sharedDirector()->getWinSize();

    // position the label on the center of the screen
    pLabel->setPosition( ccp(size.width / 2, size.height - 20) );

    // add the label as a child to this layer
    this->addChild(pLabel, 1);

    // add "HelloWorld" splash screen"
    CCSprite* pSprite = CCSprite::create("HelloWorld.png");

    // position the sprite on the center of the screen
    pSprite->setPosition( ccp(size.width/2, size.height/2) );

    // add the sprite as a child to this layer
    this->addChild(pSprite, 0);
    
    //
    CCLabelTTF* _btnLeaderboards = CCLabelTTF::create("Leaderboard", "Arial", 50.0f);
    _btnLeaderboards->setColor(ccc3(255, 0, 0));
    
    CCMenuItemLabel* _itemLeaderboard = CCMenuItemLabel::create(_btnLeaderboards, this, menu_selector(HelloWorld::_onButtonSelected));
    _itemLeaderboard->setTag(1);
    _itemLeaderboard->setPosition(ccp(200, 200));
    
    // Submit score
    CCLabelTTF* _btnSubmitScore = CCLabelTTF::create("Submit score", "Arial", 50.0f);
    _btnSubmitScore->setColor(ccc3(255, 0, 0));
    
    CCMenuItemLabel* _itemSubmitScore = CCMenuItemLabel::create(_btnSubmitScore, this, menu_selector(HelloWorld::_onButtonSelected));
    _itemSubmitScore->setTag(2);
    _itemSubmitScore->setPosition(ccp(200, 300));
    
    //
    CCLabelTTF* _btnAchievements = CCLabelTTF::create("Achievements", "Arial", 50.0f);
    _btnAchievements->setColor(ccc3(255, 0, 0));
    
    CCMenuItemLabel* _itemAchievements = CCMenuItemLabel::create(_btnAchievements, this, menu_selector(HelloWorld::_onButtonSelected));
    _itemAchievements->setTag(3);
    _itemAchievements->setPosition(ccp(200, 400));
    
    //
    CCLabelTTF* _btnUnlockAchievement = CCLabelTTF::create("Unlock Achi", "Arial", 50.0f);
    _btnUnlockAchievement->setColor(ccc3(255, 0, 0));
    
    CCMenuItemLabel* _itemUnlock = CCMenuItemLabel::create(_btnUnlockAchievement, this, menu_selector(HelloWorld::_onButtonSelected));
    _itemUnlock->setTag(4);
    _itemUnlock->setPosition(ccp(200, 500));
    
    CCMenu* menu = CCMenu::create();
    menu->setPosition(CCPointZero);
    
    menu->addChild(_itemLeaderboard);
    menu->addChild(_itemSubmitScore);
    menu->addChild(_itemAchievements);
    menu->addChild(_itemUnlock);
    
    addChild(menu, 100);
    
    return true;
}

void HelloWorld::onEnterTransitionDidFinish()
{
}

void HelloWorld::menuCloseCallback(CCObject* pSender)
{
    CCDirector::sharedDirector()->end();

#if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
}

void HelloWorld::_onButtonSelected(CCObject *pSender)
{
    CCMenuItem* item = (CCMenuItem *) pSender;
    int tag = item->getTag();
    
    if(tag == 1)
    {
        // NativeUtils::showLeaderboard(LEADERBOARD_HARD_MODE);
        NativeUtils::showLeaderboards();
    }
    else if(tag == 2)
    {
        long score = 3500 * CCRANDOM_0_1() + 1000;
        NativeUtils::submitScore(LEADERBOARD_HARD_MODE, score);
    }
    else if(tag == 3)
    {
        NativeUtils::showAchievements();
    }
    else if(tag == 4)
    {
        NativeUtils::unlockAchievement(ACHIEVEMENT_005);
    }
}
