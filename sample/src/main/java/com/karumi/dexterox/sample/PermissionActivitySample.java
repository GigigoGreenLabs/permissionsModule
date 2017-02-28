package com.karumi.dexterox.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.gigigo.permissions.PermissionCheckerImpl;
import com.gigigo.permissions.groups.PermissionGroupCalendar;
import com.gigigo.permissions.groups.PermissionGroupLocation;
import com.gigigo.permissions.groups.PermissionGroupPhone;
import com.gigigo.permissions.interfaces.Permission;
import com.gigigo.permissions.interfaces.UserPermissionRequestResponseListener;
import com.gigigo.permissions.permissions.PermissionCalendar;
import com.gigigo.permissions.permissions.PermissionLocation;
import com.gigigo.permissions.permissions.PermissionPhone;
import com.karumi.dexterox.MultiplePermissionsReport;
import com.karumi.dexterox.PermissionActivity;
import com.karumi.dexterox.PermissionToken;
import com.karumi.dexterox.listener.PermissionRequest;
import com.karumi.dexterox.listener.multi.MultiplePermissionsListener;
import java.util.List;

/**
 * Created by nubor on 27/02/2017.
 */
public class PermissionActivitySample extends PermissionActivity implements View.OnClickListener {
  Button btnAskingPermission;
  TextView txtSingle, txtMulti;
  private PermissionPhone permissionPhone =
      new PermissionPhone(this, PermissionGroupPhone.CALL_PHONE);
  private PermissionCheckerImpl permissionChecker;

  //region allpermissions
  Button btnAskAllPermissions;
  private PermissionCalendar permissionCalendar =
      new PermissionCalendar(this, PermissionGroupCalendar.READ_CALENDAR);
  private PermissionLocation permissionLocation =
      new PermissionLocation(this, PermissionGroupLocation.ACCESS_FINE_LOCATION);

  //endregion

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    permissionChecker = new PermissionCheckerImpl(this);

    setContentView(R.layout.permission_activity);

    txtSingle = (TextView) findViewById(R.id.txtSinglePermission);
    txtMulti = (TextView) findViewById(R.id.txtMultiPermission);

    btnAskingPermission = (Button) findViewById(R.id.btnAskPermission);
    btnAskingPermission.setOnClickListener(this);

    //allpermissions
    btnAskAllPermissions = (Button) findViewById(R.id.btnAskAllPermissions);
    btnAskAllPermissions.setOnClickListener(this);
  }

  public void showPermissionGranted(Permission permission, int numberDoneRetries) {
    if (permission != null) {
      txtSingle.setText(
          permission.getAndroidPermissionStringType().replace("android.permission", "")
              + " Granted"
              + " Retries: "
              + numberDoneRetries);
    } else {
      txtMulti.setText("All permissions Granted");
    }
  }

  public void showPermissionDenied(Permission permission, int numberDoneRetries) {
    if (permission != null) {
      txtSingle.setText(
          permission.getAndroidPermissionStringType().replace("android.permission", "")
              + " Denied"
              + " Retries: "
              + numberDoneRetries);
    } else {
      txtMulti.setText("All permissions Denied");
    }
  }

  @Override public void onClick(View v) {
    if (v.getId() == R.id.btnAskPermission) {
      askPhonePermissionClick();
    }

    if (v.getId() == R.id.btnAskAllPermissions) {
      askAllPermissionsClick();
    }
  }

  //region multiple permissions
  public void showPermissionRationaleMultiple() {
    txtMulti.setText("All permissions on RationaleView");
  }

  private void askAllPermissionsClick() {
    boolean granted = permissionChecker.isAllGranted(permissionCalendar, permissionLocation);
    if (!granted) {
      permissionChecker.askForPermissions(new MultiplePermissionsListener() {
        @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
          if (report.areAllPermissionsGranted()) {
            showPermissionGranted(null, 0);
          } else {
            showPermissionDenied(null, 0);
          }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
            PermissionToken token) {
          showPermissionRationaleMultiple();
        }
      }, permissionCalendar, permissionLocation);
    }
  }

  //endregion
  //region single permission
  private void askPhonePermissionClick() {

    boolean granted = permissionChecker.isGranted(permissionPhone);
    if (!granted) {
      permissionChecker.askForPermission(new UserPermissionRequestResponseListener() {
        @Override
        public void onPermissionAllowed(boolean permissionAllowed, int numberDoneRetries) {
          if (permissionAllowed) {
            showPermissionGranted(permissionPhone, numberDoneRetries);
          } else {
            showPermissionDenied(permissionPhone, numberDoneRetries);
          }
        }
      }, permissionPhone);
    }
  }
  //endregion
}