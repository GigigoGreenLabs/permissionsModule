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

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
//import android.support.designox.widget.Snackbar;
//import android.support.v4ox.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.karumi.dexterox.Dexter;
import com.karumi.dexterox.PermissionToken;
import com.karumi.dexterox.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;
import com.karumi.dexterox.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexterox.listener.single.CompositePermissionListener;
import com.karumi.dexterox.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexterox.listener.single.PermissionListener;
import com.karumi.dexterox.listener.single.SnackbarOnDeniedPermissionListener;

/**
 * Sample activity showing the permission request process with Dexter.
 */
public class SampleActivity extends Activity {


    TextView audioPermissionFeedbackView;
    TextView cameraPermissionFeedbackView;
    TextView contactsPermissionFeedbackView;

    Button contacts_permission_button,
            camera_permission_button,
            audio_permission_button,
            all_permissions_button;

    ViewGroup rootView;

    private MultiplePermissionsListener allPermissionsListener;
    private PermissionListener cameraPermissionListener;
    private PermissionListener contactsPermissionListener;
    private PermissionListener audioPermissionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        audioPermissionFeedbackView = (TextView) findViewById(R.id.audio_permission_feedback);
        cameraPermissionFeedbackView = (TextView) findViewById(R.id.camera_permission_feedback);
        contactsPermissionFeedbackView = (TextView) findViewById(R.id.contacts_permission_feedback);

        contacts_permission_button = (Button) findViewById(R.id.contacts_permission_button);
        camera_permission_button = (Button) findViewById(R.id.camera_permission_button);
        audio_permission_button = (Button) findViewById(R.id.audio_permission_button);
        all_permissions_button = (Button) findViewById(R.id.all_permissions_button);

        contacts_permission_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContactsPermissionButtonClicked();
            }
        });

        camera_permission_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCameraPermissionButtonClicked();
            }
        });
        audio_permission_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAudioPermissionButtonClicked();

            }
        });
        all_permissions_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAllPermissionsButtonClicked();
            }
        });

        rootView = (ViewGroup) findViewById(R.id.myContent);
        createPermissionListeners();

    /*
     * If during the rotate screen process the activity has been restarted you can call this method
     * to start with the check permission process without keep in an Android Bundle the state of
     * the request permission process.
     */
        Dexter.continuePendingRequestsIfPossible(allPermissionsListener);
    }


    public void onAllPermissionsButtonClicked() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermissions(allPermissionsListener, Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS, Manifest.permission.RECORD_AUDIO);
    }


    public void onCameraPermissionButtonClicked() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Dexter.checkPermissionOnSameThread(cameraPermissionListener, Manifest.permission.CAMERA);
            }
        }).start();
    }


    public void onContactsPermissionButtonClicked() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermission(contactsPermissionListener, Manifest.permission.READ_CONTACTS);
    }

    public void onAudioPermissionButtonClicked() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermission(audioPermissionListener, Manifest.permission.RECORD_AUDIO);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {

        new android.app.AlertDialog.Builder(new ContextThemeWrapper(this, com.karumi.dexterox.R.style.AlertDialogTheme_OX))
                .setTitle(R.string.permission_rationale_title)
                .setMessage(R.string.permission_rationale_message)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        token.cancelPermissionRequest();
                    }
                })
                .show();
    }

    public void showPermissionGranted(String permission) {
        TextView feedbackView = getFeedbackViewForPermission(permission);
        feedbackView.setText(R.string.permission_granted_feedback);

    }

    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {
        TextView feedbackView = getFeedbackViewForPermission(permission);
        feedbackView.setText(isPermanentlyDenied
                ? R.string.permission_permanently_denied_feedback
                : R.string.permission_denied_feedback);

    }

    private void createPermissionListeners() {
        PermissionListener feedbackViewPermissionListener = new SamplePermissionListener(this);
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new SampleMultiplePermissionListener(this);

        allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(rootView,
                                R.string.all_permissions_denied_feedback)
                                .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                                .build());


        final SnackbarOnDeniedPermissionListener build =
                SnackbarOnDeniedPermissionListener.Builder
                        .with(rootView, R.string.contacts_permission_denied_feedback)
                        .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)

                        .build();

        contactsPermissionListener =
                new CompositePermissionListener(feedbackViewPermissionListener, build);

        PermissionListener dialogOnDeniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(this)
                        .withTitle(R.string.audio_permission_denied_dialog_title)
                        .withMessage(R.string.audio_permission_denied_dialog_feedback)
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.mipmap.ic_logo_karumi)
                        .build();
        audioPermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
                dialogOnDeniedPermissionListener);
        cameraPermissionListener = new SampleBackgroundThreadPermissionListener(this);
    }

    private TextView getFeedbackViewForPermission(String name) {
        TextView feedbackView;

        switch (name) {
            case Manifest.permission.CAMERA:
                feedbackView = cameraPermissionFeedbackView;
                break;
            case Manifest.permission.READ_CONTACTS:
                feedbackView = contactsPermissionFeedbackView;
                break;
            case Manifest.permission.RECORD_AUDIO:
                feedbackView = audioPermissionFeedbackView;
                break;
            default:
                throw new RuntimeException("No feedback view for this permission");
        }

        return feedbackView;
    }
}
