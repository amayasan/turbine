package com.amayasan.ads.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amayasan.ads.fragment.AdsFragment;
import com.amayasan.ads.R;

// The AdsActivity is the Main Activity in the app
// It displays the AdsFragment and handles navigation via onBackPressed()
public class AdsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AdsFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
