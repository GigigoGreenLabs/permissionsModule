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
import com.gigigo.ggglib.device.providers.ContextProvider;
import com.gigigo.ggglib.permission.PermissionChecker;
import com.gigigo.ggglib.permission.PermissionCheckerImpl;
import com.gigigo.ggglib.permission.groups.PermissionGroupCamera;
import com.gigigo.ggglib.permission.listeners.UserPermissionRequestResponseListener;
import com.gigigo.ggglib.permission.permissions.PermissionCamera;
import com.karumi.dexterox.PermissionManager;

/**
 * Sample application that initializes the PermissionManager library.
 */
public class SampleApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    PermissionManager.initialize(this);

    checkPermissions();
  }

  private void checkPermissions() {

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
    }
  }
}
