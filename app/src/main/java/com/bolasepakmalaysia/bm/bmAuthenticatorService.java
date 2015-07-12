package com.bolasepakmalaysia.bm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by zul on 28-Nov-14.
 */
public class bmAuthenticatorService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new bmAuthenticator(this).getIBinder();
    }
}
