package dc.tools.attendance;

import dc.android.common.BaseApplication;
import dc.common.Logger;

/**
 * @author senrsl
 * @ClassName: AttendanceApplication
 * @Package: dc.tools.attendance
 * @CreateTime: 2020/11/20 5:32 PM
 */
public class VsfApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        Logger.w("POWER START");
    }
}
