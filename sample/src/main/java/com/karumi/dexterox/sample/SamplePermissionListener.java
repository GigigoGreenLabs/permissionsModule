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

import com.karumi.dexterox.PermissionToken;
import com.karumi.dexterox.listener.PermissionDeniedResponse;
import com.karumi.dexterox.listener.PermissionGrantedResponse;
import com.karumi.dexterox.listener.PermissionRequest;
import com.karumi.dexterox.listener.single.PermissionListener;

public class SamplePermissionListener implements PermissionListener {

  private final SampleActivity activity;

  public SamplePermissionListener(SampleActivity activity) {
    this.activity = activity;
  }

  @Override public void onPermissionGranted(PermissionGrantedResponse response) {
    activity.showPermissionGranted(response.getPermissionName());
  }

  @Override public void onPermissionDenied(PermissionDeniedResponse response) {
    activity.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
  }

  @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
      PermissionToken token) {
    activity.showPermissionRationale(token);
  }
}
