package com.example.trainer;

public class Settings {
    public static String PROD_DB_URI = "http://87.92.15.218:8081";
    public static String TEST_DB_URI = "http://87.92.15.218:8081";

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    public static String DB_URI = isJUnitTest()
            ? TEST_DB_URI
            : PROD_DB_URI;

}
