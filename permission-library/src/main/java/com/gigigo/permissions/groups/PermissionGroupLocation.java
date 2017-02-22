package com.gigigo.permissions.groups;

import android.Manifest;

public enum PermissionGroupLocation {
  ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
  ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION);

  private final String permission;

  PermissionGroupLocation(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
