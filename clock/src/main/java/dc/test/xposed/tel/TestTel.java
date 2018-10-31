package dc.test.xposed.tel;

import dc.test.xposed.Constants;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author senrsl
 * @ClassName: TestTel
 * @Package: dc.test.xposed.tel
 * @CreateTime: 2018/10/31 下午7:27
 */
public class TestTel implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals(Constants.PKG_TEST))
            return;
        new PhoneUtil(loadPackageParam);
    }
}
