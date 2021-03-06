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

package com.karumi.dexterox;

import android.app.Activity;
import android.content.Context;

import com.gigigo.permissions.interfaces.Permission;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;
import com.karumi.dexterox.listener.single.PermissionListener;
import java.util.Arrays;
import java.util.Collection;

/**
 * Class to simplify the management of Android runtime permissions
 * PermissionManager needs to be initialized before checking for a permission using {@link
 * #initialize(Context)}
 */
public final class PermissionManager {

  private static PermissionManagerInstance instance;

  public static Activity mPermissionActivity;

  public static void closeActivity() {
    if (mPermissionActivity != null) {
      if(mPermissionActivity.getPackageName().equals("com.karumi.dexterox")) //only close the transparent activity, not if userextend the permissionActivity
         mPermissionActivity.finish();
    }
  }

  /**
   * Initializes the library
   *
   * @param context Context used by PermissionManager. Use your {@link android.app.Application} to
   * make sure
   * the instance is not cleaned up during your app lifetime
   */
  public static void initialize(Context context) {
    if (instance == null) {
      AndroidPermissionService androidPermissionService = new AndroidPermissionService();
      IntentProvider intentProvider = new IntentProvider();
      instance = new PermissionManagerInstance(context, androidPermissionService, intentProvider);
    }
  }

  public static void initialize(PermissionActivity permissionActivity) {
    if (instance == null) {
      AndroidPermissionService androidPermissionService = new AndroidPermissionService();
      IntentProvider intentProvider = new IntentProvider();
      instance = new PermissionManagerInstance(permissionActivity.getApplicationContext(), androidPermissionService, intentProvider);
      instance.attachMyOwnPermissionActivity(permissionActivity);
    }
  }

  /**
   * Checks the permission and notifies the listener of its state.
   * It is important to note that permissions still have to be declared in the manifest.
   * Calling this method will result in an exception if {@link #isRequestOngoing()} returns true.
   * All listener methods are called on the same thread that fired the permission request.
   *
   * @param listener The class that will be reported when the state of the permission is ready
   * @param permission One of the values found in {@link android.Manifest.permission}
   */
  public static void checkPermissionOnSameThread(PermissionListener listener, String permission) {
    checkInstanceNotNull();
    instance.checkPermission(listener, permission, ThreadFactory.makeSameThread());
  }

  /**
   * Checks the permission and notifies the listener of its state.
   * It is important to note that permissions still have to be declared in the manifest.
   * Calling this method will result in an exception if {@link #isRequestOngoing()} returns true.
   * All listener methods are called on the main thread that fired the permission request.
   *
   * @param listener The class that will be reported when the state of the permission is ready
   * @param permission One of the values found in {@link android.Manifest.permission}
   */
  public static void checkPermission(PermissionListener listener, String permission) {
    checkInstanceNotNull();
    instance.checkPermission(listener, permission, ThreadFactory.makeMainThread());
  }

  /**
   * Checks the permissions and notifies the listener of its state.
   * It is important to note that permissions still have to be declared in the manifest.
   * Calling this method will result in an exception if {@link #isRequestOngoing()} returns true.
   * All listener methods are called on the same thread that fired the permission request.
   *
   * @param listener The class that will be reported when the state of the permissions are ready
   * @param permissions Array of values found in {@link android.Manifest.permission}
   */
  public static void checkPermissionsOnSameThread(MultiplePermissionsListener listener,
      String... permissions) {
    checkInstanceNotNull();
    instance.checkPermissions(listener, Arrays.asList(permissions), ThreadFactory.makeSameThread());
  }

  /**
   * Checks the permissions and notifies the listener of its state.
   * It is important to note that permissions still have to be declared in the manifest.
   * Calling this method will result in an exception if {@link #isRequestOngoing()} returns true.
   * All listener methods are called on the main thread that fired the permission request.
   *
   * @param listener The class that will be reported when the state of the permissions are ready
   * @param permissions Array of values found in {@link android.Manifest.permission}
   */
  public static void checkPermissions(MultiplePermissionsListener listener, String... permissions) {
    checkInstanceNotNull();
    instance.checkPermissions(listener, Arrays.asList(permissions), ThreadFactory.makeMainThread());
  }

  /**
   * Checks the permissions and notifies the listener of its state
   * It is important to note that permissions still have to be declared in the manifest
   *
   * @param listener The class that will be reported when the state of the permissions are ready
   * @param permissions Collection of values found in {@link android.Manifest.permission}
   */
  public static void checkPermissions(MultiplePermissionsListener listener,
      Collection<String> permissions) {
    checkInstanceNotNull();
    instance.checkPermissions(listener, permissions, ThreadFactory.makeMainThread());
  }

  /**
   * Checks is there is any permission request still ongoing.
   * If so, state of permissions must not be checked until it is resolved
   * or it will cause an exception.
   */
  public static boolean isRequestOngoing() {
    checkInstanceNotNull();
    return instance.isRequestOngoing();
  }

  /**
   * Requests pending permissions if there were permissions lost. This method can be used to
   * recover the PermissionManager state during a configuration change, for example when the device
   * is
   * rotated.
   */
  public static void continuePendingRequestsIfPossible(MultiplePermissionsListener listener) {
    checkInstanceNotNull();
    instance.continuePendingRequestsIfPossible(listener, ThreadFactory.makeMainThread());
  }

  /**
   * Requests pending permission if there was a permissions lost. This method can be used to
   * recover the PermissionManager state during a configuration change, for example when the device
   * is
   * rotated.
   */
  public static void continuePendingRequestIfPossible(PermissionListener listener) {
    checkInstanceNotNull();
    instance.continuePendingRequestIfPossible(listener, ThreadFactory.makeMainThread());
  }

  private static void checkInstanceNotNull() {
    if (instance == null) {
      throw new NullPointerException(
          "context == null \n Must call \"initialize\" on PermissionManager");
    }
  }

  /**
   * Method called whenever the PermissionActivity has been created or recreated and is ready to be
   * used.
   */
  static void onActivityReady(Activity activity) {
    instance.onActivityReady(activity);
    mPermissionActivity = activity;
  }

  static void attachMyOwnPermissionActivity(PermissionActivity activity) {
    if (activity != null) instance.attachMyOwnPermissionActivity(activity);
  }

  /**
   * Method called when all the permissions has been requested to the user
   *
   * @param grantedPermissions Collection with all the permissions the user has granted. Contains
   * values from {@link android.Manifest.permission}
   * @param deniedPermissions Collection with all the permissions the user has denied. Contains
   * values from {@link android.Manifest.permission}
   */
  static void onPermissionsRequested(Collection<String> grantedPermissions,
      Collection<String> deniedPermissions) {
    instance.onPermissionRequestGranted(grantedPermissions);
    instance.onPermissionRequestDenied(deniedPermissions);
  }
}