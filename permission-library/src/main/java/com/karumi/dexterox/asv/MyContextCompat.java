package com.karumi.dexterox.asv;

import android.content.Context;
import android.os.Process;
import android.support.annotationox.NonNull;

/**
 * Created by Alberto Sainz on 19/07/2016.
 */
public class MyContextCompat {

    public static int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        return context.checkPermission(permission, android.os.Process.myPid(), Process.myUid());
    }
}
