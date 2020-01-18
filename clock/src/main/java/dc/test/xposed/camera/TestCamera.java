package dc.test.xposed.camera;

import dc.test.xposed.Constants;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * @author senrsl
 * @ClassName: TestCamera
 * @Package: dc.test.xposed.camera
 * @CreateTime: 2020/1/18 8:28 PM
 */
public class TestCamera implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals(Constants.PKG_TEST))
            return;

        XposedHelpers.findAndHookMethod(
                "android.net.Uri", loadPackageParam.classLoader, "fromFile", "java.io.File", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log("TestCamera Uri：" + param.args[0]);
                        //param.setResult("MAC啊");
                    }

                });
    }

//    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
//        XposedBridge.log("TestCamera Loaded app: " + lpparam.packageName);
//
//        if (!lpparam.packageName.equals(Constants.PKG_TEST))
//            return;
//
//        XposedBridge.log("TestCamera CamHook: We're here!");
//
//        //somepackage.somewhere.app.NewPhotoFragment$3$1
//        findAndHookMethod("com.tencent.wework.msg.controller.CustomCameraActivity", lpparam.classLoader, "onPictureTaken", "byte[]", "android.hardware.Camera", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("CamHook: before picture taken");
//                XposedBridge.log("CamHook: byteLength " + ((byte[]) (param.args)[0]).length);
//                byte[] bitmapdata = ((byte[]) (param.args)[0]);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
//                XposedBridge.log("CamHook: StorageState: " + Environment.getExternalStorageState());
//                XposedBridge.log("CamHook: StoragePath: " + Environment.getExternalStorageDirectory().getAbsolutePath());
//                XposedBridge.log("CamHook: StoragePathEnv: " + System.getenv("EXTERNAL_STORAGE"));
//                //Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/input.jpg");
//
//                XposedBridge.log("CamHook: Bitmap is null: " + (bitmap == null));
//
//                bitmap = toGrayscale(bitmap);
//
//                ByteArrayOutputStream blob = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, blob);
//                param.args[0] = blob.toByteArray();
//            }
//            /*@Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                // this will be called after the clock was updated by the original method
//            }*/
//        });
//    }
//
//    public Bitmap toGrayscale(Bitmap bmpOriginal) {
//        int width, height;
//        height = bmpOriginal.getHeight();
//        width = bmpOriginal.getWidth();
//
//        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bmpGrayscale);
//        Paint paint = new Paint();
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0);
//        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
//        paint.setColorFilter(f);
//        c.drawBitmap(bmpOriginal, 0, 0, paint);
//        return bmpGrayscale;
//    }

}
