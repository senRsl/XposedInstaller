package dc.tools.xposed;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

/**
 * @author senrsl
 * @ClassName: HookOnInit
 * @Package: dc.tools.xposed
 * @CreateTime: 2020/12/3 2:32 PM
 */
public class HookOnInit implements IXposedHookInitPackageResources {

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {

        //XposedBridge.log(getClass().getSimpleName() + " hook on init " + initPackageResourcesParam.packageName);

    }

}
