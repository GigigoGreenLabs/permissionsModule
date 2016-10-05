package com.karumi.dexterox.asv;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.annotationox.NonNull;
//import android.support.v4ox.app.ActivityCompatApi23;
//import android.support.v4ox.app.ActivityCompatApi23;

public class MyActivityCompat extends Activity implements MyRequestPermissionsRequestCodeValidator
        , MyOnRequestPermissionsResultCallback {
    //region requestPermissions
    private static final String REQUEST_PERMISSIONS_WHO_PREFIX = "@android:requestPermissions:";
    public static final String ACTION_REQUEST_PERMISSIONS =
            "android.content.pm.action.REQUEST_PERMISSIONS";
    public static final String EXTRA_REQUEST_PERMISSIONS_NAMES =
            "android.content.pm.extra.REQUEST_PERMISSIONS_NAMES";
    public void MyRequestPermissions(Activity activity, String[] permissions,
                                     int requestCode) {
        if (activity instanceof MyRequestPermissionsRequestCodeValidator) {
            ((MyRequestPermissionsRequestCodeValidator) activity)
                    .validateRequestPermissionsRequestCode(requestCode);
        }
        requestPermissions3(permissions, requestCode);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public final void requestPermissions3(@NonNull String[] permissions, int requestCode) {
       // Intent intent = getPackageManager().buildRequestPermissionsIntent(permissions);
        Intent intent = myBuildRequestPermissionsIntent(permissions);
       startActivityForResult(intent,requestCode,null);
        // startActivityForResult(REQUEST_PERMISSIONS_WHO_PREFIX, intent, requestCode, null);
    }
    public Intent myBuildRequestPermissionsIntent(@NonNull String[] permissions) {

        Intent intent = new Intent(ACTION_REQUEST_PERMISSIONS);
        intent.putExtra(EXTRA_REQUEST_PERMISSIONS_NAMES, permissions);
        intent.setPackage(getPermissionControllerPackageName());
        return intent;
    }
    public   String getPermissionControllerPackageName(){
        return "com.karumi.dexter.sample";
        //return getPackageName();
         }
    /**/

//
//    private Instrumentation mInstrumentation;
//    private IBinder mToken;
//    ActivityThread mMainThread;
//    public void startActivityForResult(
//            String who, Intent intent, int requestCode, @Nullable Bundle options) {
//        final int version = Build.VERSION.SDK_INT;
//        if (version >= 23) {
//            Uri referrer = null;// onProvideReferrer();
//            if (referrer != null) {
//                intent.putExtra(Intent.EXTRA_REFERRER, referrer);
//            }
//           // Instrumentation.ActivityResult ar =
//                    startActivityForResult(who,
//                    intent, requestCode, options);
//              /*
//                    mInstrumentation.execStartActivity(
//                            this, mMainThread.getApplicationThread(), mToken, who,
//                            intent, requestCode, options);
//            if (ar != null) {
//                mMainThread.sendActivityResult(
//                        mToken, who, requestCode,
//                        ar.getResultCode(), ar.getResultData());
//            }
//            cancelInputsAndStartExitTransition(options);
//            */
//        }
//    }

    public void requestPermissions(final @NonNull Activity activity,
                                   final @NonNull String[] permissions, final int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            MyRequestPermissions(activity, permissions, requestCode);
        } else if (activity instanceof MyOnRequestPermissionsResultCallback) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    final int[] grantResults = new int[permissions.length];

                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();

                    final int permissionCount = permissions.length;
                    for (int i = 0; i < permissionCount; i++) {
                        grantResults[i] = packageManager.checkPermission(
                                permissions[i], packageName);
                    }

                    ((MyOnRequestPermissionsResultCallback) activity).onRequestPermissionsResult(
                            requestCode, permissions, grantResults);
                }
            });
        }
    }

    //endregion

    //region shouldShowRequestPermissionRationale
    public boolean shouldShowRequestPermissionRationale(@NonNull Activity activity,
                                                        @NonNull String permission) {
        return shouldShowRequestPermissionRationale1(activity, permission);
    }

    public boolean shouldShowRequestPermissionRationale1(@NonNull Activity activity,
                                                         @NonNull String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            return shouldShowRequestPermissionRationale2(activity, permission);
        }
        return false;
    }

    public boolean shouldShowRequestPermissionRationale2(Activity activity,
                                                         String permission) {
        return shouldShowRequestPermissionRationale3(permission);
    }

    public boolean shouldShowRequestPermissionRationale3(@NonNull String permission) {
      return true;
        //return getPackageManager().shouldShowRequestPermissionRationale(permission);
    }

    //endregion


    //region checkselfpermision
    public int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(permission, android.os.Process.myPid(), Process.myUid());
    }

    //endregion


    @Override
    public void validateRequestPermissionsRequestCode(int requestCode) {

    }
}

