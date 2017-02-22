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

package com.gigigo.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4ox.content.ContextCompat;
import com.gigigo.permissions.exceptions.NullContainerException;
import com.gigigo.permissions.interfaces.Permission;
import com.gigigo.permissions.interfaces.PermissionChecker;
import com.gigigo.permissions.interfaces.UserPermissionRequestResponseListener;
import com.gigigo.permissions.listeners.ContinueRequestPermissionListenerImpl;
import com.gigigo.permissions.listeners.GenericPermissionListenerImpl;
import com.karumi.dexterox.Dexter;
import com.karumi.dexterox.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;
import com.karumi.dexterox.listener.single.CompositePermissionListener;
import com.karumi.dexterox.listener.single.PermissionListener;
import java.util.ArrayList;
import java.util.Collection;

public class AndroidPermissionCheckerImpl implements PermissionChecker {

  private final Activity activity;

  public AndroidPermissionCheckerImpl(Activity activity) {
    Dexter.initialize(activity);
    this.activity = activity;
  }

  @Override
  public void askForPermission(Permission permission, UserPermissionRequestResponseListener userResponse) {
    if (Dexter.isRequestOngoing()) {
      return;
    }

    PermissionListener[] listeners = createListeners(permission, userResponse);

    Dexter.checkPermission(new CompositePermissionListener(listeners),
        permission.getAndroidPermissionStringType());
  }

  public void askForPermissions(MultiplePermissionsListener permissionsListener, Permission... permissions) {
    if (Dexter.isRequestOngoing()) {
      return;
    }

    CompositeMultiplePermissionsListener compositeMultiplePermissionsListener = new CompositeMultiplePermissionsListener(permissionsListener);

    Collection<String> permissionList = retrievePermissionNames(permissions);

    Dexter.checkPermissions(compositeMultiplePermissionsListener, permissionList);
  }

  @Override public void continuePendingPermissionsRequestsIfPossible() {
    Dexter.continuePendingRequestIfPossible(
        new ContinueRequestPermissionListenerImpl(activity));
  }

  @Override public boolean isGranted(Permission permission) {
    int permissionGranted =
        ContextCompat.checkSelfPermission(activity, permission.getAndroidPermissionStringType());
    return PackageManager.PERMISSION_GRANTED == permissionGranted;
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
