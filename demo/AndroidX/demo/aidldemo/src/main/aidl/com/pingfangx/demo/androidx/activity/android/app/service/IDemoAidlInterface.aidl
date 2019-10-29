// IDemoAidlInterface.aidl
package com.pingfangx.demo.androidx.activity.android.app.service;

// Declare any non-default types here with import statements

interface IDemoAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    /**
     * 计算
     */
    int calculate(int a,int b);
}
