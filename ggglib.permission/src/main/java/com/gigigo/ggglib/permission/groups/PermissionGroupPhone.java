package com.gigigo.ggglib.permission.groups;

import android.Manifest;

public enum  PermissionGroupPhone {
  READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE),
  CALL_PHONE(Manifest.permission.CALL_PHONE),
  READ_CALL_LOG(Manifest.permission.READ_CALL_LOG),
  WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG),
  ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL),
  USE_SIP(Manifest.permission.USE_SIP),
  PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS);

  private final String permission;

  PermissionGroupPhone(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
