package com.pardeep.foxy_dynamic;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.hermes.reactexecutor.HermesExecutorFactory;
import com.facebook.react.PackageList;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.soloader.SoLoader;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.pardeep.foxy_dynamic.foxynativemodules.AlarmManagerNotifications.LocalNotificationsPackage;
import com.pardeep.foxy_dynamic.foxynativemodules.AppDetect.AppDetectPackage;
import com.pardeep.foxy_dynamic.foxynativemodules.GoogleAuth.GoogleAuthPackageHandler;
import com.pardeep.foxy_dynamic.foxynativemodules.InstallSource.InstallSourcePackage;
import com.pardeep.foxy_dynamic.foxynativemodules.NetworkInformation.NativeNetworkInfoPackage;
import com.pardeep.foxy_dynamic.foxynativemodules.NotificationsChannel.NotificationsChannelPackage;
import com.pardeep.foxy_dynamic.foxynativemodules.UploadManager.UploadManagerPackage;
import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferencesInfoPackage;
import com.pardeep.foxy_dynamic.foxynativemodules.cache.CacheManagerPackage;
import com.pardeep.foxy_dynamic.foxynativemodules.deviceinfo.DeviceInfoPackage;
import com.pardeep.foxy_dynamic.foxynativemodules.navbarcolor.NavBarColorPackage;
//import com.pardeep.foxy_dynamic.foxynativemodules.payment.PayuPaymentPackageHandler;
import com.reactnativecommunity.slider.ReactSliderPackage;

import java.util.List;

public class ModuleMainActivity  extends AppCompatActivity
{


    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoLoader.init(this, false);



        mReactRootView = new ReactRootView(this);
        List<ReactPackage> packages = new PackageList(getApplication()).getPackages();
        packages.add(new DeviceInfoPackage());
        packages.add(new ReactSliderPackage());

        packages.add(new AppDetectPackage());
        packages.add(new GoogleAuthPackageHandler());
        packages.add(new NativeNetworkInfoPackage());
        packages.add(new NavBarColorPackage());
//        packages.add(new PayuPaymentPackageHandler());

        packages.add(new UploadManagerPackage());
        packages.add(new InstallSourcePackage());
        packages.add(new UserPreferencesInfoPackage());
        packages.add(new CacheManagerPackage());
        packages.add(new LocalNotificationsPackage());
        packages.add(new NotificationsChannelPackage());

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setCurrentActivity(this)
                .setBundleAssetName("index_foxy.android.bundle")
                .setJSMainModulePath("index")
                .setJSBundleFile("assets://index_foxy.android.bundle")
                .addPackages(packages)
                .setJavaScriptExecutorFactory(new HermesExecutorFactory())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        Toast.makeText(this, "Executor is ="+mReactInstanceManager.getJsExecutorName(), Toast.LENGTH_SHORT).show();

        mReactRootView.startReactApplication(mReactInstanceManager, "FoxyApp", null);
        setContentView(mReactRootView);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        // Emulates installation of on demand modules using SplitCompat.
        SplitCompat.installActivity(this);
    }
}
