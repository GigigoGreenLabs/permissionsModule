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
import com.gigigo.ggglib.permission.MultiplePermissionsReport;
import com.gigigo.ggglib.permission.PermissionToken;
import com.gigigo.ggglib.permission.PermissionWrapper;
import com.gigigo.ggglib.permission.groups.PermissionGroupCamera;
import com.gigigo.ggglib.permission.groups.PermissionGroupLocation;
import com.gigigo.ggglib.permission.listener.PermissionRequest;
import com.gigigo.ggglib.permission.listener.multi.MultiplePermissionsListener;
import com.gigigo.ggglib.permission.listeners.UserPermissionRequestResponseListener;
import com.gigigo.ggglib.permission.permissions.PermissionCamera;
import com.gigigo.ggglib.permission.permissions.PermissionLocation;
import java.util.List;

/**
 * Sample application that initializes the PermissionManager library.
 */
public class SampleApplication extends Application {
  static PermissionWrapper mPermissionWrapper;

  @Override public void onCreate() {
    super.onCreate();
    mPermissionWrapper = new PermissionWrapper(this);//todo instance? initialize instead constructor?

    //asv estas peticiones de permisos, en ppio no funcionarian, xo funciona la de single =_0,

    checkPermission();
   //checkALLPermission();
  }

  private void checkPermission() {
    PermissionCamera permissionCamera = new PermissionCamera(PermissionGroupCamera.CAMERA);

    boolean isGranted = mPermissionWrapper.isGranted(permissionCamera);
    if (!isGranted) {
      mPermissionWrapper.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          int i = 1;
          System.out.println("Permiso concecido");
        }
      }, permissionCamera);
    }
  }

  private void checkALLPermission () {
    PermissionCamera permissionCamera = new PermissionCamera(PermissionGroupCamera.CAMERA);
    PermissionLocation permissionLocation = new PermissionLocation(PermissionGroupLocation.ACCESS_FINE_LOCATION);

    boolean isGranted = mPermissionWrapper.isAllGranted(permissionCamera,permissionLocation);
    if (!isGranted) {
      mPermissionWrapper.askForPermissions(new MultiplePermissionsListener() {
        @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
            PermissionToken token) {

        }
      });
    }
  }
}
