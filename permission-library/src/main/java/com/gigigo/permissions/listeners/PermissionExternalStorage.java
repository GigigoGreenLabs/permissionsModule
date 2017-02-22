package com.gigigo.permissions.listeners;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionExternalStorage implements Permission {

  private final Context context;

  public PermissionExternalStorage(Context context) {
    this.context = context;
  }

  @Override public String getAndroidPermissionStringType() {
    return "Manifest.permission.WRITE_EXTERNAL_STORAGE";
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_denied_storage;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_storage;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_storage;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_storage;
  }

  @Override public int getNumRetry() {
    return context.getResources().getInteger(R.integer.ggg_permission_retries_storage);
  }
}
