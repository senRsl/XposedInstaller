package dc.test.xposed.amap;

import android.location.Location;

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

        if (loadPackageParam.packageName.equals("com.autonavi.minimap") | loadPackageParam.packageName.equals("com.tencent.mm")
                || loadPackageParam.packageName.equals("dc.test.xposed.anti") || loadPackageParam.packageName.equals("dc.test.xposed.anti.debug")) {

            XposedBridge.log("进入" + loadPackageParam.packageName);


            HookUtils.HookAndChange(loadPackageParam.classLoader, 33.89449, -118.214362, 16330, 22277890);

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

//    /*MCC: 310
//    MNC: 260
//    LAC: 16330
//    CID: 22277890
//    Radio Type: UMTS
//
//    Latitude: 33.89449
//    Longitude: -118.214362
//    Range: 1000 m
//
//7 measurements
//    Created: 2015-04-21T22:35:01.000Z
//    Updated: 2016-08-20T20:36:35.000Z*/


//    谷歌地图：22.6342875809,113.9501827029
//    百度地图：22.6400537426,113.9567384019
//    腾讯高德：22.6342926938,113.9501953125
//    图吧地图：22.6351332138,113.9421110825
//    谷歌地球：22.6372832138,113.9453010825
//    北纬N22°38′14.22″ 东经E113°56′43.08″
//
//    靠近：中国广东省深圳市南山区
//    周边：佳和购物广场 约39米
//    参考：广东省深圳市南山区西丽街道白芒社区东北方向约1.14公里

}
