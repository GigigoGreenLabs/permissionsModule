package com.gigigo.permissions.groups;

import android.Manifest;

/**
 * Created by nubor on 23/02/2017.
 */
  public enum PermissionGroupCalendar {
  READ_CALENDAR(Manifest.permission.READ_CALENDAR),
  WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR);

    private final String permission;

  PermissionGroupCalendar(String permission) {
      this.permission = permission;
    }

    public String getPermission() {
      return permission;
    }
}
