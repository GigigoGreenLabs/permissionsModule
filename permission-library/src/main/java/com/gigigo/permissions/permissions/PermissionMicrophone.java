package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupMicrophone;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionMicrophone implements Permission {

  private final Context context;
  private final PermissionGroupMicrophone permissionGroupMicrophone;

  public PermissionMicrophone(Context context, PermissionGroupMicrophone permissionGroupMicrophone) {
    this.context = context;
    this.permissionGroupMicrophone = permissionGroupMicrophone;
  }

  @Override
  public String getAndroidPermissionStringType() {
    return permissionGroupMicrophone.getPermission();
  }

  @Override
  public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override
  public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_contacts;
  }

  @Override
  public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_contacts;
  }

  @Override
  public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_contacts;
  }

  @Override
  public int getNumRetry() {
    return context.getResources().getInteger(R.integer.ggg_permission_retries_contacts);
  }
}
