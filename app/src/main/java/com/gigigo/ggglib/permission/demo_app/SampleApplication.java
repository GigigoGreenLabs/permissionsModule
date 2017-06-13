/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.ggglib.permission.demo_app;

import android.app.Application;
import android.widget.Toast;
import com.gigigo.ggglib.permission.PermissionWrapper;
import com.gigigo.ggglib.permission.groups.PermissionGroupCamera;
import com.gigigo.ggglib.permission.listeners.UserPermissionRequestResponseListener;
import com.gigigo.ggglib.permission.permissions.PermissionCamera;
import com.karumi.dexterox.PermissionManager;

/**
 * Sample application that initializes the PermissionManager library.
 */
public class SampleApplication extends Application {
 static PermissionWrapper permissionWrapper  ;
  @Override public void onCreate() {
    super.onCreate();
    PermissionManager.initialize(this);

    checkPermissions();
    //region checkpermission NEW
    permissionWrapper = new PermissionWrapper(this);//todo instance? initialize instead constructor?
    checkPermissionNEW();
    //checkALLPermissionNEW();
    //endregion


  }



  private void checkPermissions() {
/* OLD PermissionChecker is public only in package permissions
    PermissionChecker permissionChecker = new PermissionCheckerImpl();

    PermissionCamera permissionCamera = new PermissionCamera(PermissionGroupCamera.CAMERA);

    boolean isGranted = permissionChecker.isGranted(permissionCamera);
    if (!isGranted) {
      permissionChecker.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          int i = 1;
        }
      }, permissionCamera);
    }*/
  }




  private void checkPermissionNEW() {

    PermissionCamera permissionCamera = new PermissionCamera(PermissionGroupCamera.CAMERA);

    boolean isGranted = permissionWrapper.isGranted(permissionCamera);
    if (!isGranted) {
      permissionWrapper.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          int i = 1;
          Toast.makeText(SampleApplication.this,"Permiso concecido",Toast.LENGTH_LONG).show();
        }
      }, permissionCamera);
    }
  }


  private void checkALLPermissionNEW() {
   /* PermissionCamera permissionCamera = new PermissionCamera(PermissionGroupCamera.CAMERA);
    PermissionLocation permissionLocation = new PermissionLocation(PermissionGroupLocation.ACCESS_FINE_LOCATION);

    boolean isGranted = PermissionManager.isGranted(permissionCamera);
    if (!isGranted) {
      PermissionManager.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          int i = 1;
        }
      }, permissionCamera);
    }*/
  }

}
