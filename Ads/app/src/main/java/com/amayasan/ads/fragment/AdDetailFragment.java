package com.amayasan.ads.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amayasan.ads.model.Ad;
import com.amayasan.ads.viewmodel.AdDetailViewModel;
import com.amayasan.ads.R;
import com.squareup.picasso.Picasso;

// The AdDetailFragment receives a Ad object via arguments in the Bundle
// and displays all the relevant information in a TableLayout
public class AdDetailFragment extends Fragment {
    private static final String AD_KEY = "ad";

    private AdDetailViewModel mViewModel;

    private ImageView vProductThumbnail;
    private TextView vProductName;
    private TextView vProductDescription;
    private TextView vProductId;
    private TextView vAppId;
    private TextView vAppPrivacyPolicyUrl;
    private TextView vAverageRatingImageUrl;
    private TextView vBidRate;
    private TextView vBillingTypeId;
    private TextView vCallToAction;
    private TextView vCampaignDisplayOrder;
    private TextView vCampaignId;
    private TextView vCampaignTypeId;
    private TextView vCategoryName;
    private TextView vClickProxyUrl;
    private TextView vCreativeId;
    private TextView vHomeScreen;
    private TextView vMinOSVersion;
    private TextView vImpressionTrackingUrl;
    private TextView vIsRandomPick;
    private TextView vNumberOfRatings;
    private TextView vRating;

    public static AdDetailFragment newInstance(Ad ad) {
        AdDetailFragment adDetailFragment = new AdDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(AD_KEY, ad);
        adDetailFragment.setArguments(bundle);

        return adDetailFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ad_detail_fragment, container, false);

        vProductThumbnail = view.findViewById(R.id.vProductThumbnail);
        vProductName = view.findViewById(R.id.vProductName);
        vProductDescription = view.findViewById(R.id.vProductDescription);
        vProductId = view.findViewById(R.id.vProductId);
        vAppId = view.findViewById(R.id.vAppId);
        vAppPrivacyPolicyUrl = view.findViewById(R.id.vAppPrivacyPolicyUrl);
        vAverageRatingImageUrl = view.findViewById(R.id.vAverageRatingImageUrl);
        vBidRate = view.findViewById(R.id.vBidRate);
        vBillingTypeId = view.findViewById(R.id.vBillingTypeId);
        vCallToAction = view.findViewById(R.id.vCallToAction);
        vCampaignDisplayOrder = view.findViewById(R.id.vCampaignDisplayOrder);
        vCampaignId = view.findViewById(R.id.vCampaignId);
        vCampaignTypeId = view.findViewById(R.id.vCampaignTypeId);
        vCategoryName = view.findViewById(R.id.vCategoryName);
        vClickProxyUrl = view.findViewById(R.id.vClickProxyUrl);
        vCreativeId = view.findViewById(R.id.vCreativeId);
        vHomeScreen = view.findViewById(R.id.vHomeScreen);
        vMinOSVersion = view.findViewById(R.id.vMinOSVersion);
        vImpressionTrackingUrl = view.findViewById(R.id.vImpressionTrackingUrl);
        vIsRandomPick = view.findViewById(R.id.vIsRandomPick);
        vNumberOfRatings = view.findViewById(R.id.vNumberOfRatings);
        vRating = view.findViewById(R.id.vRating);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdDetailViewModel.class);
        mViewModel.setAd((Ad) getArguments().getSerializable(AD_KEY));
    }

    @Override
    public void onResume() {
        super.onResume();

        populateData();
    }

    private void populateData() {
        Picasso.get().load(mViewModel.getAd().getProductThumbnail()).into(vProductThumbnail);
        vProductName.setText(mViewModel.getAd().getProductName());
        vProductDescription.setText(mViewModel.getAd().getProductDescription());
        vProductId.setText(Long.toString(mViewModel.getAd().getProductId()));
        vAppId.setText(mViewModel.getAd().getAppId());
        vAppPrivacyPolicyUrl.setText(mViewModel.getAd().getAppPrivacyPolicyUrl());
        vAverageRatingImageUrl.setText(mViewModel.getAd().getAverageRatingImageUrl());
        vBidRate.setText(mViewModel.getAd().getBidRate());
        vBillingTypeId.setText(Integer.toString(mViewModel.getAd().getBillingTypeId()));
        vCallToAction.setText(mViewModel.getAd().getCallToAction());
        vCampaignDisplayOrder.setText(Integer.toString(mViewModel.getAd().getCampaignDisplayOrder()));
        vCampaignId.setText(Long.toString(mViewModel.getAd().getCampaignId()));
        vCampaignTypeId.setText(Integer.toString(mViewModel.getAd().getCampaignTypeId()));
        vCategoryName.setText(mViewModel.getAd().getCategoryName());
        vClickProxyUrl.setText(mViewModel.getAd().getClickProxyUrl());
        vCreativeId.setText(Long.toString(mViewModel.getAd().getCreativeId()));
        vHomeScreen.setText(Boolean.toString(mViewModel.getAd().isHomeScreen()));
        vMinOSVersion.setText(mViewModel.getAd().getMinOSVersion());
        vImpressionTrackingUrl.setText(mViewModel.getAd().getImpressionTrackingUrl());
        vIsRandomPick.setText(Boolean.toString(mViewModel.getAd().isRandomPick()));
        vNumberOfRatings.setText(mViewModel.getAd().getNumberOfRatings());
        vRating.setText(Float.toString(mViewModel.getAd().getRating()));

    }
}
