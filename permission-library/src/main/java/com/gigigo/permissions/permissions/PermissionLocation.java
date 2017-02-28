package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupCamera;
import com.gigigo.permissions.groups.PermissionGroupLocation;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionLocation implements Permission {

  private final Context context;
  private final PermissionGroupLocation permissionGroupLocation;

  public PermissionLocation(Context context,PermissionGroupLocation permissionGroupLocation) {
    this.context = context;
    this.permissionGroupLocation=permissionGroupLocation;
  }

  @Override
  public String getAndroidPermissionStringType() {
    return permissionGroupLocation.getPermission();
  }

  @Override
  public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override
  public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_location;
  }

  @Override
  public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_location;
  }

  @Override
  public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_location;
  }

  @Override
  public int getNumRetry() {
    return context.getResources().getInteger(R.integer.ggg_permission_retries_location);
  }
}
