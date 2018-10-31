package dc.test.xposed.gl;

import dc.test.xposed.Constants;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author senrsl
 * @ClassName: TestGraphic
 * @Package: dc.test.xposed.gl
 * @CreateTime: 2018/10/31 下午7:26
 */
public class TestGraphic implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals(Constants.PKG_TEST))
            return;
        new GlUtil().OpenGLTest(loadPackageParam);
    }
}
