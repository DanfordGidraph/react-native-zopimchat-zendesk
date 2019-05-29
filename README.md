# react-native-zopimchat-zendesk

Simple module that allows displaying Zopim Chat from Zendesk for React Native With Support for push notifications. Module works for both Android and iOS
#### NOTE
This package was inspired by [react-native-zendesk-chat](https://github.com/taskrabbit/react-native-zendesk-chat)

`department` is set to `ZDCPreChatDataRequiredEditable`.

## Getting started

### Further Reading (Important)

Follow the instructions and understand how nativeley to install the SDK for. This will assist you in troubleshooting installing & configuring the module
####
[iOS](https://developer.zendesk.com/embeddables/docs/ios-chat-sdk/introduction) 
####
[Android](https://developer.zendesk.com/embeddables/docs/android-chat-sdk/introduction).

## Pre-Reqisites

To use the push notifications features you must first install firebase. I recommend React Native Firebase. [](https://rnfirebase.io)
####
[iOS](https://rnfirebase.io/docs/v5.x.x/installation/ios) 
####
[Android](https://rnfirebase.io/docs/v5.x.x/installation/android)

Then go through the installation for enabling notifications with the rnfirebase package 
####
[iOS](https://rnfirebase.io/docs/v5.x.x/messaging/ios) 
####
[Android](https://rnfirebase.io/docs/v5.x.x/messaging/android)

### Installation

Run in terminal
```groovy
    npm i react-native-zopimchat-zendesk
```

### Automatic Integration

### Using React Native Link

Run 
```groovy
react-native link react-native-zopimchat-zendesk
```
 after which you should be able to use this library on iOS and Android directly


### Manual Integration

#### iOS
1. `npm install react-native-zopimchat-zendesk --save`
2. In Xcode, drag and drop `node_modules/react-native-zopimchat-zendesk/RNZopimChatModule.m` and `node_modules/react-native-zopimchat-zendesk/RNZopimChatModule.h` into your project.
3. Configure `ZDCChat` in your `AppDelegate.m`:

```
#import <ZDCChat/ZDCChat.h>

[ZDCChat initializeWithAccountKey:@"YOUR_ZENDESK_ACCOUNT_KEY"];
```

#### Android

1. Define the `react-native-zopimchat-zendesk` project in `android/settings.gradle`:

```groovy
...
include ':react-native-zopimchat-zendesk'
project(':react-native-zopimchat-zendesk').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-zopimchat-zendesk/android')
```

2. Add the `react-native-zopimchat-zendesk` as an dependency of your app in `android/app/build.gradle`:

```groovy
...
dependencies {
  ...
  implementation project(':react-native-firebase')
  implementation (project(':react-native-zopimchat-zendesk')){ // <--- Add this block
      exclude group: 'com.android.support'
      exclude module: 'appcompat-v7'
      exclude module: 'support-v4'
    } 
}
```

If you've defined *[project-wide
properties](https://developer.android.com/studio/build/gradle-tips.html)*
(**recommended**) in your root `build.gradle`, this library will detect
the presence of the following properties: (Project level gradle file) in ` android/build.gradle `
```groovy
buildscript {
  ...
     repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.3.2'
        classpath 'com.google.gms:google-services:4.2.0'
        classpath 'com.google.firebase:firebase-plugins:1.1.5'
    }

    /**
    + Project-wide Gradle configuration properties 
    */
    ext {
        compileSdkVersion   = 28
        targetSdkVersion    = 28
        buildToolsVersion   = "28.0.3"
        supportLibVersion   = "28.0.0"
        googlePlayServicesVersion = "16.1.0" // or set latest version HERE
    }
    ...
}
allprojects {
  ...
    repositories {
        mavenLocal()
        jcenter()
        google()
        maven {
            // All of React Native (JS, Obj-C sources, Android binaries) is installed from npm
            url "$rootDir/../node_modules/react-native/android"
        }
        // Crashlytics repository
        maven { url 'https://maven.google.com'}
        maven { url 'https://zendesk.jfrog.io/zendesk/repo' }
    }
  ...
}
 
```

If you do **not** have *project-wide properties* defined and have a
different play-services version than the one included in this library,
use the following instead (switch 16.0.1 for the desired version):

```groovy
...
dependencies {
  ...
    // Google and Firebase Dependencies
    implementation project(':react-native-firebase')
    implementation "com.google.android.gms:play-services-base:16.0.1"
    implementation 'com.google.firebase:firebase-core:16.0.8'
    implementation 'com.google.firebase:firebase-messaging:17.5.0'

    implementation (project(':react-native-zopimchat-zendesk')){
      exclude group: 'com.android.support'
      exclude module: 'appcompat-v7'
      exclude module: 'support-v4'
    }
    ...
}
```
3. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `com.zaidiapps.zopimchat.zendesk.RNZopimChatPackage;` to the imports at the top of the file
  - Add `new RNZopimChatPackage()` to the list returned by the `getPackages()` method :

```java
// Firebase packages
import io.invertase.firebase.RNFirebasePackage;
import io.invertase.firebase.messaging.RNFirebaseMessagingPackage;
import io.invertase.firebase.notifications.RNFirebaseNotificationsPackage; 
// Zopim Chat package
import com.zaidiapps.zopimchat.zendesk.RNZopimChatPackage; // <---- Add this line here
...
    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new RNFirebasePackage(),
                new RNFirebaseMessagingPackage(),
                new RNFirebaseNotificationsPackage(),
                new RNZopimChatPackage() // <---- Add this line here
        );
    }
```

4. Configure `ZopimChat` in `android/app/src/main/java/[...]/MainApplication.java`

```java
...
  static RNZopimChatModule rnZopimChatModule; // <--- Instantiate this ZopimChatModule variable here

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
    // Init the chat module from taskrabbit
    ReactApplicationContext reactContext = new ReactApplicationContext(this);
    rnZopimChatModule = new RNZopimChatModule(reactContext); // <--- Initialize the module here
    rnZopimChatModule.init("YOUR_ZENDESK_ACCOUNT_KEY"); // <---- Set your account key here to initialize the module
  }
  ...
```

## Push Notifications
### Android 
#### Adding the server key to Chat
You must be a Chat admin to upload the server key. If you're not a Chat admin, ask one to do it for you and provide them with the key. For the admin instructions, see 
[Enabling push notifications for the Chat SDK](https://chat.zendesk.com/hc/en-us/articles/360000456648) for mobile in the Chat Help Center.

##### Note
If your app is targeting Android O and above, make sure the app properly handles the notification channel for these push notifications.

After the Chat admin confirms they've uploaded the server key to the account, enable push notifications in your app as described in the next section.

#### Enabling push notifications in your app
You need to register devices interested in receiving push notifications with Zendesk Chat.

First, go through the [following guide from React Native Firebase](https://rnfirebase.io/docs/v5.x.x/messaging/android) on implementing an FCM client on Android. After initializing Firebase you will be able to access the device registration token.

### iOS
#### Generating a combined PEM file
To generate the combined PEM file, export the APNS SSL certificate and the private key from Keychain Access on your Mac computer.

When exporting, don't enter a password when asked about protecting the export.

If you don't know how to combine the certificate PEM and server key PEM, use the following command in Terminal:

``` 
cat PushAppCert.pem PushAppKey.pem > PushAppCertAndKey.pem
```
Then test the combined PEM file against the sandbox Apple server using the following command:

telnet gateway.sandbox.push.apple.com 2195
Finally, test the connection using the SSL certificate and private key to set up a secure connection:

```
openssl s_client -connect gateway.sandbox.push.apple.com:2195 -cert PushChatCert.pem -key PushChatKey.pem
```
#### Uploading the combined PEM file to Chat
You must be a Chat admin to upload the PEM file. If you're not a Chat admin, ask one to do it for you and provide them with the PEM file. For the admin instructions, see [Enabling push notifications for the Chat SDK](https://chat.zendesk.com/hc/en-us/articles/360000456648) for mobile in the Chat Help Center.

Make sure you use the proper certificate and key in the PEM file for the profile you're using. For example, *don't use a combined PEM file for staging in the production profile of the app.*

After the Chat admin confirms they've uploaded the PEM file to the account successfully, enable push notifications as described in the next section.

### Enabling Push Notifications (JavaScript)
Send the user token Zendesk Chat servers through the Chat SDK as follows to register the device:
```javascript
ZendeskChat.setPushToken(token)
```

## Styling
The default Activity provided by Zendesk for the Chat may not fit into your App Style. 

### Android
Therefore add the following to your styles.xml in `androud/app/src/main/res/values/styles.xml`
```xml
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="android:statusBarColor">@color/theme_color</item>
    </style>
...

      <style name="ZopimChatTheme" parent="AppTheme">
         <!--Chat UI uses the toolbar so no need to show ActionBar-->
         <item name="windowActionBar">false</item>
        <!-- Customize chat theme here. -->
        <item name="colorAccent">@color/theme_color</item>
        <item name="colorPrimary">@color/theme_color</item>
        <item name="colorPrimaryDark">@color/statusBarColor</item>
    </style>

    <style name="no_agents_button">
        <item name="android:background">@color/theme_color</item>
        <item name="android:padding">8dp</item>
        <item name="android:textColor">@android:color/white</item>
    </style>

    <!--Customize toolbar that replaces action bar-->
    <style name="zopim_toolbar">
        <item name="android:background">?attr/colorPrimary</item>
        <item name="android:minHeight">?attr/actionBarSize</item>
        <item name="android:theme">@style/ThemeOverlay.AppCompat.Dark.ActionBar</item>
        <item name="popupTheme">@style/ThemeOverlay.AppCompat.Light</item>
    </style>

    <!--Customize where chat widget appears-->
    <style name="chat_widget" parent="_chat_widget">
        <item name="anchor">bottom_left</item>
    </style>

    <!--Customize chat colors-->
    <color name="chat_bubble_visitor">@color/theme_color</color>
    
...

</resources>

```
Remember to add all the referenced colors to your colors.xml file in `androud/app/src/main/res/values/colors.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
...
    <color name="theme_color">#00A19B</color>
    <color name="colorAccent" >#fff</color>
    <color name="Black">#000000</color>
    <color name="White">#ffffff</color>
    <color name="Transparent">#00000000</color>
...
</resources>

```
There are many more styling options available for Android. [HERE](https://developer.zendesk.com/embeddables/docs/android-chat-sdk/customization)


### iOS

```objective-c
    // loading the chat
    [[ZDCLoadingView appearance] set...]
    [[ZDCLoadingErrorView appearance] set...]

    // offline message view
    [[ZDCOfflineMessageView appearance] set...]

    // chat view
    [[ZDCChatView appearance] set...]
    [[ZDCTextEntryView appearance] set...]
    [[ZDCJoinLeaveCell appearance] set...]
    [[ZDCSystemTriggerCell appearance] set...]
    [[ZDCVisitorChatCell appearance] set...]
    [[ZDCAgentChatCell appearance] set...]
    [[ZDCChatTimedOutCell appearance] set...]
    [[ZDCRatingCell appearance] set...]
    [[ZDCAgentAttachmentCell appearance] set...]
    [[ZDCVisitorAttachmentCell appearance] set...]

    // chat UI and nav buttons
    [[ZDCChatUI appearance] set...]

    // chat overlay icon
    [[ZDCChatOverlay appearance] set...]

    // agent chat avatar
    [[ZDCChatAvatar appearance] set...]

```
There are many more styling options available for iOS. [HERE](https://developer.zendesk.com/embeddables/docs/ios-chat-sdk/customization)

## Usage

### In your code 

#### App Container 
```javascript
import React from 'react'
import { AsyncStorage } from 'react-native'
import ZendeskChat from 'react-native-zendesk-chat'
import firebase from 'react-native-firebase'
import Router from './Router'

class App extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      fcm_token: '',
      alert: {
        title: '', message: '', confirmText: '', showLoading: false,
      },
    }
  }

  render() {
    const { alert } = this.state
    return (
      <Router screenProps={
          { alert }
        }
      />
    )
  }


  _showLocalNotification = (notif) => {
    let notification = new firebase.notifications.Notification()
    notification = notification.setNotificationId(new Date().valueOf().toString())
      .setNotificationId(notif.notificationId)
      .setTitle(notif.title)
      .setSubtitle(notif.subtitle)
      .setBody(notif.body)
      .setData(notif.data)
      .setSound('notif_sound.mp3') // Found in android/app/src/main/src/res/raw/

    notification.ios.badge = 10
    notification.android.setAutoCancel(true)
    notification.android.setColorized(true)
    notification.android.setOngoing(true)
    notification.android.setPriority(firebase.notifications.Android.Priority.High)
    notification.android.setSmallIcon('ic_stat_notif_icon')
    notification.android.setLargeIcon('ic_launcher')
    notification.android.setVibrate([500])
    notification.android.setChannelId('main_channel')

    firebase.notifications().displayNotification(notification)
  }

  // This method handles notifications based on the state the app is in upon receiving a notif i.e whether closed or open
  _dealWithNotification = (notif, source) => {
    let alert_title = ''
    let alert_message = ''
    let alert_button_text = ''
    let destination = ''
    let notif_type = ''
    let notif_data = {}
    if ('data' in notif) {
      const { data } = notif
      notif_data = data
      alert_title = 'alert_title' in data ? data.alert_title : 'Urgent Message'
      alert_message = 'alert_message' in data ? data.alert_message : 'Urgent Message'
      destination = 'destination' in data ? data.destination : null // The destination to navigate to (Zendesk component screen below)
      notif_type = 'notif_type' in data ? data.notif_type : null // Check if the type is zendesk or other as shown in the docs below    
    }

    if (source === 'app_open') { // Notification opened from app open foreground/background state
      if (notif_type === 'alert') {
        this.setState(alert)
      }
    } else if (source === 'app_closed') { // Notification opened from app open closed state.
      AsyncStorage.multiSet([ // Cache the notif to use it on the prefered screen if need be
        ['pending_notif', notif_type],
        ['notif_data', JSON.stringify(notif_data)],
      ])
    }
  }


  // The app listener handles notifications that came in while the app is in the foreground or in open state in the background.
  _registerAppListener() {
    this.notificationListener = firebase.notifications().onNotification((notification) => {
      this._showLocalNotification(notification)
    })
    this.notificationOpenedListener = firebase.notifications().onNotificationOpened((notificationOpen) => {
      const notif = notificationOpen.notification
      this._dealWithNotification(notif, 'app_open')
    });

    this.onTokenRefreshListener = firebase.messaging().onTokenRefresh((token) => {
      this.setState({ fcm_token: token }, () => {
        ZendeskChat.setPushToken(token)
      })
    });

    this.messageListener = firebase.messaging().onMessage((message) => {
      _showLocalNotification(message)
    });
  }


  componentDidMount = async () => {
    const channel = new firebase.notifications.Android.Channel('main_channel', 'App Main Channel', firebase.notifications.Android.Importance.Max)
      .setDescription('My App Mobile Main Notification Channel ')
    // Create the channel
    firebase.notifications().android.createChannel(channel)
    this._registerAppListener()
    // Here we retrieve the notification that woke the App from state CLOSED and handle it
    firebase.notifications().getInitialNotification()
      .then((notificationOpen) => {
        if (notificationOpen) {
        // Get information about the notification that was opened
          const notif = notificationOpen.notification;
          this._dealWithNotification(notif, 'app_closed')
        }
      })
    // As of Android M permissions must be requested explicitly
    if (!await firebase.messaging().hasPermission()) {
      try {
        await firebase.messaging().requestPermission();
      } catch (e) {
        // alert('Failed to grant permission')
      }
    }
    firebase.messaging().getToken().then((token) => {
      this.setState({ fcm_token: token })
      ZendeskChat.setPushToken(token)
    })
  }

  ...
```

  ####
  ##### For Returned Android Notifications See the Documentation [Android](https://developer.zendesk.com/embeddables/docs/android-chat-sdk/push_notifications)
  ##### For Returned iOS Notifications See the Documentation [iOS](https://developer.zendesk.com/embeddables/docs/ios-chat-sdk/push_notifications)
  ####

#### Other Component

```javascript
...
import ZendeskChat from 'react-native-zopimchat-zendesk';
...

ZendeskChat.startChat({
  name: user.full_name,
  email: user.email,
  phone: user.mobile_phone,
  tags: ['tag1', 'tag2'],
  department: "Your department"
});

```

## TODO

* Add examples
