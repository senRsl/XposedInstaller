package dc.test.xposed.isroot;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author senrsl
 * @ClassName: TestRoot
 * @Package: dc.test.xposed.isroot
 * @CreateTime: 2018/10/31 下午6:50
 */
public class TestRoot implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        new RootCloakUtils().handleLoadPackage(loadPackageParam);
    }
}
