package com.gigigo.permissions.permissions;

import android.Manifest;
import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupCamera;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionCamera implements Permission {

  private final Context context;
  private final PermissionGroupCamera permissionGroupCamera;

  public PermissionCamera(Context context, PermissionGroupCamera permissionGroupCamera) {
    this.context = context;
    this.permissionGroupCamera = permissionGroupCamera;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupCamera.getPermission();
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_camera;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_camera;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_camera;
  }

  @Override public int getNumRetry() {
    return context.getResources().getInteger(R.integer.ggg_permission_retries_camera);
  }
}
