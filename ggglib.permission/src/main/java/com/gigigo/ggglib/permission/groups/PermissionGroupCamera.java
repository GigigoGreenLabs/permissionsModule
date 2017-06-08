package com.gigigo.ggglib.permission.groups;

import android.Manifest;

public enum PermissionGroupCamera {
  CAMERA(Manifest.permission.CAMERA);

  private final String permission;

  PermissionGroupCamera(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
