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

import com.gigigo.ggglib.permission.MultiplePermissionsReport;
import com.gigigo.ggglib.permission.PermissionToken;
import com.gigigo.ggglib.permission.listener.PermissionDeniedResponse;
import com.gigigo.ggglib.permission.listener.PermissionGrantedResponse;
import com.gigigo.ggglib.permission.listener.PermissionRequest;
import com.gigigo.ggglib.permission.listener.multi.MultiplePermissionsListener;
import java.util.List;
@Deprecated //asv mejor en el ejemplo mostrar el new en el aksingpermissions q hacer esto
public class SampleMultiplePermissionListener implements MultiplePermissionsListener {

  private final SampleActivity activity;

  public SampleMultiplePermissionListener(SampleActivity activity) {
    this.activity = activity;
  }

  @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
    for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
      //activity.showPermissionGranted(response.getPermissionName());
      System.out.println("Permiso concecido");
    }

    for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
      //activity.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
      System.out.println("Permiso Denegado");
    }
  }

  @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
      PermissionToken token) {
    activity.showPermissionRationale(token);
  }
}
