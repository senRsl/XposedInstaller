package dc.test.xposed.amap;

import android.location.Location;

import dc.test.xposed.Constants;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 测试 高德地图 跟微信 都可以
 * 但是写的 简单的套壳反劫持就取不到了，这么简单的写法为什么微信跟高德不用？奇怪
 *
 * @author senrsl
 * @ClassName: TestAmap
 * @Package: dc.test.xposed.amap
 * @CreateTime: 2018/10/30 下午5:51
 */
public class TestAmap implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        XposedBridge.log("加载包: " + loadPackageParam.packageName);

        if (loadPackageParam.packageName.equals("com.autonavi.minimap") || loadPackageParam.packageName.equals("com.tencent.mm")
                || loadPackageParam.packageName.equals("dc.test.xposed.anti") || loadPackageParam.packageName.equals("dc.test.xposed.anti.debug")
                || loadPackageParam.packageName.equals(Constants.PKG_TEST)) {

            XposedBridge.log("进入" + loadPackageParam.packageName);


//            HookUtils.HookAndChange(loadPackageParam.classLoader, 33.89449, -118.214362, 16330, 22277890);

//            new LocationHookUtils(loadPackageParam.classLoader, 39.91539, 116.405228, 4213, 1473, LocationHookUtils.TYPE_GSM, 46001, 300).hook(); //王府井
//            new LocationHookUtils(loadPackageParam.classLoader, 37.776868, -122.432061, 14450, 44909059, LocationHookUtils.TYPE_LTE, 310260,300).hook();//旧金山
//            new LocationHookUtils(loadPackageParam.classLoader, 38.894684, -77.037576, 7997, 160434770, LocationHookUtils.TYPE_UMTS, 310410, 300).hook();//华盛顿
//            new LocationHookUtils(loadPackageParam.classLoader, 35.170669, 136.884841, 24579, 5996547, LocationHookUtils.TYPE_LTE, 44020, 300).hook();//名古屋 微信无效
//            new LocationHookUtils(loadPackageParam.classLoader, 35.157623, 136.904068, 41479, 70696704, LocationHookUtils.TYPE_LTE, 44051, 300).hook();//名古屋 微信无效
            new LocationHookUtils(loadPackageParam.classLoader, 35.184219, 136.895067, 16390, 67131648, LocationHookUtils.TYPE_LTE, 44010, 300).hook();//名古屋 这个就可以了，但我这个地图却没有这个基站。。。。
//            new LocationHookUtils(loadPackageParam.classLoader, 48.85987, 2.327022, 29953, 52062, LocationHookUtils.TYPE_GSM, 20801, 300).hook();//巴黎 微信无效

        }
    }

    //如果只有gps定位一种方式，这个应该是可行的
    private void setHookMethodAndClassGaoDe() {
        XposedHelpers.findAndHookMethod(Location.class, "getLongitude", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("进入，开始修改经度坐标");
                param.setResult(113.9501953125);
            }
        });
        XposedHelpers.findAndHookMethod(Location.class, "getLatitude", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("进入，开始修改纬度坐标");

                param.setResult(22.6342926938);
            }
        });
    }


}
