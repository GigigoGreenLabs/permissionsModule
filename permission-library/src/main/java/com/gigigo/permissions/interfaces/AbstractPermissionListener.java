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

package com.gigigo.permissions.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.gigigo.permissions.ui.PermissionsUIViews;
import com.karumi.dexterox.Dexter;
import com.karumi.dexterox.PermissionToken;
import com.karumi.dexterox.listener.PermissionDeniedResponse;
import com.karumi.dexterox.listener.PermissionGrantedResponse;
import com.karumi.dexterox.listener.PermissionRequest;
import com.karumi.dexterox.listener.single.PermissionListener;

public abstract class AbstractPermissionListener implements PermissionListener {

  private UserPermissionRequestResponseListener userPermissionRequestResponseListener;
  private Activity activity;

  public AbstractPermissionListener(Activity activity) {
    this.activity = activity;
  }

  public AbstractPermissionListener(
      UserPermissionRequestResponseListener userPermissionRequestResponseListener,
      Activity activity) {
    this.userPermissionRequestResponseListener = userPermissionRequestResponseListener;
    this.activity = activity;
  }

  @Override public void onPermissionGranted(PermissionGrantedResponse response) {
    if (userPermissionRequestResponseListener != null) {
      userPermissionRequestResponseListener.onPermissionAllowed(true);
    }
  }

  @Override public void onPermissionDenied(PermissionDeniedResponse response) {
    if (userPermissionRequestResponseListener != null) {
      userPermissionRequestResponseListener.onPermissionAllowed(false);
    }
  }

  @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest,
      PermissionToken token) {

    RationaleResponse rationaleResponse = createRationaleResponseInstance(token);

    if (showRationaleDialog()) {
      PermissionsUIViews.showRationaleView(rationaleResponse, activity,
          getPermissionRationaleTitle(), getPermissionRationaleMessage());
    }
    if (getNumRetry() > 0 && getNumRetry() == readNumRetryDone()) {
      PermissionsUIViews.showSettingsView(activity, getPermissionRationaleTitle(),
          getPermissionSettingsDeniedFeedback(), getPermissionDeniedFeedback());
    }

    //no retry close permission request
    if (getNumRetry() == 0) {
      rationaleResponse.cancelPermissionRequest();
      Dexter.closeActivity();   //kill dexterActivity
    }

    if (getNumRetry() > 0) writeNewRetry();

    if (getNumRetry() > 0 && readNumRetryDone() >= getNumRetry()) {
      rationaleResponse.cancelPermissionRequest();
      Dexter.closeActivity();
    }
  }

  private RationaleResponse createRationaleResponseInstance(final PermissionToken token) {
    return new RationaleResponse() {
      @Override public void cancelPermissionRequest() {
        token.cancelPermissionRequest();
      }

      @Override public void continuePermissionRequest() {
        token.continuePermissionRequest();
      }
    };
  }

  public abstract int getPermissionDeniedFeedback();

  public abstract int getPermissionRationaleMessage();

  public abstract int getPermissionRationaleTitle();

  public abstract int getNumRetry();

  public abstract int getPermissionSettingsDeniedFeedback();

  /*NEW retrys save in preferences*/
  private String calculateHashCodeKey() {
    return this.hashCode() + "";
  }

  private void writeNewRetry() {
    if (activity != null) {

      String prefKey = calculateHashCodeKey();
      SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
      int defaultValue = 0;
      int numRetrys = sharedPref.getInt(prefKey, defaultValue);

      numRetrys = numRetrys + 1;
      SharedPreferences.Editor editor = sharedPref.edit();
      editor.putInt(prefKey, numRetrys);
      editor.apply();
    }
  }

  private int readNumRetryDone() {
    if (activity != null) {

      String prefKey = calculateHashCodeKey();
      SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
      int defaultValue = 0;
      return sharedPref.getInt(prefKey, defaultValue);
    }
    return 0;
  }

  private boolean showRationaleDialog() {

    if (getNumRetry() == -1) return true; //infinite retries
    if (getNumRetry() == 0) return false; //no one retries
    //check retries
    return getNumRetry() > readNumRetryDone();
  }

  /****
   * calculate hashcode from permission for create key in preferences and counts retries
   */
  public int hashCode() {
    int megaHash = 0;
    try {
      megaHash = hashCodeObject(this.getPermissionDeniedFeedback());
      megaHash = megaHash + hashCodeObject(this.getPermissionRationaleMessage());
      megaHash = megaHash + hashCodeObject(this.getPermissionRationaleTitle());
      // megaHash = megaHash + hashCodeObject(this.getNumRetry());
      megaHash = megaHash + hashCodeObject(activity.getApplicationContext().getPackageName());
    } catch (Exception e) {
      Log.i("ERROR ", e.getMessage());
    } catch (Throwable throwable) {
      Log.i("ERROR ", throwable.getMessage());
    }
    return megaHash;
  }

  private static int hashCodeObject(Object o) {
    return (o == null) ? 0 : o.hashCode();
  }
}
