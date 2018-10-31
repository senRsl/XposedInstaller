package dc.test.xposed.screen;


import android.util.DisplayMetrics;
import android.view.Display;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

/**
 * @author senrsl
 * @ClassName: ScreenUtil
 * @Package: dc.test.xposed.screen
 * @CreateTime: 2018/10/31 下午7:53
 */
public class ScreenUtil {
    /*
            屏幕相关
     */
    public void Display(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    // TODO Auto-generated method stub
                    super.afterHookedMethod(param);
                    final int dpi = tryParseInt("999");
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.densityDpi = dpi;

                }

            });
        } catch (Exception e) {
            XposedBridge.log("Fake DPI ERROR: " + e.getMessage());
        }

        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getRealMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    // TODO Auto-generated method stub
                    super.afterHookedMethod(param);
                    final int dpi = 333;
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.densityDpi = dpi;

                }

            });
        } catch (Exception e) {

        }


        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    // TODO Auto-generated method stub
                    super.afterHookedMethod(param);
                    final float sdensity = 100.2f;
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.density = sdensity;

                }

            });
        } catch (Exception e) {

        }


        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    // TODO Auto-generated method stub
                    super.afterHookedMethod(param);
                    final float sxdpi = 300.6f;
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.xdpi = sxdpi;

                }

            });
        } catch (Exception e) {
            XposedBridge.log("Fake Real DPI ERROR: " + e.getMessage());
        }


        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    // TODO Auto-generated method stub
                    super.afterHookedMethod(param);
                    final float sydpi = 400.1f;
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.ydpi = sydpi;

                }

            });
        } catch (Exception e) {

        }


        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    // TODO Auto-generated method stub
                    super.afterHookedMethod(param);
                    final float scdensity = 300.1f;
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.scaledDensity = scdensity;

                }

            });


        } catch (Exception e) {

        }

        //  已废弃的修改屏幕信息
        XposedHelpers.findAndHookMethod("android.view.Display",
                loadPkgParam.classLoader, "getWidth", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        // TODO Auto-generated method stub
                        super.afterHookedMethod(param);

                        param.setResult(600);
                    }
                });

        XposedHelpers.findAndHookMethod("android.view.Display",
                loadPkgParam.classLoader, "getHeight", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        // TODO Auto-generated method stub
                        super.afterHookedMethod(param);

                        param.setResult(300);
                    }
                });


        // 宽
        XposedHelpers.findAndHookMethod(Display.class, "getMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                final int zhenwidth = 700;
                DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                metrics.widthPixels = zhenwidth;

            }
        });
        // 高
        XposedHelpers.findAndHookMethod(Display.class, "getMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                final int zhenheight = 800;
                DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                metrics.heightPixels = zhenheight;

            }
        });

    }


    private static int tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 320;
        }
    }

    private static float tryParsefloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return (float) 480.0;
        }
    }
}
