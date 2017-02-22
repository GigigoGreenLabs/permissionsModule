package com.gigigo.permissions.groups;

import android.Manifest;

public enum PermissionGroupMicrophone {
  RECORD_AUDIO(Manifest.permission.RECORD_AUDIO);

  private final String permission;

  PermissionGroupMicrophone(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
