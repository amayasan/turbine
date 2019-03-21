package com.amayasan.ads.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.amayasan.ads.model.Ad;

import java.util.List;

public class AdsViewModel extends ViewModel {
    private List<Ad> ads;

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
