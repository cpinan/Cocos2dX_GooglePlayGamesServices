//
//  ViewSingleLeaderboard.m
//  GooglePlayGameServices
//
//  Created by NSS on 4/7/14.
//
//

#import "ViewSingleLeaderboard.h"
#import "PlayGameSingleton.h"

@interface ViewSingleLeaderboard ()

@end

@implementation ViewSingleLeaderboard


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {

        NSLog(@"Single Leaderboard Screen Ready");
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)showLeaderboard: (NSString *) leaderBoardID
{
    // You get this leaderboard Id from the Developer Console
    
    GPGLeaderboardController *leadController = [[GPGLeaderboardController alloc] initWithLeaderboardId:leaderBoardID];
    
    // Newly-added lines to set the timescope
    // leadController.timeScope = GPGLeaderboardTimeScopeThisWeek;
    
    leadController.leaderboardDelegate = self;
    [self presentViewController:leadController animated:YES completion:nil];
    
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

#pragma mark - Leader Board View Controller

- (void)leaderboardViewControllerDidFinish: (GPGLeaderboardController *)viewController
{
    NSLog(@"LeaderBoard View Controller Did Finish");
    [self dismissViewControllerAnimated:YES completion:nil];
    PlayGameSingleton::sharedInstance().finishSingleLeaderboard();
}

@end
