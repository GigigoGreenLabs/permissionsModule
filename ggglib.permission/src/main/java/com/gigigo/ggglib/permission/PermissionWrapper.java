package com.gigigo.ggglib.permission;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import com.gigigo.ggglib.permission.listener.multi.MultiplePermissionsListener;
import com.gigigo.ggglib.permission.listeners.UserPermissionRequestResponseListener;
import com.gigigo.ggglib.permission.permissions.Permission;

/**
 * Created by nubor on 13/06/2017.
 */

public class PermissionWrapper {

  static PermissionCheckerImpl mPermissionChecker;
  static PermissionModuleLifeCycle mPermissionModuleLifeCycle;
  Application mApp;

  public PermissionWrapper(Application app) {
    mPermissionModuleLifeCycle = new PermissionModuleLifeCycle();

    app.registerActivityLifecycleCallbacks(mPermissionModuleLifeCycle);

    PermissionManager.initialize(app.getApplicationContext());
    mPermissionChecker = new PermissionCheckerImpl(mPermissionModuleLifeCycle.getCurrentActivity());
    mApp = app;
  }

  public boolean isGranted(Permission permission) {
    if (mPermissionModuleLifeCycle.isActivityContextAvailable()) {
      mPermissionChecker.setActivity(mPermissionModuleLifeCycle.getCurrentActivity());
      return mPermissionChecker.isGranted(permission);
    }
    return false;
  }

  public boolean isAllGranted(Permission... permissions) {

    if (mPermissionModuleLifeCycle.isActivityContextAvailable()) {
      mPermissionChecker.setActivity(mPermissionModuleLifeCycle.getCurrentActivity());
      return mPermissionChecker.isAllGranted(permissions);
    }
    return false;
  }

  public  void askForPermission(UserPermissionRequestResponseListener userResponse,
      Permission permission) {
    if (PermissionManager.isRequestOngoing()) {
      return;
    }
    if (mPermissionModuleLifeCycle.isActivityContextAvailable()) {
      mPermissionChecker.setActivity(mPermissionModuleLifeCycle.getCurrentActivity());
      mPermissionChecker.askForPermission(userResponse, permission);
    } else {
      //todo  transladar esta vaina al permissionchecker
      mUserPermissionRequestResponseListener = userResponse;
      mPermission = permission;
      startTransparentActivityIfNeeded();
    }
  }

  static UserPermissionRequestResponseListener mUserPermissionRequestResponseListener;
  static Permission mPermission;

  private void startTransparentActivityIfNeeded() {
    Intent intent =
        new IntentProvider().get(mApp.getApplicationContext(), PermissionActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    intent.putExtra(PermissionActivity.IS_TRANSPARENT_ACTIVITY, true);
    mApp.getApplicationContext().startActivity(intent);
  }

  //region multiplepermissions NO WORK YET
  public void askForPermissions(MultiplePermissionsListener permissionsListener,
      Permission... permissions) {
    if (PermissionManager.isRequestOngoing()) {
      return;
    }
    if (mPermissionModuleLifeCycle.isActivityContextAvailable()) {
      mPermissionChecker.setActivity(mPermissionModuleLifeCycle.getCurrentActivity());
      mPermissionChecker.askForPermissions(permissionsListener, permissions);
    }
   /*  CompositeMultiplePermissionsListener compositeMultiplePermissionsListener =
        new CompositeMultiplePermissionsListener(permissionsListener);

    Collection<String> permissionList = retrievePermissionNames(permissions);

    PermissionManager.checkPermissions(compositeMultiplePermissionsListener, permissionList);
   1º
    checkPermissions(permissionsListener, permissions, ThreadFactory.makeMainThread());
    checkMultiplePermissions(listener, permissions, thread);
    */
  }
  //endregion

  public void attachMyOwnPermissionActivity(Activity activity) {
    //nothing todo
    // PermissionManager.attachMyOwnPermissionActivity(activity);
  }
}
