# NFCPresenceFix

LSPosed module to fix the NFC presence timeout of AOSP `NfcNci`. Requires an Android phone and https://github.com/LSPosed/LSPosed (e.g. via https://github.com/topjohnwu/Magisk and Zygisk).

**Important:** I am not responsible for any bricked smartphones or bootloops. Use at your own risk.

**This project is still very alpha and might crash your phone.**

## Explanation

See https://gitlab.com/LineageOS/issues/android/-/issues/7268 :

> In short, unless the `EXTRA_READER_PRESENCE_CHECK_DELAY` bundle configuration parameter in `NFCAdapter::enableReaderMode` is set to something high like `1000 ms`, the presented smartcard will be considered lost by the AOSP `NfcNci` implementation, even though the card is still busy performing computations.

Many apps do not implement this, as the default proprietary `NQNfcNci` implementation by NXP and Qualcomm die not have this issue. This LSPosed module patches selected apps and forces them to correctly set the Bundle parameter to one second.

More research at https://github.com/Governikus/AusweisApp/pull/52 .

Currently targeted apps:

- `com.governikus.ausweisapp2`
- `com.fidesmo.sec.android`
- `com.nect.app.prod`

However any other apps which use `NFCAdapter::enableReaderMode` should be patchable. Targeted apps have to be force-stopped before the module is applied after a fresh module installation.

## Logs

If everything works as intended, Logcat will display messages like this:

```
2024-06-20 14:09:45.754 32614-32614 LSPosed-Bridge          pid-32614                            I  Loading legacy module de.chrz.nfcpresencefix from /data/app/~~Jy37pL9n7r57U81hXmuK6w==/de.chrz.nfcpresencefix-f-Wof6gKROdt0NcogS6b0A==/base.apk
2024-06-20 14:09:45.795 32614-32614 LSPosed-Bridge          pid-32614                            I    Loading class de.chrz.nfcpresencefix.NFCHook
2024-06-20 14:09:45.859 32614-32614 LSPosed-Bridge          pid-32614                            I  I/NFCHook android.nfc.NfcAdapter found
2024-06-20 14:09:45.859 32614-32614 LSPosed-Bridge          pid-32614                            I  I/NFCHook enableReaderMode found
2024-06-20 14:09:46.393 32614-32648 LSPosed-Bridge          pid-32614                            I  I/NFCHook modified Bundle[{presence=1000}]
2024-06-20 14:09:46.488 32614-32648 LSPosed-Bridge          com.governikus.ausweisapp2           I  I/NFCHook modified Bundle[{presence=1000}]
```

The logs also show up in the LSPosed manager, via module logs.

## Download

You can download prebuilt APKs from the GitHub releases: https://github.com/StarGate01/NFCPresenceFix/releases .

## Screenshots

![LSPosed Module](https://github.com/StarGate01/NFCPresenceFix/blob/master/screenshots/signal-2024-06-20-160618_002.png?raw=true)

![LSPosed Module Apps](https://github.com/StarGate01/NFCPresenceFix/blob/master/screenshots/signal-2024-06-20-160618_003.png?raw=true)
