package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupSensors;

/**
 * Created by nubor on 23/02/2017.
 */

public class PermissionSensors implements Permission {

  private final PermissionGroupSensors permissionGroupSensors;

  public PermissionSensors(PermissionGroupSensors permissionGroupSensors) {
    this.permissionGroupSensors = permissionGroupSensors;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupSensors.getPermission();
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_sensors;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_sensors;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_sensors;
  }

  @Override public int getNumRetry() {
    return R.integer.ggg_permission_retries_sensors;
  }
}
