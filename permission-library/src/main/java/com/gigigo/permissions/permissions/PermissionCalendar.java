package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupCalendar;
import com.gigigo.permissions.groups.PermissionGroupLocation;
import com.gigigo.permissions.interfaces.Permission;

/**
 * Created by nubor on 23/02/2017.
 */
 public class PermissionCalendar implements Permission {

  private final Context context;
  private final PermissionGroupCalendar permissionGroupCalendar;

  public PermissionCalendar(Context context, PermissionGroupCalendar permissionGroupCalendar) {
    this.context = context;
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
    return context.getResources().getInteger(R.integer.ggg_permission_retries_calendar);
  }
}
