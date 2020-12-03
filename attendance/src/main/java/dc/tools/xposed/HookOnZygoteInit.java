package dc.tools.xposed;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;

/**
 * 12-03 14:38:07.686 336-336/? I/Xposed:   Loading class dc.tools.xposed.HookOnZygoteInit
 * 12-03 14:38:07.699 336-336/? I/Xposed: HookOnZygoteInit hook on zygote init /data/app/dc.tools.attendance-2/base.apk
 * 12-03 14:38:07.700 336-336/? I/Xposed:   Loading class dc.tools.xposed.HookOnInit
 * Loading class dc.tools.xposed.HookLocation
 * 12-03 14:38:07.701 336-336/? I/Xposed:   Loading class dc.tools.xposed.HookCamera
 * 12-03 14:38:07.723 336-336/? I/LIBSEC: apply sec hook legacy
 * 12-03 14:38:10.292 1861-1861/system_process I/Xposed: HookCamera hook camera android
 * 12-03 14:38:10.303 1861-1861/system_process I/TEST: hook null 1 dc.tools.attendance 2 sp_dc_us 3 target 4
 *
 * @author senrsl
 * @ClassName: HookOnZygoteInit
 * @Package: dc.tools.xposed
 * @CreateTime: 2020/12/3 2:33 PM
 */
public class HookOnZygoteInit implements IXposedHookZygoteInit {

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

        XposedBridge.log(getClass().getSimpleName() + " hook on zygote init " + startupParam.modulePath);

    }

}
