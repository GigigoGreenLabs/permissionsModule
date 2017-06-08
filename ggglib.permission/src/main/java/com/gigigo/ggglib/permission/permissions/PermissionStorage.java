package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupStorage;

public class PermissionStorage implements Permission {

  private final PermissionGroupStorage permissionGroupStorage;

  public PermissionStorage(PermissionGroupStorage permissionGroupStorage) {
    this.permissionGroupStorage = permissionGroupStorage;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupStorage.getPermission();
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
    return R.integer.ggg_permission_retries_storage;
  }
}
