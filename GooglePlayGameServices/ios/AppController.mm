//
//  GooglePlayGameServicesAppController.mm
//  GooglePlayGameServices
//
//  Created by NSS on 4/7/14.
//  Copyright __MyCompanyName__ 2014. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "AppController.h"
#import "cocos2d.h"
#import "EAGLView.h"
#import "AppDelegate.h"
#import "PlayGameConstants.h"
#import "PlayGameSingleton.h"

#import "RootViewController.h"
#import <GoogleOpenSource/GoogleOpenSource.h>

@implementation AppController

@synthesize window;
@synthesize viewController;

#pragma mark -
#pragma mark Application lifecycle

// cocos2d application instance
static AppDelegate s_sharedApplication;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    //
    
    GPPSignIn* signIn = [GPPSignIn sharedInstance];
    signIn.clientID = [NSString stringWithUTF8String:kClientID];    
    signIn.scopes = [NSArray arrayWithObjects:
                                                @"https://www.googleapis.com/auth/games",
                                                @"https://www.googleapis.com/auth/appstate",
                                                nil];
    signIn.language = [[NSLocale preferredLanguages] objectAtIndex:0];
    signIn.delegate = self;
    signIn.shouldFetchGoogleUserID = YES;
    
    [GPGManager sharedInstance].achievementUnlockedToastPlacement = kGPGToastPlacementTop;
    
    if(!PlayGameSingleton::sharedInstance().isSignedIn())
    {
        PlayGameSingleton::sharedInstance().trySilentAuthentication();
        [NSThread detachNewThreadSelector:@selector(playServicesAuthenticate) toTarget:self withObject:nil];
    }

        //[self performSelectorInBackground:@selector(playServicesAuthenticate) withObject:nil];

    
    //
    

    // Override point for customization after application launch.

    // Add the view controller's view to the window and display.
    window = [[UIWindow alloc] initWithFrame: [[UIScreen mainScreen] bounds]];
    EAGLView *__glView = [EAGLView viewWithFrame: [window bounds]
                                     pixelFormat: kEAGLColorFormatRGBA8
                                     depthFormat: GL_DEPTH_COMPONENT16
                              preserveBackbuffer: NO
                                      sharegroup: nil
                                   multiSampling: NO
                                 numberOfSamples:0 ];

    // Use RootViewController manage EAGLView
    viewController = [[RootViewController alloc] initWithNibName:nil bundle:nil];
    viewController.wantsFullScreenLayout = YES;
    viewController.view = __glView;

    // Set RootViewController to window
    if ( [[UIDevice currentDevice].systemVersion floatValue] < 6.0)
    {
        // warning: addSubView doesn't work on iOS6
        [window addSubview: viewController.view];
    }
    else
    {
        // use this method on ios6
        [window setRootViewController:viewController];
    }
    
    [window makeKeyAndVisible];

    [[UIApplication sharedApplication] setStatusBarHidden: YES];

    cocos2d::CCApplication::sharedApplication()->run();
    
    return YES;
}

- (void)playServicesAuthenticate
{
    NSLog(@"Authenticate");
    PlayGameSingleton::sharedInstance().authenticate();
}


- (void)applicationWillResignActive:(UIApplication *)application {
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
    cocos2d::CCDirector::sharedDirector()->pause();
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
    cocos2d::CCDirector::sharedDirector()->resume();
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
     If your application supports background execution, called instead of applicationWillTerminate: when the user quits.
     */
    cocos2d::CCApplication::sharedApplication()->applicationDidEnterBackground();
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    /*
     Called as part of  transition from the background to the inactive state: here you can undo many of the changes made on entering the background.
     */
    cocos2d::CCApplication::sharedApplication()->applicationWillEnterForeground();
}

- (void)applicationWillTerminate:(UIApplication *)application {
    /*
     Called when the application is about to terminate.
     See also applicationDidEnterBackground:.
     */
}


#pragma mark -
#pragma mark Memory management

- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application {
    /*
     Free up as much memory as possible by purging cached data objects that can be recreated (or reloaded from disk) later.
     */
     cocos2d::CCDirector::sharedDirector()->purgeCachedData();
}


- (void)dealloc {
    [super dealloc];
}

#pragma mark - Google Play Game Services

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
    return [GPPURLHandler handleURL:url sourceApplication:sourceApplication annotation:annotation];
}

- (void)finishedWithAuth:(GTMOAuth2Authentication *)auth error:(NSError *)error
{
    NSLog(@"Finished with auth.");
    //if (error == nil && auth) {
        //NSLog(@"Success signing in to Google! Auth object is %@", auth);
        
    if (error.code == 0 && auth) {
        NSLog(@"Success signing in to Google! Auth object is %@", auth);
        
        // Tell your GPGManager that you're ready to go.
        [self startGoogleGamesSignIn];
        
    } else {
        NSLog(@"Failed to log into Google\n\tError=%@\n\tAuthObj=%@",error,auth);
    }
}

-(void)startGoogleGamesSignIn
{
    // The GPPSignIn object has an auth token now. Pass it to the GPGManager.
    [[GPGManager sharedInstance] signIn:[GPPSignIn sharedInstance]
                     reauthorizeHandler:^(BOOL requiresKeychainWipe, NSError *error) {
                         // If you hit this, auth has failed and you need to authenticate.
                         // Most likely you can refresh behind the scenes
                         if (requiresKeychainWipe) {
                             [[GPPSignIn sharedInstance] signOut];
                         }
                         [[GPPSignIn sharedInstance] authenticate];
                     }];
}

@end

