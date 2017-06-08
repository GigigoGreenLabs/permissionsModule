package com.gigigo.ggglib.permission.permissions;

import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.groups.PermissionGroupCalendar;

/**
 * Created by nubor on 23/02/2017.
 */
public class PermissionCalendar implements Permission {

  private final PermissionGroupCalendar permissionGroupCalendar;

  public PermissionCalendar(PermissionGroupCalendar permissionGroupCalendar) {
    this.permissionGroupCalendar = permissionGroupCalendar;
  }

  @Override public String getAndroidPermissionStringType() {
    return permissionGroupCalendar.getPermission();
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_calendar;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_calendar;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_calendar;
  }

  @Override public int getNumRetry() {
    return R.integer.ggg_permission_retries_calendar;
  }
}
