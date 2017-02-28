package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupStorage;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionStorage implements Permission {

  private final Context context;
  private final PermissionGroupStorage permissionGroupStorage;

  public PermissionStorage(Context context, PermissionGroupStorage permissionGroupStorage) {
    this.context = context;
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
    return context.getResources().getInteger(R.integer.ggg_permission_retries_storage);
  }
}
