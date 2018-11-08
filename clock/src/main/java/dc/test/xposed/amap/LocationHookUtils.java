package dc.test.xposed.amap;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import android.location.*;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.*;
import android.telephony.gsm.GsmCellLocation;

import dc.common.Logger;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 代码复制
 *
 * @author senrsl
 * @ClassName: HookUtils
 * @Package: dc.test.xposed.amap
 * @CreateTime: 2018/10/30 下午6:19
 */
public class LocationHookUtils {

    private ClassLoader classLoader;

    private double lat, lng;
    private int lac, cid;

    private String radioType;

    private int hni;
    private int mcc = 460, mnc = 1;
    private int sid = 0, pci = 300, psc = 300;

    private int wifiState = 1;

    private String mac = "00-00-00-00-00-00-00-00";
    private String wifiSsid = "SENRSL24";
    private String wifiMac = "00-00-00-00-00-00-00-00";

    private float locAccuracy = 100f, speed = 1.05f, bearing = 30.01f, altitude = 250.86f, bearingAcc = 30.8f, speedAcc = 20.0f, altitudeAcc = 80.0f;


    public LocationHookUtils(ClassLoader classLoader, double lat, double lng, int lac, int cid, String radioType, int hni) {
        this.classLoader = classLoader;
        this.lat = lat;
        this.lng = lng;
        this.lac = lac;
        this.cid = cid;
        this.radioType = radioType;
        this.hni = hni;
        init();
    }

    public LocationHookUtils(ClassLoader classLoader, double lat, double lng, int lac, int cid, String radioType, int hni, int sid) {
        this.classLoader = classLoader;
        this.lat = lat;
        this.lng = lng;
        this.lac = lac;
        this.cid = cid;
        this.radioType = radioType;
        this.hni = hni;
        this.sid = sid;
        init();
    }

    public LocationHookUtils(ClassLoader classLoader, double lat, double lng, int lac, int cid, String radioType, int hni, String mac, String wifiSsid, String wifiMac, float locAccuracy, int sid) {
        this.classLoader = classLoader;
        this.lat = lat;
        this.lng = lng;
        this.lac = lac;
        this.cid = cid;
        this.radioType = radioType;
        this.hni = hni;
        this.mac = mac;
        this.wifiSsid = wifiSsid;
        this.wifiMac = wifiMac;
        this.locAccuracy = locAccuracy;
        this.sid = sid;
        init();
    }

    private void init() {
        mcc = Integer.valueOf(String.valueOf(hni).substring(0, 3));
        mnc = Integer.valueOf(String.valueOf(hni).substring(3));
        pci = psc = sid;
    }


    public void hook() {

        Logger.w(toString());

        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getCellLocation", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                GsmCellLocation gsmCellLocation = new GsmCellLocation();
                gsmCellLocation.setLacAndCid(lac, cid);
                param.setResult(gsmCellLocation);
            }
        });

        XposedHelpers.findAndHookMethod("android.telephony.PhoneStateListener", classLoader, "onCellLocationChanged", CellLocation.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                GsmCellLocation gsmCellLocation = new GsmCellLocation();
                gsmCellLocation.setLacAndCid(lac, cid);
                param.setResult(gsmCellLocation);
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getPhoneCount", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(1);
                }
            });
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getNeighboringCellInfo", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(new ArrayList<>());
                }
            });
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", classLoader, "getAllCellInfo", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(getCell(mcc, mnc, lac, cid, sid, radioType));
                }
            });
            XposedHelpers.findAndHookMethod("android.telephony.PhoneStateListener", classLoader, "onCellInfoChanged", List.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(getCell(mcc, mnc, lac, cid, sid, radioType));
                }
            });
        }

        XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", classLoader, "getScanResults", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(new ArrayList<>());
            }
        });

        XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", classLoader, "getWifiState", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(wifiState);
            }
        });

        XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", classLoader, "isWifiEnabled", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });

        XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", classLoader, "getMacAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(mac);
            }
        });

        XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", classLoader, "getSSID", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(wifiSsid);
            }
        });

        XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", classLoader, "getBSSID", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(wifiMac);
            }
        });


        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", classLoader, "getTypeName", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult("WIFI");
            }
        });
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", classLoader, "isConnectedOrConnecting", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });

        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", classLoader, "isConnected", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });

        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", classLoader, "isAvailable", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });

        XposedHelpers.findAndHookMethod("android.telephony.CellInfo", classLoader, "isRegistered", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });

//        XposedHelpers.findAndHookMethod("android.location.Location", classLoader, "getSpeed", new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                param.setResult(1.05f);
//            }
//        });

        XposedHelpers.findAndHookMethod(LocationManager.class, "getLastLocation", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(getLastLocation());
            }
        });

        XposedHelpers.findAndHookMethod(LocationManager.class, "getLastKnownLocation", String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(getLastLocation());
            }
        });


        XposedBridge.hookAllMethods(LocationManager.class, "getProviders", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("gps");
                param.setResult(arrayList);
            }
        });

        XposedHelpers.findAndHookMethod(LocationManager.class, "getBestProvider", Criteria.class, Boolean.TYPE, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult("gps");
            }
        });

        XposedHelpers.findAndHookMethod(LocationManager.class, "addGpsStatusListener", GpsStatus.Listener.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (param.args[0] != null) {
                    XposedHelpers.callMethod(param.args[0], "onGpsStatusChanged", 1);
                    XposedHelpers.callMethod(param.args[0], "onGpsStatusChanged", 3);
                }
            }
        });

        XposedHelpers.findAndHookMethod(LocationManager.class, "addNmeaListener", GpsStatus.NmeaListener.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(false);
            }
        });

        XposedHelpers.findAndHookMethod("android.location.LocationManager", classLoader, "getGpsStatus", GpsStatus.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                GpsStatus gss = (GpsStatus) param.getResult();
                if (gss == null)
                    return;

                Class<?> clazz = GpsStatus.class;
                Method m = null;
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.getName().equals("setStatus")) {
                        if (method.getParameterTypes().length > 1) {
                            m = method;
                            break;
                        }
                    }
                }
                if (m == null)
                    return;

                //access the private setStatus function of GpsStatus
                m.setAccessible(true);

                //make the apps belive GPS works fine now
                int svCount = 10;
                int[] prns = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                float[] snrs = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                float[] elevations = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                float[] azimuths = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                int ephemerisMask = 0x1f;
                int almanacMask = 0x1f;

                //5 satellites are fixed
                int usedInFixMask = 0x1f;

                XposedHelpers.callMethod(gss, "setStatus", svCount, prns, snrs, elevations, azimuths, ephemerisMask, almanacMask, usedInFixMask);
                param.args[0] = gss;
                param.setResult(gss);
                try {
                    m.invoke(gss, svCount, prns, snrs, elevations, azimuths, ephemerisMask, almanacMask, usedInFixMask);
                    param.setResult(gss);
                } catch (Exception e) {
                    XposedBridge.log(e);
                }
            }
        });

        for (Method method : LocationManager.class.getDeclaredMethods()) {
            if (method.getName().equals("requestLocationUpdates") && !Modifier.isAbstract(method.getModifiers()) && Modifier.isPublic(method.getModifiers())) {
                XposedBridge.hookMethod(method, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if (param.args.length >= 4 && (param.args[3] instanceof LocationListener)) {

                            LocationListener ll = (LocationListener) param.args[3];

                            Class<?> clazz = LocationListener.class;
                            Method m = null;
                            for (Method method : clazz.getDeclaredMethods()) {
                                if (method.getName().equals("onLocationChanged") && !Modifier.isAbstract(method.getModifiers())) {
                                    m = method;
                                    break;
                                }
                            }
                            Location l = new Location(LocationManager.GPS_PROVIDER);
                            l.setLatitude(lac);
                            l.setLongitude(lng);
                            l.setAccuracy(locAccuracy);
                            l.setTime(System.currentTimeMillis());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                l.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                            }
                            XposedHelpers.callMethod(ll, "onLocationChanged", l);
                            try {
                                if (m != null) {
                                    m.invoke(ll, l);
                                }
                            } catch (Exception e) {
                                XposedBridge.log(e);
                            }
                        }
                    }
                });
            }

            if (method.getName().equals("requestSingleUpdate ") && !Modifier.isAbstract(method.getModifiers()) && Modifier.isPublic(method.getModifiers())) {
                XposedBridge.hookMethod(method, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if (param.args.length >= 3 && (param.args[1] instanceof LocationListener)) {

                            LocationListener ll = (LocationListener) param.args[3];

                            Class<?> clazz = LocationListener.class;
                            Method m = null;
                            for (Method method : clazz.getDeclaredMethods()) {
                                if (method.getName().equals("onLocationChanged") && !Modifier.isAbstract(method.getModifiers())) {
                                    m = method;
                                    break;
                                }
                            }

                            try {
                                if (m != null) {
                                    Location l = new Location(LocationManager.GPS_PROVIDER);
                                    l.setLatitude(lac);
                                    l.setLongitude(lng);
                                    l.setAccuracy(locAccuracy);
                                    l.setTime(System.currentTimeMillis());
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                        l.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                                    }
                                    m.invoke(ll, l);
                                }
                            } catch (Exception e) {
                                XposedBridge.log(e);
                            }
                        }
                    }
                });
            }
        }
    }


    public static final String TYPE_GSM = "GSM";
    public static final String TYPE_CDMA = "CDMA";
    public static final String TYPE_UMTS = "UMTS";
    public static final String TYPE_LTE = "LTE";


    //networkType 取 TelephonyManager.NETWORK_TYPE_GSM
    private ArrayList getCell(int mcc, int mnc, int lac, int cid, int sid, String networkType) {
        ArrayList arrayList = new ArrayList();

        if (TYPE_LTE.equals(networkType)) {
            CellInfoLte cellInfoLte = (CellInfoLte) XposedHelpers.newInstance(CellInfoLte.class);
            XposedHelpers.callMethod(cellInfoLte, "setCellIdentity", XposedHelpers.newInstance(CellIdentityLte.class,
                    new Object[]{Integer.valueOf(mcc), Integer.valueOf(mnc), Integer.valueOf(cid), Integer.valueOf(pci), Integer.valueOf(lac)}));

            arrayList.add(cellInfoLte);
        } else if (TYPE_UMTS.equals(networkType)) {
            CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) XposedHelpers.newInstance(CellInfoWcdma.class);
            XposedHelpers.callMethod(cellInfoWcdma, "setCellIdentity", XposedHelpers.newInstance(CellIdentityWcdma.class,
                    new Object[]{Integer.valueOf(mcc), Integer.valueOf(mnc), Integer.valueOf(lac), Integer.valueOf(cid), Integer.valueOf(psc)}));

            arrayList.add(cellInfoWcdma);
        } else if (TYPE_CDMA.equals(networkType)) {
            CellInfoCdma cellInfoCdma = (CellInfoCdma) XposedHelpers.newInstance(CellInfoCdma.class);
            XposedHelpers.callMethod(cellInfoCdma, "setCellIdentity", XposedHelpers.newInstance(CellIdentityCdma.class,
                    new Object[]{Integer.valueOf(lac), Integer.valueOf(sid), Integer.valueOf(cid), Integer.valueOf((int) lng * 10000), Integer.valueOf((int) lat * 10000)}));

            arrayList.add(cellInfoCdma);
        } else {
            CellInfoGsm cellInfoGsm = (CellInfoGsm) XposedHelpers.newInstance(CellInfoGsm.class);
            XposedHelpers.callMethod(cellInfoGsm, "setCellIdentity", XposedHelpers.newInstance(CellIdentityGsm.class,
                    new Object[]{Integer.valueOf(mcc), Integer.valueOf(mnc), Integer.valueOf(lac), Integer.valueOf(cid)}));
            arrayList.add(cellInfoGsm);
        }

        return arrayList;
    }

    private Location getLastLocation() {
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        loc.setLatitude(lac);
        loc.setLongitude(lng);
        loc.setAccuracy(locAccuracy);
        loc.setTime(System.currentTimeMillis());
        loc.setSpeed(speed);
        loc.setBearing(bearing);//角度
        loc.setAltitude(altitude);//高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loc.setBearingAccuracyDegrees(bearingAcc);//角度精度
            loc.setSpeedAccuracyMetersPerSecond(speedAcc);//速度精度
            loc.setVerticalAccuracyMeters(altitudeAcc);//高度精度
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            loc.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        return loc;
    }

    @Override
    public String toString() {
        return "LocationHookUtils{" +
                "classLoader=" + classLoader +
                ", lat=" + lat +
                ", lng=" + lng +
                ", lac=" + lac +
                ", cid=" + cid +
                ", radioType='" + radioType + '\'' +
                ", hni=" + hni +
                ", mcc=" + mcc +
                ", mnc=" + mnc +
                ", sid=" + sid +
                ", wifiState=" + wifiState +
                ", mac='" + mac + '\'' +
                ", wifiSsid='" + wifiSsid + '\'' +
                ", wifiMac='" + wifiMac + '\'' +
                ", locAccuracy=" + locAccuracy +
                ", pci=" + pci +
                ", psc=" + psc +
                '}';
    }


}
