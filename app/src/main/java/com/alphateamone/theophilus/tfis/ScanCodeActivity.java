package com.alphateamone.theophilus.tfis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import cdflynn.android.library.checkview.CheckView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;
    private CheckView checkViewMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView    = new ZXingScannerView(this);
//        setContentView(R.layout.activity_scan_code);
        setContentView(zXingScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        zXingScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        Intent sentIntent = getIntent();
        sentIntent.putExtra("keyQrCode", result.getText());
        setResult(RESULT_OK, sentIntent);
        finish();
    }
}
