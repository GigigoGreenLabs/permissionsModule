package com.gigigo.permissions.permissions;

import android.content.Context;
import com.gigigo.permissions.R;
import com.gigigo.permissions.groups.PermissionGroupMicrophone;
import com.gigigo.permissions.groups.PermissionGroupSms;
import com.gigigo.permissions.interfaces.Permission;

/**
 * Created by nubor on 23/02/2017.
 */
public class PermissionSms implements Permission {

  private final Context context;
  private final PermissionGroupSms permissionGroupSms;

  public PermissionSms(Context context, PermissionGroupSms permissionGroupSms) {
    this.context = context;
    this.permissionGroupSms = permissionGroupSms;
  }

  @Override
  public String getAndroidPermissionStringType() {
    return permissionGroupSms.getPermission();
  }

  @Override
  public int getPermissionSettingsDeniedFeedback() {
    return R.string.ggg_permission_settings;
  }

  @Override
  public int getPermissionDeniedFeedback() {
    return R.string.ggg_permission_denied_sms;
  }

  @Override
  public int getPermissionRationaleTitle() {
    return R.string.ggg_permission_rationale_title_sms;
  }

  @Override
  public int getPermissionRationaleMessage() {
    return R.string.ggg_permission_rationale_message_sms;
  }

  @Override
  public int getNumRetry() {
    return context.getResources().getInteger(R.integer.ggg_permission_retries_sms);
  }
}
