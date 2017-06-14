package com.gigigo.ggglib.permission.listeners.multi;

import android.content.Context;
import com.gigigo.ggglib.permission.R;
import com.gigigo.ggglib.permission.PermissionsUIViews;
import com.gigigo.ggglib.permission.MultiplePermissionsReport;
import com.gigigo.ggglib.permission.PermissionToken;
import com.gigigo.ggglib.permission.listener.PermissionDeniedResponse;
import com.gigigo.ggglib.permission.listener.PermissionGrantedResponse;
import com.gigigo.ggglib.permission.listener.PermissionRequest;
import java.util.List;

/**
 * Created by nubor on 23/02/2017.
 */
@Deprecated //asv esto era una impl para test, mejor borrarlo
public class MultiplePermissionsListenerImpl
    implements com.gigigo.ggglib.permission.listener.multi.MultiplePermissionsListener {
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