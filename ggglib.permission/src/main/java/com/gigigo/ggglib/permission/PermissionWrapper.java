package com.gigigo.ggglib.permission;

import android.app.Application;
import com.gigigo.ggglib.permission.listeners.UserPermissionRequestResponseListener;
import com.gigigo.ggglib.permission.permissions.Permission;
import com.karumi.dexterox.PermissionManager;
import com.karumi.dexterox.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nubor on 13/06/2017.
 */

public class PermissionWrapper {
  PermissionManager mPermissionManager;
  PermissionCheckerImpl mPermissionChecker;
  static PermissionModuleLifeCycle mPermissionModuleLifeCycle;

  public PermissionWrapper(Application app) {
    mPermissionModuleLifeCycle = new PermissionModuleLifeCycle();
    app.registerActivityLifecycleCallbacks(mPermissionModuleLifeCycle);
    //mPermissionManager= new PermissionManager();
    //PermissionManager.initialize(app.getApplicationContext());
    //mPermissionManager=PermissionManager.

    //mPermissionChecker= new PermissionCheckerImpl()

  }

  public boolean isGranted(Permission permission) {

    return false;
  }

  public boolean isAllGranted(Permission... permission) {

    return false;
  }

  public void askForPermission(UserPermissionRequestResponseListener userResponse,
      Permission permission) {

    //todo isRequestOngoing ?¿ wtf must dissapear
    if (PermissionManager.isRequestOngoing()) {
      return;
    }

    if (mPermissionModuleLifeCycle.isActivityContextAvailable()) {
      PermissionCheckerImpl perchecker=new PermissionCheckerImpl(mPermissionModuleLifeCycle.getCurrentActivity());
      perchecker.askForPermission(userResponse,permission);
      //PermissionManager.checkPermission(new GenericPermissionListenerImpl(permission, userResponse,
      //        mPermissionModuleLifeCycle.getCurrentActivity()),
      //    permission.getAndroidPermissionStringType());
    }
    else
    {
      //todo keep permission and listener in static public
      //open de transparentactivity ask for this permission

    }
  }
//region multiplepermissions NO WORK YET
  public void askForPermissions(MultiplePermissionsListener permissionsListener,
      Permission... permissions) {


    //todo isRequestOngoing ?¿ wtf must dissapear
    if (PermissionManager.isRequestOngoing()) {
      return;
    }

    CompositeMultiplePermissionsListener compositeMultiplePermissionsListener =
        new CompositeMultiplePermissionsListener(permissionsListener);

    Collection<String> permissionList = retrievePermissionNames(permissions);

    PermissionManager.checkPermissions(compositeMultiplePermissionsListener, permissionList);
   /* 1º
    checkPermissions(permissionsListener, permissions, ThreadFactory.makeMainThread());
    checkMultiplePermissions(listener, permissions, thread);
    */
  }

  private void checkMultiplePermissions(MultiplePermissionsListener listener,
      Collection<String> permissions, Thread thread) {
    /*
    checkNoDexterRequestOngoing();
    checkRequestSomePermission(permissions);

    pendingPermissions.clear();
    pendingPermissions.addAll(permissions);
    multiplePermissionsReport.clear();
    this.listener = new MultiplePermissionListenerThreadDecorator(listener, thread);
    if (!mPermissionModuleLifeCycle.isActivityContextAvailable()) {
      startTransparentActivityIfNeeded();
    }
    thread.loop();
    */
  }

  private Collection<String> retrievePermissionNames(Permission[] permissions) {
    Collection<String> permissionList = new ArrayList<>();
    for (Permission permission : permissions) {
      permissionList.add(permission.getAndroidPermissionStringType());
    }
    return permissionList;
  }
  //endregion
}
