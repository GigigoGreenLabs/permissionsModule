package com.gigigo.permissions.listeners;

import android.Manifest;
import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionCamera implements Permission {

  private final Context context;

  public PermissionCamera(Context context) {
    this.context = context;
  }

  @Override public String getAndroidPermissionStringType() {
    return Manifest.permission.CAMERA;
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_camera;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_camera;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_camera;
  }

  @Override public int getNumRetry() {
    return context.getResources().getInteger(R.integer.ggg_permission_retries_camera);
  }
}
