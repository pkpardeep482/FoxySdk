//package com.pardeep.foxy_dynamic.foxynativemodules.GoogleAuth;
//
//import com.facebook.react.bridge.ReadableMap;
//import com.facebook.react.bridge.WritableArray;
//import com.facebook.react.bridge.WritableMap;
//import com.facebook.react.bridge.WritableNativeArray;
//import com.facebook.react.bridge.WritableNativeMap;
//import com.facebook.react.bridge.Callback;
//import android.app.Activity;
//import android.content.Intent;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.FragmentActivity;
//import android.util.Log;
//import android.widget.Toast;
//import com.facebook.react.bridge.Promise;
//import com.facebook.react.bridge.ActivityEventListener;
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.bridge.ReactContextBaseJavaModule;
//import com.facebook.react.bridge.ReactMethod;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.Scope;
//import com.google.api.services.youtube.YouTubeScopes;
//import java.util.Iterator;
//import javax.annotation.Nonnull;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class GoogleAuthModule extends ReactContextBaseJavaModule implements ActivityEventListener {
//    Callback successClb;
//    Callback failureClb;
//    String clientId = "467636570447-965eqdgnb8a3s11s09gsis3ehukd1a1k.apps.googleusercontent.com";
//
//    public GoogleAuthModule(@Nonnull ReactApplicationContext reactContext) {
//        super(reactContext);
//        reactContext.addActivityEventListener(this);
//
//    }
//
//    @Nonnull
//    @Override
//    public String getName() {
//        return "googleSignIn";
//    }
//
//    @Override
//    public boolean hasConstants() {
//        return super.hasConstants();
//    }
//
//    @ReactMethod
//    public void initiateGoogleAuth(Callback successCallback, Callback failure) {
//
//        successClb = successCallback;
//        failureClb = failure;
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
//                .requestServerAuthCode(clientId).requestIdToken(clientId)
//                .requestScopes(new Scope(YouTubeScopes.YOUTUBE)).build();
//
//        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getReactApplicationContext())
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
//        mGoogleApiClient.connect();
//
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        getReactApplicationContext().startActivityForResult(signInIntent, 1000, null);
//
//    }
//
//    @Override
//    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == 1000) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()) {
//                // Signed in successfully, show authenticated UI.
//                GoogleSignInAccount acct = result.getSignInAccount();
//                String personName = acct.getDisplayName();
//                String personPhotoUrl = acct.getPhotoUrl().toString();
//                String email = acct.getEmail();
//                String id = acct.getId();
//                String getIdToken = acct.getIdToken();
//                String server_auth_code = acct.getServerAuthCode();
//
//                if (acct.getServerAuthCode() != null) {
//                    try {
//                        WritableMap map = new WritableNativeMap();
//                        map.putString("user_name", personName);
//                        map.putString("email", email);
//                        map.putString("id", id);
//                        map.putString("idToken", getIdToken);
//                        map.putString("profile_pic", personPhotoUrl);
//                        map.putString("server_auth_code", server_auth_code);
//                        successClb.invoke(map);
//                    } catch (Exception e) {
//                        Toast.makeText(getReactApplicationContext(), "Exceptions  " + e, Toast.LENGTH_LONG).show();
//
//                    }
//
//                } else {
//                    failureClb.invoke("001");
//
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onNewIntent(Intent intent) {
//
//    }
//
//}
