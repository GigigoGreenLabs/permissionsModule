package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupCamera;

public class PermissionCamera implements Permission {

  private final PermissionGroupCamera permissionGroupCamera;

  public PermissionCamera(PermissionGroupCamera permissionGroupCamera) {
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
    return R.integer.ggg_permission_retries_camera;
  }
}
