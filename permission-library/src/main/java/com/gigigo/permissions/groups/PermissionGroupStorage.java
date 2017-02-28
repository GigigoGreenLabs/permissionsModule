package com.gigigo.permissions.groups;

import android.Manifest;

public enum PermissionGroupStorage {
  READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),
  WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE);

  private final String permission;

  PermissionGroupStorage(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
