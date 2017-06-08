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

package com.gigigo.ggglib.permission.demo_app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.gigigo.ggglib.permission.PermissionCheckerImpl;
import com.gigigo.ggglib.permission.groups.PermissionGroupCamera;
import com.gigigo.ggglib.permission.groups.PermissionGroupContacts;
import com.gigigo.ggglib.permission.groups.PermissionGroupMicrophone;
import com.gigigo.ggglib.permission.interfaces.Permission;
import com.gigigo.ggglib.permission.interfaces.UserPermissionRequestResponseListener;
import com.gigigo.ggglib.permission.permissions.PermissionCamera;
import com.gigigo.ggglib.permission.permissions.PermissionContacts;
import com.gigigo.ggglib.permission.permissions.PermissionMicrophone;
import com.karumi.dexterox.PermissionManager;
import com.karumi.dexterox.PermissionToken;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;

/**
 * Sample activity showing the permission request process with PermissionManager.
 */
public class SampleActivity extends Activity {

  TextView audioPermissionFeedbackView;
  TextView cameraPermissionFeedbackView;
  TextView contactsPermissionFeedbackView;

  Button contacts_permission_button, camera_permission_button, audio_permission_button,
      all_permissions_button, btnYourOwnPermissionActivity;

  private PermissionCheckerImpl permissionChecker;

  private PermissionCamera permissionCamera;
  private PermissionMicrophone permissionMicrophone;
  private PermissionContacts permissionContacts;
  private ViewGroup rootView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sample_activity);
    //step1 Initialize PermissionManager
    PermissionManager.initialize(this.getApplicationContext());

    audioPermissionFeedbackView = (TextView) findViewById(R.id.audio_permission_feedback);
    cameraPermissionFeedbackView = (TextView) findViewById(R.id.camera_permission_feedback);
    contactsPermissionFeedbackView = (TextView) findViewById(R.id.contacts_permission_feedback);

    //step2 Declare permissionChecker
    permissionChecker = new PermissionCheckerImpl(this);

    //step3 declare Permissions
    permissionContacts = new PermissionContacts(this, PermissionGroupContacts.READ_CONTACTS);
    permissionCamera = new PermissionCamera(this, PermissionGroupCamera.CAMERA);
    permissionMicrophone = new PermissionMicrophone(this, PermissionGroupMicrophone.RECORD_AUDIO);

    contacts_permission_button = (Button) findViewById(R.id.contacts_permission_button);
    camera_permission_button = (Button) findViewById(R.id.camera_permission_button);
    audio_permission_button = (Button) findViewById(R.id.audio_permission_button);
    all_permissions_button = (Button) findViewById(R.id.all_permissions_button);

    btnYourOwnPermissionActivity = (Button) findViewById(R.id.btnYourOwnPermissionActivity);

    btnYourOwnPermissionActivity.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onYourownActivityClicked();
      }
    });

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
    //step4 Multiple permissions asking
    MultiplePermissionsListener feedbackViewMultiplePermissionListener =
        new SampleMultiplePermissionListener(this);
    permissionChecker.askForPermissions(feedbackViewMultiplePermissionListener, permissionCamera,
        permissionContacts, permissionMicrophone);
  }

  public void onCameraPermissionButtonClicked() {
    //step5 single Permission with Retries
    boolean granted = permissionChecker.isGranted(permissionCamera);//step6 is granted
    if (!granted) {
      //step7 askforPermission
      permissionChecker.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          if (permissionAllowed) {
            showPermissionGranted(permissionCamera, numberDoneRetries);
          } else {
            showPermissionDenied(permissionCamera, numberDoneRetries);
          }
        }
      }, permissionCamera);
    }
  }

  public void onContactsPermissionButtonClicked() {
    boolean granted = permissionChecker.isGranted(permissionContacts);
    if (!granted) {
      permissionChecker.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          if (permissionAllowed) {
            showPermissionGranted(permissionContacts, numberDoneRetries);
          } else {
            showPermissionDenied(permissionContacts, numberDoneRetries);
          }
        }
      }, permissionContacts);
    }
  }

  public void onAudioPermissionButtonClicked() {
    boolean granted = permissionChecker.isGranted(permissionMicrophone);
    if (!granted) {
      permissionChecker.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          if (permissionAllowed) {
            showPermissionGranted(permissionMicrophone, numberDoneRetries);
          } else {
            showPermissionDenied(permissionMicrophone, numberDoneRetries);
          }
        }
      }, permissionMicrophone);
    }
  }

  public void onYourownActivityClicked() {
    Intent intent = new Intent(this, PermissionActivitySample.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    this.startActivity(intent);
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

  public void showPermissionGranted(Permission permission, int numberDoneRetries) {
    TextView feedbackView = getFeedbackViewForPermission(permission);
    feedbackView.setText(this.getResources().getString(R.string.permission_granted_feedback)
        + " "
        + numberDoneRetries
        + " retries");
  }

  public void showPermissionDenied(Permission permission, int numberDoneRetries) {
    TextView feedbackView = getFeedbackViewForPermission(permission);
    feedbackView.setText(this.getResources().getString(R.string.permission_denied_feedback)
        + " "
        + numberDoneRetries
        + " retries");
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
