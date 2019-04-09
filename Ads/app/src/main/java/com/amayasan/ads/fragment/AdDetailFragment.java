package com.amayasan.ads.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amayasan.ads.databinding.AdDetailFragmentBinding;
import com.amayasan.ads.model.Ad;
import com.amayasan.ads.viewmodel.AdDetailViewModel;
import com.amayasan.ads.R;

// The AdDetailFragment receives a Ad object via arguments in the Bundle
// and displays all the relevant information in a TableLayout
public class AdDetailFragment extends Fragment {
    private static final String AD_KEY = "ad";

    private AdDetailViewModel mViewModel;
    private AdDetailFragmentBinding mBinding;

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
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.ad_detail_fragment, container, false);

        mBinding.setLifecycleOwner(this);


        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdDetailViewModel.class);
        mViewModel.setAd((Ad) getArguments().getSerializable(AD_KEY));

        mBinding.setViewmodel(mViewModel);
    }
}
