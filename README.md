# NFCPresenceFix

LSPosed module to fix the NFC presence timeout of AOSP `NcfNci`. Requires a rooted Android phone and https://github.com/LSPosed/LSPosed .

See also https://gitlab.com/LineageOS/issues/android/-/issues/7268 :

> In short, unless the `EXTRA_READER_PRESENCE_CHECK_DELAY` bundle configuration parameter in `NFCAdapter::enableReaderMode` is set to something high like `1000 ms`, the presented smartcard will be considered lost by the AOSP `NfcNci` implementation, even though the card is still busy performing computations.

Many apps do not implement this, as the default proprietary `NQNfcNci` implementation by NXP and Qualcomm die not have this issue. This LSPosed module patches selected apps and forces them to correctly set the Bundle parameter to one second.

More research at https://github.com/Governikus/AusweisApp/pull/52 .

Currently targeted apps:

- `com.governikus.ausweisapp2`
- `com.fidesmo.sec.android`
- `com.nect.app.prod`

However any other apps which use `NFCAdapter::enableReaderMode` should be patchable.