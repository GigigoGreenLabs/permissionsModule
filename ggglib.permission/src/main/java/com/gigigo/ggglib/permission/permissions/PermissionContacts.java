package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupContacts;

public class PermissionContacts implements Permission {

  private final PermissionGroupContacts permissionGroupContacts;

  public PermissionContacts(PermissionGroupContacts permissionGroupContacts) {
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
    return R.integer.ggg_permission_retries_contacts;
  }
}
