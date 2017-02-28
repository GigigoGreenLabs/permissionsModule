package com.gigigo.permissions.groups;

import android.Manifest;

/**
 * Created by nubor on 23/02/2017.
 */
public enum PermissionGroupSensors {
  BODY_SENSORS(Manifest.permission.BODY_SENSORS);

    private final String permission;

  PermissionGroupSensors(String permission) {
      this.permission = permission;
    }

    public String getPermission() {
      return permission;
    }
  }


