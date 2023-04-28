//package com.pardeep.foxy_dynamic.foxynativemodules.GoogleAuth;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.Scope;
//import com.google.api.services.youtube.YouTubeScopes;
//
//import com.google.api.services.youtube.model.*;
//
//import android.Manifest;
//import android.accounts.AccountManager;
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.text.method.ScrollingMovementMethod;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
//public class AuthHandler extends AppCompatActivity {
//    private Button mCallApiButton;
////    GoogleApiClient    mGoogleApiClient;
//    private static final String BUTTON_TEXT = "Call YouTube Data API";
//    private static final String[] SCOPES = { YouTubeScopes.YOUTUBE_READONLY,YouTubeScopes.YOUTUBE };
//
//    /**
//     * Create the main activity.
//     * @param savedInstanceState previously saved instance data.
//     */
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail().requestScopes(new Scope(YouTubeScopes.YOUTUBE))
//                .build();
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//                    }
//                })
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        // Initialize credentials and service object.
//        mGoogleApiClient.connect();
//
//        initaiteGoogleAuth();
//
//    }
//
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == 1000) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//
//            handleSignInResult(result);
//        }
//    }
//    private void handleSignInResult(GoogleSignInResult result) {
//
//        Toast.makeText(getApplicationContext(),"values"+result.isSuccess(),Toast.LENGTH_LONG).show();
//        Log.e("wrfd", "handleSignInResult:" + result.isSuccess());
//        if (result.isSuccess()) {
//            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount acct = result.getSignInAccount();
//
//            Log.e("zfdsfds", "display name: " + acct.getDisplayName());
//
//            String personName = acct.getDisplayName();
//            String personPhotoUrl = acct.getPhotoUrl().toString();
//            String email = acct.getEmail();
//
//            Log.e("TAG", "Name: " + personName + ", email: " + email
//                    + ", Image: " + personPhotoUrl);
//
////            Toast.makeText(getApplicationContext(),"values"+result.isSuccess() +"Name: " + personName + ", email: " + email
////                    + ", Image: " + personPhotoUrl +"Token "+acct.getServerAuthCode(),Toast.LENGTH_LONG).show();
//
//
//
//            Intent intent=new Intent();
//            intent.putExtra("token",acct.getServerAuthCode());
//            setResult(RESULT_OK,intent);
//            finish();
//
//
//
////            finish();
//        }
//    }
//
//
//    public void initaiteGoogleAuth () {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, 1000);
//    }
//
//}