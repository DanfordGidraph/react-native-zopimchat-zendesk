//
//  RNZendeskChat.m
//  Tasker
//
//  Created by Jean-Richard Lai on 11/23/15.
//  Copyright © 2015 Facebook. All rights reserved.
// 
//  Modified by Gidraph Dunfrord on 29/May/2019
//  Copyright © 2019 ZaidiMarvels. All rights reserved.
//

#import "RNZendeskChatPushModule.h"
#import <ZDCChat/ZDCChat.h>

@implementation RNZendeskChatPushModule

RCT_EXPORT_MODULE(RNZendeskChatPushModule);

RCT_EXPORT_METHOD(setPushToken: (NSData*)tokenData) {
	[ZDCChat setPushToken:tokenData];
}

RCT_EXPORT_METHOD(setVisitorInfo:(NSDictionary *)options) {
  [ZDCChat updateVisitor:^(ZDCVisitorInfo *visitor) {
    if (options[@"name"]) {
      visitor.name = options[@"name"];
    }
    if (options[@"email"]) {
      visitor.email = options[@"email"];
    }
    if (options[@"phone"]) {
      visitor.phone = options[@"phone"];
    }
    visitor.shouldPersist = options[@"shouldPersist"] || NO;
  }];
}

RCT_EXPORT_METHOD(startChat:(NSDictionary *)options) {
  [self setVisitorInfo:options];

  dispatch_sync(dispatch_get_main_queue(), ^{
    [ZDCChat startChat:^(ZDCConfig *config) {
      if (options[@"department"]) {
        config.department = options[@"department"];
      }
      if (options[@"tags"]) {
        config.tags = options[@"tags"];
      }
      config.preChatDataRequirements.name       = ZDCPreChatDataRequired;
      config.preChatDataRequirements.email      = options[@"emailNotRequired"] ? ZDCPreChatDataNotRequired : ZDCPreChatDataRequired;
      config.preChatDataRequirements.phone      = options[@"phoneNotRequired"] ? ZDCPreChatDataNotRequired : ZDCPreChatDataRequired;
      config.preChatDataRequirements.department = options[@"departmentNotRequired"] ? ZDCPreChatDataNotRequired : ZDCPreChatDataRequiredEditable;
      config.preChatDataRequirements.message    = options[@"messageNotRequired"] ? ZDCPreChatDataNotRequired : ZDCPreChatDataRequired;
    }];
  });
}

@end
