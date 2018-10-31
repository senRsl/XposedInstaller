package dc.test.xposed.gl;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author senrsl
 * @ClassName: GlUtil
 * @Package: dc.test.xposed.gl
 * @CreateTime: 2018/10/31 下午7:58
 */
public class GlUtil {
    public void OpenGLTest(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("com.google.android.gles_jni.GLImpl", loadPkgParam.classLoader, "glGetString", Integer.TYPE, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(MethodHookParam param)
                        throws Throwable {
                    // TODO Auto-generated method stub
                    //super.beforeHookedMethod(param);
                    if (param.args[0] != null) {
                        if (param.args[0].equals(Integer.valueOf(7936))) {
                            param.setResult("GLVendor啊");
                        }
                        if (param.args[0].equals(Integer.valueOf(7937))) {
                            param.setResult("GLRend啊erer");
                        }
                    }
                }

            });
        } catch (Exception e) {
            XposedBridge.log("HOOK GPU 失败" + e.getMessage());
        }
    }
}
