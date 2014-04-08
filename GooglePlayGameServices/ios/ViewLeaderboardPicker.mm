//
//  ViewLeaderboardPicker.m
//  GooglePlayGameServices
//
//  Created by NSS on 4/7/14.
//
//

#import "ViewLeaderboardPicker.h"
#import "PlayGameSingleton.h"

@interface ViewLeaderboardPicker ()

@end

@implementation ViewLeaderboardPicker

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        NSLog(@"Single Leaderboard Picker Screen Ready");
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

- (void)leaderboardsViewControllerDidFinish: (GPGLeaderboardsController *)viewController {
    [self dismissViewControllerAnimated:YES completion:nil];
}

-(void) showLeaderboards
{
    GPGLeaderboardsController *leadsController = [[GPGLeaderboardsController alloc] init];
    leadsController.leaderboardsDelegate = self;
    [self presentViewController:leadsController animated:YES completion:nil];
}

@end
