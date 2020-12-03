package dc.tools.attendance.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import dc.common.Logger;
import dc.tools.attendance.IDataSupport;

/**
 * @author senrsl
 * @ClassName: AttendanceService
 * @Package: dc.tools.attendance.service
 * @CreateTime: 2020/12/3 3:20 PM
 */
@Deprecated
public class AttendanceService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.w(getClass().getSimpleName(), "onCreate()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.w(getClass().getSimpleName(), "onBind()");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.w(getClass().getSimpleName(), "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.w(getClass().getSimpleName(), "onDestroy()");
    }

    private final IDataSupport.Stub binder = new IDataSupport.Stub() {
        @Override
        public String test(String message) throws RemoteException {
            return getClass().getCanonicalName();
        }

    };

}
