/*
 * Copyright (C) 2015 Karumi.
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

package com.karumi.dexterox.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.gigigo.permissions.AndroidPermissionCheckerImpl;
import com.gigigo.permissions.groups.PermissionGroupCamera;
import com.gigigo.permissions.groups.PermissionGroupContacts;
import com.gigigo.permissions.groups.PermissionGroupMicrophone;
import com.gigigo.permissions.interfaces.Permission;
import com.gigigo.permissions.interfaces.UserPermissionRequestResponseListener;
import com.gigigo.permissions.permissions.PermissionCamera;
import com.gigigo.permissions.permissions.PermissionContacts;
import com.gigigo.permissions.permissions.PermissionMicrophone;
import com.karumi.dexterox.PermissionToken;
import com.karumi.dexterox.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;
import com.karumi.dexterox.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexterox.listener.single.PermissionListener;

/**
 * Sample activity showing the permission request process with Dexter.
 */
public class SampleActivity extends Activity {

  TextView audioPermissionFeedbackView;
  TextView cameraPermissionFeedbackView;
  TextView contactsPermissionFeedbackView;

  Button contacts_permission_button, camera_permission_button, audio_permission_button,
      all_permissions_button;

  private AndroidPermissionCheckerImpl permissionChecker;
  private PermissionContacts permissionContacts;
  private PermissionCamera permissionCamera;
  private PermissionMicrophone permissionMicrophone;

  private ViewGroup rootView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sample_activity);

    audioPermissionFeedbackView = (TextView) findViewById(R.id.audio_permission_feedback);
    cameraPermissionFeedbackView = (TextView) findViewById(R.id.camera_permission_feedback);
    contactsPermissionFeedbackView = (TextView) findViewById(R.id.contacts_permission_feedback);

    permissionChecker = new AndroidPermissionCheckerImpl(this);
    permissionContacts = new PermissionContacts(this, PermissionGroupContacts.READ_CONTACTS);
    permissionCamera = new PermissionCamera(this, PermissionGroupCamera.CAMERA);
    permissionMicrophone = new PermissionMicrophone(this, PermissionGroupMicrophone.RECORD_AUDIO);

    contacts_permission_button = (Button) findViewById(R.id.contacts_permission_button);
    camera_permission_button = (Button) findViewById(R.id.camera_permission_button);
    audio_permission_button = (Button) findViewById(R.id.audio_permission_button);
    all_permissions_button = (Button) findViewById(R.id.all_permissions_button);

    contacts_permission_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onContactsPermissionButtonClicked();
      }
    });

    camera_permission_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onCameraPermissionButtonClicked();
      }
    });
    audio_permission_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onAudioPermissionButtonClicked();
      }
    });
    all_permissions_button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onAllPermissionsButtonClicked();
      }
    });

    rootView = (ViewGroup) findViewById(R.id.myContent);
  }

  public void onAllPermissionsButtonClicked() {

    MultiplePermissionsListener feedbackViewMultiplePermissionListener =
        new SampleMultiplePermissionListener(this);

    SnackbarOnAnyDeniedMultiplePermissionsListener snackbarOnAnyDeniedMultiplePermissionsListener =
        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(rootView,
            R.string.all_permissions_denied_feedback)
            .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
            .build();

    permissionChecker.askForPermissions(feedbackViewMultiplePermissionListener, permissionCamera, permissionContacts,
        permissionMicrophone);

    SnackbarOnAnyDeniedMultiplePermissionsListener build =
        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(rootView,
            R.string.all_permissions_denied_feedback)
            .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
            .build();

    CompositeMultiplePermissionsListener allPermissionsListener =
        new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener, build);

  }

  public void onCameraPermissionButtonClicked() {
    boolean granted = permissionChecker.isGranted(permissionCamera);
    if (!granted) {
      permissionChecker.askForPermission(permissionCamera,
          new UserPermissionRequestResponseListener() {
            @Override public void onPermissionAllowed(boolean permissionAllowed) {
              if (permissionAllowed) {
                showPermissionGranted(permissionCamera);
              } else {
                showPermissionDenied(permissionCamera);
              }
            }
          });
    }
  }

  public void onContactsPermissionButtonClicked() {
    boolean granted = permissionChecker.isGranted(permissionContacts);
    if (!granted) {
      permissionChecker.askForPermission(permissionContacts,
          new UserPermissionRequestResponseListener() {
            @Override public void onPermissionAllowed(boolean permissionAllowed) {
              if (permissionAllowed) {
                showPermissionGranted(permissionContacts);
              } else {
                showPermissionDenied(permissionContacts);
              }
            }
          });
    }
  }

  public void onAudioPermissionButtonClicked() {
    boolean granted = permissionChecker.isGranted(permissionMicrophone);
    if (!granted) {
      permissionChecker.askForPermission(permissionMicrophone,
          new UserPermissionRequestResponseListener() {
            @Override public void onPermissionAllowed(boolean permissionAllowed) {
              if (permissionAllowed) {
                showPermissionGranted(permissionMicrophone);
              } else {
                showPermissionDenied(permissionMicrophone);
              }
            }
          });
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public void showPermissionRationale(final PermissionToken token) {
    new AlertDialog.Builder(this).setTitle(R.string.permission_rationale_title)
        .setMessage(R.string.permission_rationale_message)
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            token.cancelPermissionRequest();
          }
        })
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            token.continuePermissionRequest();
          }
        })
        .setOnDismissListener(new DialogInterface.OnDismissListener() {
          @Override public void onDismiss(DialogInterface dialog) {
            token.cancelPermissionRequest();
          }
        })
        .show();
  }

  public void showPermissionGranted(Permission permission) {
    TextView feedbackView = getFeedbackViewForPermission(permission);
    feedbackView.setText(R.string.permission_granted_feedback);
  }

  public void showPermissionDenied(Permission permission) {
    TextView feedbackView = getFeedbackViewForPermission(permission);
    feedbackView.setText(R.string.permission_denied_feedback);
  }

  private TextView getFeedbackViewForPermission(Permission permission) {
    TextView feedbackView;

    if (permission.equals(permissionCamera)) {
      feedbackView = cameraPermissionFeedbackView;
    } else if (permission.equals(permissionContacts)) {
      feedbackView = contactsPermissionFeedbackView;
    } else if (permission.equals(permissionMicrophone)) {
      feedbackView = audioPermissionFeedbackView;
    } else {
      throw new RuntimeException("No feedback view for this permission");
    }

    return feedbackView;
  }
}
