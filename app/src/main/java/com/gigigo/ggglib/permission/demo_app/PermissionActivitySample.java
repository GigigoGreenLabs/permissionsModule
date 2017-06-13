package com.gigigo.ggglib.permission.demo_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.gigigo.ggglib.permission.groups.PermissionGroupCalendar;
import com.gigigo.ggglib.permission.groups.PermissionGroupLocation;
import com.gigigo.ggglib.permission.groups.PermissionGroupPhone;
import com.gigigo.ggglib.permission.listeners.UserPermissionRequestResponseListener;
import com.gigigo.ggglib.permission.permissions.Permission;
import com.gigigo.ggglib.permission.permissions.PermissionCalendar;
import com.gigigo.ggglib.permission.permissions.PermissionLocation;
import com.gigigo.ggglib.permission.permissions.PermissionPhone;
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
  private PermissionPhone permissionPhone = new PermissionPhone(PermissionGroupPhone.CALL_PHONE);


  //region allpermissions
  Button btnAskAllPermissions;
  private PermissionCalendar permissionCalendar =
      new PermissionCalendar(PermissionGroupCalendar.READ_CALENDAR);
  private PermissionLocation permissionLocation =
      new PermissionLocation(PermissionGroupLocation.ACCESS_FINE_LOCATION);

  //endregion

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


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
    Toast.makeText(this,"NO WORKING YET!!",Toast.LENGTH_LONG).show();

    boolean granted = SampleApplication.permissionWrapper.isAllGranted(permissionCalendar, permissionLocation);
    if (!granted) {
      SampleApplication.permissionWrapper.askForPermissions(new MultiplePermissionsListener() {
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

    boolean granted = SampleApplication.permissionWrapper.isGranted(permissionPhone);
    if (!granted) {
      SampleApplication.permissionWrapper.askForPermission(new UserPermissionRequestResponseListener() {
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