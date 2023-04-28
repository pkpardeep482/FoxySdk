//package com.pardeep.foxy_dynamic.foxynativemodules.payment;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//import android.view.View;
//
//
//
//
//public class PaymentWebView extends AppCompatActivity {
//    WebView webView;
//    ProgressBar progressBar;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.webview);
//        webView=(WebView)findViewById(R.id.paymentWebview);
//        progressBar = findViewById(R.id.webviewProgressBar);
//        Bundle bundle=getIntent().getExtras();
//        PayuConfig payuConfig= bundle.getParcelable(PayuConstants.PAYU_CONFIG);
//        String url = payuConfig.getEnvironment() == PayuConstants.PRODUCTION_ENV ? PayuConstants.PRODUCTION_PAYMENT_URL : PayuConstants.TEST_PAYMENT_URL;
//        byte[] encodedData = payuConfig.getData().getBytes();
//        webView.postUrl(url,encodedData);
//        webView.getSettings().setSupportMultipleWindows(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient(){});
//        PayuJavascriptInterface javascriptInterface =new PayuJavascriptInterface();
//        webView.addJavascriptInterface(javascriptInterface,"Android");
//        String surl = bundle.getString("surl");
//        String furl = bundle.getString("furl");
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                 progressBar.setVisibility(View.GONE);
//                if(url.equals(surl) || url.equals(furl))
//                {
//                     webView.loadUrl("javascript:Android.getPayUData(PayU());");
//                }
//            }
//        });
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        AlertDialog alertDialog = new AlertDialog.Builder(this)
//                //set title
//                .setTitle("Are you sure you want to cancel the transaction?")
//                //set positive button
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //set what would happen when positive button is clicked
//                        //make new intent
//                        //setresult
//
//                        Intent intent = new Intent();
//                        String backResponse = "{\"code\":\"cancelled\"}";
//                        intent.putExtra("payuResponse",backResponse);
//                        setResult(PayuAndroidConstants.RESULT_GO_BACK,intent);
//                        finish();
//                    }
//                })
//                //set negative button
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //set what should happen when negative button is clicked
//                        //Toast.makeText(getApplicationContext(),"Nothing happenedd", Toast.LENGTH_LONG).show();
//
//                    }
//                })
//                .show();
//    }
//
//   class PayuJavascriptInterface {
//        @JavascriptInterface
//       public void getPayUData(String response){
//            Intent intent = new Intent();
//            intent.putExtra("payuResponse",response);
//            setResult(PayuAndroidConstants.RESULT_PAYMENT_SUCCESS,intent);
//            finish();
//        }
//   }
//
//}
