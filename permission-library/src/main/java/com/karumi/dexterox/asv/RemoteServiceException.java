package com.karumi.dexterox.asv;

import android.util.AndroidRuntimeException;

/**
 * Created by Alberto Sainz on 20/07/2016.
 */
final class RemoteServiceException extends AndroidRuntimeException {
    public RemoteServiceException(String msg) {
        super(msg);
    }
}
