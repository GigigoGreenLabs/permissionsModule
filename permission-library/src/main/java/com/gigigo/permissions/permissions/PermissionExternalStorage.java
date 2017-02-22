package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupExternalStorage;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionExternalStorage implements Permission {

  private final Context context;
  private final PermissionGroupExternalStorage permissionGroupExternalStorage;

  public PermissionExternalStorage(Context context, PermissionGroupExternalStorage permissionGroupExternalStorage) {
    this.context = context;
    this.permissionGroupExternalStorage = permissionGroupExternalStorage;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupExternalStorage.getPermission();
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
