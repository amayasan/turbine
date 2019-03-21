package com.amayasan.ads.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amayasan.ads.model.Ad;
import com.amayasan.ads.viewmodel.AdsViewModel;
import com.amayasan.ads.asynctask.AdsDownloadXmlTask;
import com.amayasan.ads.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdsFragment extends Fragment implements AdsDownloadXmlTask.OnTaskCompleted {
    private static final String URL = "http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=10777&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=20&lname=amaya";

    private AdsViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static AdsFragment newInstance() {
        return new AdsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mViewModel.getAds() == null) {
            loadPage();
        } else {
            mAdapter = new AdsAdapter(mViewModel.getAds());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void loadPage() {
        new AdsDownloadXmlTask(this).execute(URL);
    }

    @Override
    public void onSuccess(List<Ad> ads) {
        mViewModel.setAds(ads);
        mAdapter = new AdsAdapter(mViewModel.getAds());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onError(String errorMessage) {

    }

    public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdViewHolder> {
        private List<Ad> mDataset;

        public class AdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView vProductName;
            public ImageView vProductThumbnail;
            public TextView vRating;

            public AdViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);

                vProductName = v.findViewById(R.id.vProductName);
                vProductThumbnail = v.findViewById(R.id.vProductThumbnail);
                vRating = v.findViewById(R.id.vRating);
            }

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AdDetailFragment.newInstance(mDataset.get(getAdapterPosition())))
                        .addToBackStack("ad_detail_fragment")
                        .commit();
            }
        }

        public AdsAdapter(List<Ad> ads) {
            mDataset = ads;
        }

        @Override
        public AdsAdapter.AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_viewholder, parent, false);

            AdsAdapter.AdViewHolder vh = new AdsAdapter.AdViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(AdsAdapter.AdViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.vProductName.setText(mDataset.get(position).getProductName());
            Picasso.get().load(mDataset.get(position).getProductThumbnail()).into(holder.vProductThumbnail);
            holder.vRating.setText(Float.toString(mDataset.get(position).getRating()));

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}

