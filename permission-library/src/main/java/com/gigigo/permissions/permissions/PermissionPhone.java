package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupPhone;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionPhone implements Permission {

  private final Context context;
  private final PermissionGroupPhone permissionGroupPhone;

  public PermissionPhone(Context context, PermissionGroupPhone permissionGroupPhone) {
    this.context = context;
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
    return context.getResources().getInteger(R.integer.ggg_permission_retries_phone);
  }
}
