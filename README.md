# Permissions Module for Android
This library provide a easy solution for resolve permission asking in API 23<.
The compile from supports library v7-23 or v4-23, is not necesary, all dependencie are inse the module.

## Add Dependency

In our rootproject build.gradle file:

```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

And in your android application build.gradle:

```groovy
 compile 'com.github.GigigoGreenLabs:permissionsModule:3.0'
```

## Manifest
You must to especify userpermissions in AndroidManifest.
for example:
```xml
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.READ_CONTACTS"/>
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
...
```


## Getting started #1
 Initialize Permission Manager:
 ```java
 PermissionManager.initialize(this.getApplicationContext());
 ```
## Declare permissions #2
Inside the next package:
```java
package com.gigigo.permissions.permissions;
```
We have one class for each one nine danger-permissions
```java
 public class PermissionCalendar...
 public class PermissionCamera...
 public class PermissionContacts...
 public class PermissionLocation...
 public class PermissionMicrophone...
 public class PermissionPhone...
 public class PermissionSensors...
 public class PermissionSms...
 public class PermissionStorage...
```

And in the package:
```java
package com.gigigo.permissions.groups;
```
We have one enum with the inner permission of each dangerPermission.
```java
 public enum PermissionGroupCalendar...
 public enum PermissionGroupCamera...
 public enum PermissionGroupContacts...
 public enum PermissionGroupLocation...
 public enum PermissionGroupMicrophone...
 public enum PermissionGroupPhone...
 public enum PermissionGroupSensors...
 public enum PermissionGroupSms...
 public enum PermissionGroupStorage...
```
###### Now in your code

Using the Permission classes and Permission Group enum declare your permission instance:

```java
   PermissionCamera permissionCamera
   = new PermissionCamera(this, PermissionGroupCamera.CAMERA);

   PermissionMicrophone permissionMicrophone
   = new PermissionMicrophone(this, PermissionGroupMicrophone.RECORD_AUDIO);
 ...
 ```

## Declare permission Checker #3
In this point you can initialize the PermissionCheckerImpl, for checkPermissions and for asking for it.
You can use the transparent Appcompat Activity inside permissionModule, like this:
```java
permissionChecker = new PermissionCheckerImpl((Activity)this);
```
Or you can use your own Activity for initialize PermissionCheckerImpl
```java
permissionChecker = new PermissionCheckerImpl((PermissionActivity)this);
```
Your activity must to extends PermissionActivity


## Asking Permissions
You can ask each one permission one by one, setting a number of retries or asking all permission your app need in only one multi question permission
#### Single Permission
Asking if the permission is granted:
```java
  boolean granted = permissionChecker.isGranted(permissionCamera);
```
If the permission is not granted, askForPermission:
```java
permissionChecker.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          if (permissionAllowed)
            //PermissionGranted
           else
            //PermissionDenied
        }
      }, permissionPhone);
```
For callback we create new instance of:
```java
com.gigigo.permissions.interfaces.UserPermissionRequestResponseListener()
```

For Example, this will be a onClick method for ask single permission with retries:
```java
public void onCameraPermissionButtonClicked() {
permissionChecker = new PermissionCheckerImpl(this);
PermissionCamera permissionCamera
   = new PermissionCamera(this, PermissionGroupCamera.CAMERA); //declare permission
    boolean granted = permissionChecker.isGranted(permissionCamera); //isgranted
    if (!granted) {
    //askPermission
       permissionChecker.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          if (permissionAllowed) {
            managePermissionGranted(permissionCamera, numberDoneRetries);
          } else {
            managePermissionDenied(permissionCamera, numberDoneRetries);
          }
        }
      }, permissionCamera);
    }
  }
```

#### Multiple Permission
Asking if the permissions are all granted:
```java
  boolean granted = permissionChecker.isAllGranted(permissionCalendar, permissionLocation);
```
If any permission are not granted, askForPermissions:
```java
permissionChecker.askForPermissions(new MultiplePermissionsListener() {
        @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
          if (report.areAllPermissionsGranted()) {
            showPermissionGranted(null, 0);
          } else {
            showPermissionDenied(null, 0);
          }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
            PermissionToken token) {
          showPermissionRationaleMultiple();
        }
      }, permissionCalendar, permissionLocation);
```
For callback we create new instance of:
```java
com.gigigo.permissions.interfaces.MultiplePermissionsListener()
```

In multiple permission asking the retries no apply, but you have the Event in callback:
```java
onPermissionRationaleShouldBeShown
```


## Resources Literals/Retries
You can override the next resources in your app:
```xml
<string name="continueRequestPermissionAllowedFeedback">All permissions have been allowed</string>
  <string name="continueRequestPermissionSettingsDeniedFeedback">SETTINGS</string>
  <string name="continueRequestPermissionDeniedFeedback">Permissions denied are necessary for doing some tasks</string>
  <string name="continueRequestPermissionRationaleMessage">Permissions denied are necessary for doing some tasks, allow it!</string>
  <string name="continueRequestPermissionRationaleTitle">Permission Request</string>

  <string name="ggg_permission_settings">Settings</string>
  <string name="ggg_permission_literal">Permission</string><!-- this res are using in multilelistenerImplbase for show toast result-->
  <string name="ggg_permission_granted">Granted</string>
  <string name="ggg_permission_denied">Denied</string>

  <string name="ggg_permission_denied_location">Denied location device</string>
  <string name="ggg_permission_rationale_title_location">Location Permissions</string>
  <string name="ggg_permission_rationale_message_location">App needs the device location</string>
  <integer name="ggg_permission_retries_location">3</integer>

  <string name="ggg_permission_denied_camera">Denied camera device</string>
  <string name="ggg_permission_rationale_title_camera">Camera Permissions</string>
  <string name="ggg_permission_rationale_message_camera">App needs the device camera functionality</string>
  <integer name="ggg_permission_retries_camera">3</integer>

  <string name="ggg_permission_denied_phone">Denied phone device</string>
  <string name="ggg_permission_rationale_title_phone">Phone Permissions</string>
  <string name="ggg_permission_rationale_message_phone">App needs phone permission</string>
  <integer name="ggg_permission_retries_phone">3</integer>

  <string name="ggg_permission_denied_storage">Denied external storage device</string>
  <string name="ggg_permission_rationale_title_storage">External Storage Permissions</string>
  <string name="ggg_permission_rationale_message_storage">App needs external storage permission</string>
  <integer name="ggg_permission_retries_storage">3</integer>

  <string name="ggg_permission_denied_contacts">Denied contacts permission</string>
  <string name="ggg_permission_rationale_title_contacts">Contacts Permissions</string>
  <string name="ggg_permission_rationale_message_contacts">App needs to access to your contacts</string>
  <integer name="ggg_permission_retries_contacts">3</integer>

  <string name="ggg_permission_denied_microphone">Denied microphone permission</string>
  <string name="ggg_permission_rationale_title_microphone">Microphone Permissions</string>
  <string name="ggg_permission_rationale_message_microphone">App needs to access to your device microphone</string>
  <integer name="ggg_permission_retries_microphone">3</integer>

  <string name="ggg_permission_denied_sms">Denied sms permission</string>
  <string name="ggg_permission_rationale_title_sms">Sms Permissions</string>
  <string name="ggg_permission_rationale_message_sms">App needs to access to your SMS</string>
  <integer name="ggg_permission_retries_sms">3</integer>

  <string name="ggg_permission_denied_calendar">Denied calendar permission</string>
  <string name="ggg_permission_rationale_title_calendar">Calendar Permissions</string>
  <string name="ggg_permission_rationale_message_calendar">App needs to access to your calendar</string>
  <integer name="ggg_permission_retries_calendar">3</integer>

  <string name="ggg_permission_denied_sensors">Denied sensors permission</string>
  <string name="ggg_permission_rationale_title_sensors">Sensors Permissions</string>
  <string name="ggg_permission_rationale_message_sensors">App needs to access to your device sensors</string>
  <integer name="ggg_permission_retries_sensors">3</integer>
```



That's all!!



