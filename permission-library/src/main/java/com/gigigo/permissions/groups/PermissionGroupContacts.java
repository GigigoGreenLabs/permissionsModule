package com.gigigo.permissions.groups;

import android.Manifest;

public enum PermissionGroupContacts {
  READ_CONTACTS(Manifest.permission.READ_CONTACTS),
  WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
  GET_CONTACTS(Manifest.permission.GET_ACCOUNTS);

  private final String permission;

  PermissionGroupContacts(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
