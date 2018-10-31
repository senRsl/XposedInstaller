package dc.test.xposed.cpu;

import dc.test.xposed.Constants;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author senrsl
 * @ClassName: TestCpu
 * @Package: dc.test.xposed.cpu
 * @CreateTime: 2018/10/31 下午7:25
 */
public class TestCpu implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals(Constants.PKG_TEST))
            return;
        new CpuUtil(loadPackageParam);
    }
}
