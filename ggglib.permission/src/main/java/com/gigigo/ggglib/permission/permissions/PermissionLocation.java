package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupLocation;

public class PermissionLocation implements Permission {

  private final PermissionGroupLocation permissionGroupLocation;

  public PermissionLocation(PermissionGroupLocation permissionGroupLocation) {
    this.permissionGroupLocation = permissionGroupLocation;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupLocation.getPermission();
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_location;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_location;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_location;
  }

  @Override public int getNumRetry() {
    return R.integer.ggg_permission_retries_location;
  }
}
