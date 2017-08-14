package com.ranieri.bodegaweb.connection;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import static java.security.AccessController.getContext;

/**
 * Created by ranie on 13 de ago.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    // This is called each time a sync is attempted; this is okay, since the
    // overhead is negligible if the security provider is up-to-date.
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        try {
            ProviderInstaller.installIfNeeded(getContext());
        } catch (GooglePlayServicesRepairableException e) {

            // Indicates that Google Play services is out of date, disabled, etc.

            // Prompt the user to install/update/enable Google Play services.
            GooglePlayServicesUtil.showErrorNotification(
                    e.getConnectionStatusCode(), getContext());

            // Notify the SyncManager that a soft error occurred.
            //syncResult.stats.numIOExceptions++;
            return;

        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates a non-recoverable error; the ProviderInstaller is not able
            // to install an up-to-date Provider.

            // Notify the SyncManager that a hard error occurred.
            syncResult.stats.numAuthExceptions++;
            return;
        }

        // If this is reached, you know that the provider was already up-to-date,
        // or was successfully updated.
    }

    public void onPerformSync() {
        String errorMsg = "";
        try {
            ProviderInstaller.installIfNeeded(getContext());
            errorMsg = "GooglePlayServices Ok";
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil.showErrorNotification(
                    e.getConnectionStatusCode(), getContext());
            errorMsg = "GooglePlayServices Desatualizada";
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            errorMsg = "GooglePlayServices Não disponível";
            e.printStackTrace();
        } finally {
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
        }
    }
}
