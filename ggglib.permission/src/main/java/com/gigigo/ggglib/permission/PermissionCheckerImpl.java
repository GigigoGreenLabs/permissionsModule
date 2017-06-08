/*
 * Created by Gigigo Android Team
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
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
import android.content.pm.PackageManager;
import android.support.v4ox.content.ContextCompat;
import com.gigigo.ggglib.permission.exceptions.NullContainerException;
import com.gigigo.ggglib.permission.listeners.ContinueRequestPermissionListenerImpl;
import com.gigigo.ggglib.permission.listeners.GenericPermissionListenerImpl;
import com.gigigo.ggglib.permission.listeners.UserPermissionRequestResponseListener;
import com.gigigo.ggglib.permission.permissions.Permission;
import com.karumi.dexterox.PermissionActivity;
import com.karumi.dexterox.PermissionManager;
import com.karumi.dexterox.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;
import com.karumi.dexterox.listener.single.PermissionListener;
import java.util.ArrayList;
import java.util.Collection;

public class PermissionCheckerImpl implements PermissionChecker {

  private final Activity activity;

  public PermissionCheckerImpl(Activity activity) {
    PermissionManager.initialize(activity);
    this.activity = activity;
  }

  public PermissionCheckerImpl(PermissionActivity activity) {
    PermissionManager.initialize(activity);
    this.activity = activity;
  }

  @Override public void askForPermission(UserPermissionRequestResponseListener userResponse,
      Permission permission) {
    if (PermissionManager.isRequestOngoing()) {
      return;
    }
    PermissionManager.checkPermission(
        new GenericPermissionListenerImpl(permission, userResponse, this.activity),
        permission.getAndroidPermissionStringType());
  }

  @Override public void askForPermissions(MultiplePermissionsListener permissionsListener,
      Permission... permissions) {
    if (PermissionManager.isRequestOngoing()) {
      return;
    }

    CompositeMultiplePermissionsListener compositeMultiplePermissionsListener =
        new CompositeMultiplePermissionsListener(permissionsListener);

    Collection<String> permissionList = retrievePermissionNames(permissions);

    PermissionManager.checkPermissions(compositeMultiplePermissionsListener, permissionList);
  }

  @Override public void continuePendingPermissionsRequestsIfPossible() {
    PermissionManager.continuePendingRequestIfPossible(
        new ContinueRequestPermissionListenerImpl(activity));
  }

  @Override public boolean isGranted(Permission permission) {
    int permissionGranted =
        ContextCompat.checkSelfPermission(activity, permission.getAndroidPermissionStringType());
    return PackageManager.PERMISSION_GRANTED == permissionGranted;
  }

  @Override public boolean isAllGranted(Permission... permissions) {

    for (int i = 0; i < permissions.length - 1; i++) {
      Permission permission = permissions[i];
      int permissionGranted =
          ContextCompat.checkSelfPermission(activity, permission.getAndroidPermissionStringType());
      boolean isPermissionGranted = PackageManager.PERMISSION_GRANTED == permissionGranted;

      if (!isPermissionGranted) return false;
    }

    return true;
  }

  private PermissionListener[] createListeners(Permission permission,
      UserPermissionRequestResponseListener userResponse) {

    PermissionListener basicListener = getPermissionListenerImpl(permission, userResponse);
    try {
      return new PermissionListener[] { basicListener };
    } catch (NullContainerException n) {
      return new PermissionListener[] { basicListener };
    }
  }

  private PermissionListener getPermissionListenerImpl(final Permission permission,
      final UserPermissionRequestResponseListener userPermissionRequestResponseListener) {

    return new GenericPermissionListenerImpl(permission, userPermissionRequestResponseListener,
        activity);
  }

  private Collection<String> retrievePermissionNames(Permission[] permissions) {
    Collection<String> permissionList = new ArrayList<>();
    for (Permission permission : permissions) {
      permissionList.add(permission.getAndroidPermissionStringType());
    }
    return permissionList;
  }
}
