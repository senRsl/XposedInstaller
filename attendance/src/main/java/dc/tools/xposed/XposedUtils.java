package dc.tools.xposed;

import android.util.Log;
import dc.common.utils.StringUtils;
import dc.tools.attendance.BuildConfig;
import de.robv.android.xposed.XSharedPreferences;

import static dc.android.common.CoreContext.DC_SERVICE_DATAMANAGER_SP;
import static dc.tools.attendance.AttendanceContext.KEY_TARGET;

/**
 * @author senrsl
 * @ClassName: XposedUtils
 * @Package: dc.tools.xposed
 * @CreateTime: 2020/11/30 6:12 PM
 */
public class XposedUtils {

    /**
     * 判断当前 packageName 是否需要 hook
     *
     * @param packageName
     * @return
     */
    public static boolean isNeedHook(String packageName) {
        String target = getTarget();
        Log.i("TEST", "hook getTarget result " + target);

        if (StringUtils.isEmpty(target)) return false;

        return target.indexOf(packageName) != -1;
    }

    private static String target = null;

    private static String getTarget() {
        Log.i("TEST", "hook current target " + target);
        if (null != target) return target;
        XSharedPreferences xsp = new XSharedPreferences(BuildConfig.APPLICATION_ID, DC_SERVICE_DATAMANAGER_SP);

        return target = xsp.getString(KEY_TARGET, null);
    }

    public static void setTarget(String target) {
        XposedUtils.target = target;
    }
}
