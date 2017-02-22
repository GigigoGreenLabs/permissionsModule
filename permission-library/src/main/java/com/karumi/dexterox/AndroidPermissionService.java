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

package com.karumi.dexterox;

import android.app.Activity;
import android.content.Context;
import android.support.annotationox.NonNull;
import android.support.v4ox.app.ActivityCompat;
import android.support.v4ox.content.ContextCompat;

/**
 * Wrapper class for all the static calls to the Android permission system
 */
class AndroidPermissionService {

//    private final MyActivityCompat mMyActivityCompat;
//
//    public AndroidPermissionService(MyActivityCompat myActivityCompat) {
//        mMyActivityCompat = myActivityCompat;
//    }


    int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        //return mMyActivityCompat.checkSelfPermission(context, permission);
        return ContextCompat.checkSelfPermission(context, permission);
    }


    void requestPermissions(@NonNull Activity activity, @NonNull String[] permissions,
                            int requestCode) {
       // mMyActivityCompat.requestPermissions(activity, permissions, requestCode);
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }


    boolean shouldShowRequestPermissionRationale(@NonNull Activity activity,
                                                 @NonNull String permission) {
       // return mMyActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
       return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }
}
