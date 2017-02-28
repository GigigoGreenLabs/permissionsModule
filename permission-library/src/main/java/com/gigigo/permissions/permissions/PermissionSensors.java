package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupPhone;
import com.gigigo.permissions.groups.PermissionGroupSensors;
import com.gigigo.permissions.interfaces.Permission;

/**
 * Created by nubor on 23/02/2017.
 */

public class PermissionSensors implements Permission {

  private final Context context;
  private final PermissionGroupSensors permissionGroupSensors;

  public PermissionSensors(Context context, PermissionGroupSensors permissionGroupSensors) {
    this.context = context;
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
    return context.getResources().getInteger(R.integer.ggg_permission_retries_sensors);
  }
}
