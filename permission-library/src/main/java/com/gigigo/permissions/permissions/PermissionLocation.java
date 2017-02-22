package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionLocation implements Permission {

  private final Context context;

  public PermissionLocation(Context context) {
    this.context = context;
  }

  @Override
  public String getAndroidPermissionStringType() {
    return "android.permission.ACCESS_FINE_LOCATION";
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
