package dc.test.xposed.clock;

import android.graphics.Color;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * @author senrsl
 * @ClassName: TestClock
 * @Package: dc.test.xposed.clock
 * @CreateTime: 2018/10/29 下午4:48
 */
public class TestClock implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.android.systemui"))
            return;

        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", loadPackageParam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                tv.setText(text + " :)");
                tv.setTextColor(Color.RED);
            }
        });


        findAndHookMethod("com.android.keyguard.CarrierText", loadPackageParam.classLoader, "updateCarrierText", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

                TextView mLabel = (TextView) param.thisObject;
                mLabel.setText("大中华局域网");
                XposedBridge.log("Changing Carrier Done");
            }
        });

    }
}
