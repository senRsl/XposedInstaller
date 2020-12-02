package dc.tools.xposed;

import android.util.Log;
import dc.tools.attendance.BuildConfig;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static dc.android.common.CoreContext.DC_SERVICE_DATAMANAGER_SP;
import static dc.common.Global.EMPTY;
import static dc.tools.attendance.AttendanceContext.KEY_PATH;
import static dc.tools.attendance.AttendanceContext.KEY_TARGET;

/**
 * @author senrsl
 * @ClassName: HookCamera
 * @Package: dc.tools.xposed
 * @CreateTime: 2020/11/30 6:02 PM
 */
public class HookCamera implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        //Logger.w(getClass().getSimpleName(),"hook camera ",loadPackageParam.packageName);
//        XposedBridge.log(getClass().getSimpleName() + " hook camera " + loadPackageParam.packageName + " - " + XposedUtils.isNeedHook(loadPackageParam.packageName));
        XposedBridge.log(getClass().getSimpleName() + " hook camera " + loadPackageParam.packageName);

        //if (!XposedUtils.isNeedHook(loadPackageParam.packageName)) return;
        XSharedPreferences xsp = new XSharedPreferences(BuildConfig.APPLICATION_ID, DC_SERVICE_DATAMANAGER_SP);
        //xsp.makeWorldReadable();

        String target = xsp.getString(KEY_TARGET, null);

        String picPath = getPicPath();

        Log.i("TEST", "hook " + target + " 1 " + BuildConfig.APPLICATION_ID + " 2 " + DC_SERVICE_DATAMANAGER_SP + " 3 " + KEY_TARGET + " 4 " + picPath);


//        XposedBridge.log(getClass().getSimpleName() + "  hook camera on " + loadPackageParam.packageName + " picPath: " + picPath);
//
//        if (StringUtils.isEmpty(picPath)) return;
//
//        XposedHelpers.findAndHookMethod("android.os.BaseBundle", loadPackageParam.classLoader,
//                "getString", String.class, new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        //if (!isOpen) return;
//
//                        //通过拦截bundle返回值的方式修改图片地址
//                        if (param.args.length > 0 && (param.args[0] instanceof String)) {
//                            String pa = (String) param.args[0];
//                            if (pa.equals("extra_photo_url")) {
//
//                                //Logger.w(getClass().getSimpleName(), "get extra_photo_url: ", pa, "hook extra_photo_url: ", picPath);
//                                XposedBridge.log(getClass().getSimpleName() + "  get extra_photo_url:  " + pa + "  hook extra_photo_url:  " + picPath);
//                                param.setResult(picPath);
//                            }
//                        }
//                    }
//                });
    }

    private String getPicPath() {
        XSharedPreferences xsp = new XSharedPreferences(BuildConfig.APPLICATION_ID, DC_SERVICE_DATAMANAGER_SP);
        return xsp.getString(KEY_PATH, EMPTY);
    }

}
