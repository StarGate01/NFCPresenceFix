package de.chrz.nfcpresencefix;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import java.lang.reflect.Method;


public class NFCHook implements IXposedHookLoadPackage
{
    public static final String TAG = "NFCHook";

    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable
    {
        Class<?> pmService = XposedHelpers.findClassIfExists("android.nfc.NfcAdapter", lpparam.classLoader);
        if (pmService != null) {
            XposedBridge.log("I/" + TAG + " android.nfc.NfcAdapter found");
            Method enableReaderMode = XposedHelpers.findMethodExactIfExists(pmService, "enableReaderMode",
                Activity.class, NfcAdapter.ReaderCallback.class, int.class, Bundle.class);
            if (enableReaderMode != null) {
                XposedBridge.log("I/" + TAG + " enableReaderMode found");
                XposedBridge.hookMethod(enableReaderMode, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if(param.args[3] == null) param.args[3] = new Bundle();
                        ((Bundle)(param.args[3])).putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 1000);
                        XposedBridge.log("I/" + TAG + " modified " + param.args[3].toString());
                    }

                });
            } else {
                XposedBridge.log("E/" + TAG + " enableReaderMode not found");
            }
        } else {
            XposedBridge.log("E/" + TAG + " android.nfc.NfcAdapter not found");
        }
    }

}