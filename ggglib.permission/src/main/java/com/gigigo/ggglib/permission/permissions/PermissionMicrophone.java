package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupMicrophone;

public class PermissionMicrophone implements Permission {

  private final PermissionGroupMicrophone permissionGroupMicrophone;

  public PermissionMicrophone(PermissionGroupMicrophone permissionGroupMicrophone) {
    this.permissionGroupMicrophone = permissionGroupMicrophone;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupMicrophone.getPermission();
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_microphone;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_microphone;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_microphone;
  }

  @Override public int getNumRetry() {
    return R.integer.ggg_permission_retries_microphone;
  }
}
