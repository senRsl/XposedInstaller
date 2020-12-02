package dc.tools.xposed;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author senrsl
 * @ClassName: HookLocation
 * @Package: dc.tools.xposed
 * @CreateTime: 2020/11/30 6:01 PM
 */
public class HookLocation implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        //Logger.w(getClass().getSimpleName(),"hook camera ",loadPackageParam.packageName);
        //XposedBridge.log(getClass().getSimpleName() + " hook camera " + loadPackageParam.packageName + " - " + XposedUtils.isNeedHook(loadPackageParam.packageName));

//        if (!XposedUtils.isNeedHook(loadPackageParam.packageName)) return;
//
//        XposedBridge.log(getClass().getSimpleName() + "  hook location on " + loadPackageParam.packageName);
    }


}
