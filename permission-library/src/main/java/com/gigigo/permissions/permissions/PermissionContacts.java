package com.gigigo.permissions.permissions;

import android.Manifest;
import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupContacts;
import com.gigigo.permissions.interfaces.Permission;

public class PermissionContacts implements Permission {

  private final Context context;
  private final PermissionGroupContacts permissionGroupContacts;

  public PermissionContacts(Context context, PermissionGroupContacts permissionGroupContacts) {
    this.context = context;
    this.permissionGroupContacts = permissionGroupContacts;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupContacts.getPermission();
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_contacts;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_contacts;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_contacts;
  }

  @Override public int getNumRetry() {
    return context.getResources().getInteger(R.integer.ggg_permission_retries_contacts);
  }
}
