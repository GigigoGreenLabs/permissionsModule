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

package com.gigigo.ggglib.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import com.gigigo.ggglib.permission.listeners.UserPermissionRequestResponseListener;
import java.util.Collection;
import java.util.LinkedList;

public class PermissionActivity extends Activity {

  public static final String IS_TRANSPARENT_ACTIVITY = "IS_TRANSPARENT_ACTIVITY";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PermissionManager.onActivityReady(this);

    if (getIntent() != null) {
      Bundle extras = getIntent().getExtras();

      if (extras != null && extras.containsKey(IS_TRANSPARENT_ACTIVITY) && extras.getBoolean(
          IS_TRANSPARENT_ACTIVITY)) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
      }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setExitTransition(null);
      getWindow().setEnterTransition(null);
      overridePendingTransition(0, 0);
    }

    if (PermissionWrapper.mPermission != null
        && PermissionWrapper.mUserPermissionRequestResponseListener != null) {
      PermissionWrapper.mPermissionChecker.setActivity(this);
      PermissionWrapper.mPermissionChecker.askForPermission(
          new UserPermissionRequestResponseListener() {
            @Override
            public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
              PermissionWrapper.mUserPermissionRequestResponseListener.onPermissionAllowed(
                  permissionAllowed, numberDoneRetries);
              PermissionWrapper.mUserPermissionRequestResponseListener = null;
              PermissionWrapper.mPermission = null;
              PermissionActivity.this.finish();
            }
          }

          , PermissionWrapper.mPermission);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    PermissionManager.mPermissionActivity = null;
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    PermissionManager.onActivityReady(this);
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    Collection<String> grantedPermissions = new LinkedList<>();
    Collection<String> deniedPermissions = new LinkedList<>();

    for (int i = 0; i < permissions.length; i++) {
      String permission = permissions[i];
      if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
        deniedPermissions.add(permission);
      } else {
        grantedPermissions.add(permission);
      }
    }

    PermissionManager.onPermissionsRequested(grantedPermissions, deniedPermissions);
  }
}
