//
//  ViewAchievements.m
//  GooglePlayGameServices
//
//  Created by NSS on 4/7/14.
//
//

#import "ViewAchievements.h"
#include "PlayGameSingleton.h"

@interface ViewAchievements ()

@end

@implementation ViewAchievements

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
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

#pragma mark - Achievements Google Play Game Services
- (void)achievementViewControllerDidFinish: (GPGAchievementController *)viewController {
    [self dismissViewControllerAnimated:YES completion:nil];
    PlayGameSingleton::sharedInstance().finishAchievements();
}

-(void) showAchievements
{
    GPGAchievementController *achController = [[GPGAchievementController alloc] init];
    achController.achievementDelegate = self;
    [self presentViewController:achController animated:YES completion:nil];
}

@end
