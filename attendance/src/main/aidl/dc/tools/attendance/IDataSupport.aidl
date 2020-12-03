// IDataSupport.aidl
package dc.tools.attendance;

// Declare any non-default types here with import statements

//服务端
interface IDataSupport {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    String test(String message);
}
