/*
 * Created by Gigigo Android Team
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.permissions.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.gigigo.permissions.R;
import com.gigigo.permissions.interfaces.RationaleResponse;
import com.karumi.dexterox.PermissionManager;
import com.karumi.dexterox.PermissionToken;

public class PermissionsUIViews {

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static void showRationaleView(final RationaleResponse rationaleResponse, Context context,
      int rationaleTitleStringId, int rationaleMessageStringId) {

    final boolean[] isClickedButton = { false };

    new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.ggg_theme_dialog)).setTitle(
        rationaleTitleStringId)
        .setMessage(rationaleMessageStringId)
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            isClickedButton[0] = true;

            dialog.dismiss();
            rationaleResponse.cancelPermissionRequest();
            PermissionManager.closeActivity();
          }
        })
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            isClickedButton[0] = true;

            dialog.dismiss();
            rationaleResponse.continuePermissionRequest();
          }
        })
        .setOnDismissListener(new DialogInterface.OnDismissListener() {
          @Override public void onDismiss(DialogInterface dialog) {
            if (!isClickedButton[0]) {
              rationaleResponse.cancelPermissionRequest();
              PermissionManager.closeActivity();
            }
          }
        })
        .show();
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static void showDefaultRationaleView(final PermissionToken token, Context context) {

    final boolean[] isClickedButton = { false };

    new AlertDialog.Builder(context)
        .setTitle(R.string.continueRequestPermissionRationaleTitle)
        .setMessage(R.string.continueRequestPermissionRationaleMessage)
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            isClickedButton[0] = true;
            dialog.dismiss();
            token.cancelPermissionRequest();
            PermissionManager.closeActivity();
          }
        })
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            isClickedButton[0] = true;
            dialog.dismiss();
            token.continuePermissionRequest();
          }
        })
        .setOnDismissListener(new DialogInterface.OnDismissListener() {
          @Override public void onDismiss(DialogInterface dialog) {
            if (!isClickedButton[0]) {
              token.cancelPermissionRequest();
              PermissionManager.closeActivity();
            }
          }
        })
        .show();
  }

  public static void showPermissionToast(Context context, int stringId) {
    if (stringId != -1) Toast.makeText(context, stringId, Toast.LENGTH_LONG).show();
  }

  public static void showPermissionToast(Context context, String text) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
  }
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static void showSettingsView(final Context context, int rationaleTitleStringId,
      int settingsStringId, int deniedStringId) {
    if (deniedStringId != -1) {
      new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.ggg_theme_dialog)).setTitle(
          rationaleTitleStringId)
          .setMessage(deniedStringId)
          .setPositiveButton(settingsStringId, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
              Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                  Uri.parse("package:" + context.getPackageName()));
              myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
              myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(myAppSettings);
            }
          })
          .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
              PermissionManager.closeActivity();
            }
          })

          .setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override public void onDismiss(DialogInterface dialog) {
              dialog.dismiss();
              PermissionManager.closeActivity();
            }
          })
          .show();
    }
  }
}
