package com.gigigo.permissions.listeners;

import android.content.Context;
import android.widget.Toast;
import com.gigigo.permissions.R;
import com.gigigo.permissions.ui.PermissionsUIViews;
import com.karumi.dexterox.MultiplePermissionsReport;
import com.karumi.dexterox.PermissionToken;
import com.karumi.dexterox.listener.PermissionDeniedResponse;
import com.karumi.dexterox.listener.PermissionGrantedResponse;
import com.karumi.dexterox.listener.PermissionRequest;
import java.util.List;

/**
 * Created by nubor on 23/02/2017.
 */
public class MultiplePermissionsListenerImpl
    implements com.karumi.dexterox.listener.multi.MultiplePermissionsListener {
  private final Context mContext;

  public MultiplePermissionsListenerImpl(Context context) {
    this.mContext = context;
  }

  @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
    for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
      String grantedStr = mContext.getResources().getString(R.string.ggg_permission_literal) + " " +
          response.getPermissionName() + " " + mContext.getResources()
          .getString(R.string.ggg_permission_granted);
      PermissionsUIViews.showPermissionToast(mContext, grantedStr);
    }

    for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
      String deniedStr = mContext.getResources().getString(R.string.ggg_permission_literal) + " " +
          response.getPermissionName() + " " + mContext.getResources()
          .getString(R.string.ggg_permission_denied);
      PermissionsUIViews.showPermissionToast(mContext, deniedStr);
    }
  }

  @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
      PermissionToken token) {
    for (PermissionRequest pr : permissions) {
      PermissionsUIViews.showDefaultRationaleView(token, mContext);
    }
  }
}