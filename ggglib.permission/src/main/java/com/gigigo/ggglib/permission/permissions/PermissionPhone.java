package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupPhone;

public class PermissionPhone implements Permission {

  private final PermissionGroupPhone permissionGroupPhone;

  public PermissionPhone(PermissionGroupPhone permissionGroupPhone) {
    this.permissionGroupPhone = permissionGroupPhone;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupPhone.getPermission();
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_phone;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_phone;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_phone;
  }

  @Override public int getNumRetry() {
    return R.integer.ggg_permission_retries_phone;
  }
}
