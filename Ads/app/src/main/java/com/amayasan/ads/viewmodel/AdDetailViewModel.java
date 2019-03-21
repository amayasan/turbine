package com.amayasan.ads.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.amayasan.ads.model.Ad;

public class AdDetailViewModel extends ViewModel {
    private Ad ad;

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
