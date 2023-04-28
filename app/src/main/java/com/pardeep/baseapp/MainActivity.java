package com.pardeep.baseapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;

import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.android.play.core.splitinstall.SplitInstallHelper;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity
{
    Button button;
    SplitInstallManager splitInstallManager = null;
    private ProgressDialog dialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);


        splitInstallManager = SplitInstallManagerFactory.create(MainActivity.this);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDynamicModuleAlreadyInstalled();
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        // Emulates installation of future on demand modules using SplitCompat.
        // Necessary to access AltAccoActivity once ingornaltacco module is downloaded
        // Docs: https://developer.android.com/guide/playcore/feature-delivery/on-demand#access_downloaded_modules
        SplitCompat.installActivity(this);
    }


    private void isDynamicModuleAlreadyInstalled() {
        boolean isModuleAlreadyInstalled = splitInstallManager.getInstalledModules().contains("foxy_dynamic");

        Log.e("Is bundle installed", ""+isModuleAlreadyInstalled);
        if(!isModuleAlreadyInstalled) {
            dialog.setTitle("Downloading host app dynamically");
            dialog.show();
            getAppBundle();
        } else {
            SplitInstallHelper.updateAppInfo(this);
            Intent intent = new Intent();
            intent.setClassName(BuildConfig.APPLICATION_ID, "com.pardeep.foxy_dynamic.ModuleMainActivity");
            startActivity(intent);
        }

    }

    int mySessionId = 0;
    SplitInstallStateUpdatedListener listener = state -> {
        switch (state.status()) {
            case SplitInstallSessionStatus.DOWNLOADING:
                long totalKiloBytes = state.totalBytesToDownload() / 1024;
                long kiloBytesDownloaded = state.bytesDownloaded() / 1024;
                long progressPercentage = kiloBytesDownloaded * 100 / totalKiloBytes;
                // Update progress bar.
                String progressMessage = String.format("Total size: %dKB, Progress: %d%%", totalKiloBytes, progressPercentage);
                System.out.println("totalKiloBytes: " + totalKiloBytes + ", kiloBytesDownloaded: " + kiloBytesDownloaded);
                System.out.println(progressMessage);
                dialog.setMessage(progressMessage);
                break;

            case SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION:
                // Displays a confirmation for the user to confirm the request.
                try {
                    splitInstallManager.startConfirmationDialogForResult(
                            state,
                            /* activity = */ this,
                            // You use this request code to later retrieve the user's decision.
                            /* requestCode = */ 0);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;

            case SplitInstallSessionStatus.INSTALLED:
                // After a module is installed, you can start accessing its content or
                // fire an intent to start an activity in the installed module.
                // For other use cases, see access code and resources from installed modules.

                // If the request is an on demand module for an Android Instant App
                // running on Android 8.0 (API level 26) or higher, you need to
                // update the app context using the SplitInstallHelper API.
                dialog.setMessage("Installed");
                dialog.dismiss();
                SplitInstallHelper.updateAppInfo(this);


                break;
            case SplitInstallSessionStatus.DOWNLOADED:
                System.out.println("Download successful");
                dialog.setMessage("Download successful");

                break;
            case SplitInstallSessionStatus.FAILED:
                System.out.println("Failed to download host app, error code:  " + state.errorCode());
                dialog.setMessage("Failed to download host app, error code: " + state.errorCode());
                break;

            case SplitInstallSessionStatus.PENDING:
                System.out.println("Waiting for the download to begin");
                dialog.setMessage("Waiting for the download to begin");
                break;

            case SplitInstallSessionStatus.INSTALLING:
                System.out.println("Installing host app...");
                dialog.setMessage("Installing host app...");
                break;

            default:
                dialog.setMessage("Failed to download, unknown error occurred. Status code: " + state.status() + ", Error code: " + state.errorCode());
        }
    };

// Registers the listener.


    public void getAppBundle(){
        SplitInstallRequest request = SplitInstallRequest.newBuilder()
                .addModule("foxy_dynamic")
                .build();


        splitInstallManager.registerListener(listener);

        splitInstallManager.startInstall(request).addOnCompleteListener(new OnCompleteListener<Integer>() {
            @Override
            public void onComplete(@NonNull Task<Integer> task) {

                Log.e("onComplete", "Completed = "+task.isComplete() + ", Success = "+task.isSuccessful());
            }
        }).addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer integer) {

                Log.e("onSuccess", ""+integer);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

            }
        });
    }


}
