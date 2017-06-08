package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupSms;

/**
 * Created by nubor on 23/02/2017.
 */
public class PermissionSms implements Permission {

  private final PermissionGroupSms permissionGroupSms;

  public PermissionSms(PermissionGroupSms permissionGroupSms) {
    this.permissionGroupSms = permissionGroupSms;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupSms.getPermission();
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_sms;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_sms;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_sms;
  }

  @Override public int getNumRetry() {
    return R.integer.ggg_permission_retries_sms;
  }
}
