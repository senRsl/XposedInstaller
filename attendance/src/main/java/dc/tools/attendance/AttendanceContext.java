package dc.tools.attendance;

import android.os.Environment;
import dc.android.common.CoreContext;
import dc.common.utils.StringUtils;

/**
 * @author senrsl
 * @ClassName: AttendanceContext
 * @Package: dc.tools.attendance
 * @CreateTime: 2020/11/30 5:44 PM
 */
public class AttendanceContext extends CoreContext {

    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_PATH = "path";
    public static final String KEY_TARGET = "target";

    public static String NAME_COMP = "SENRSL";


    public static String getSharedConfig() {
        return StringUtils.sub(getDirStoragePath(), DC_SERVICE_DATAMANAGER_SP);
    }

    public static String getDirStoragePath() {
        return StringUtils.sub(Environment.getExternalStorageDirectory().getAbsolutePath(), URL_SPEC, "Android/data", URL_SPEC, BuildConfig.APPLICATION_ID, URL_SPEC);
    }


}
