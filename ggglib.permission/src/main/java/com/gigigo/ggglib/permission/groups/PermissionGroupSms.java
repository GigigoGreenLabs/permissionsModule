package com.gigigo.ggglib.permission.groups;

import android.Manifest;

/**
 * Created by nubor on 23/02/2017.
 */
public enum PermissionGroupSms {
  SEND_SMS(Manifest.permission.SEND_SMS),
  RECEIVE_SMS(Manifest.permission.RECEIVE_SMS),
  READ_SMS(Manifest.permission.READ_SMS),
  RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH),
  RECEIVE_MMS(Manifest.permission.RECEIVE_MMS);

  private final String permission;

  PermissionGroupSms(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}

