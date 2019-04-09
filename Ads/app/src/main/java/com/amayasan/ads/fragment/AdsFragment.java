package com.amayasan.ads.fragment;

import android.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amayasan.ads.model.Ad;
import com.amayasan.ads.viewmodel.AdsViewModel;
import com.amayasan.ads.asynctask.AdsDownloadXmlTask;
import com.amayasan.ads.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

// The AdsFragment initiates an AsyncTask to download/parse the XML document
// and displays the resulting List<Ad> in a RecyclerView
public class AdsFragment extends Fragment {
    private static final String URL = "http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=10777&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=20&lname=amaya";

    private AdsViewModel mViewModel;

    private RecyclerView vRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressBar vProgressBar;
    private TextView vEmptyText;

    public static AdsFragment newInstance() {
        return new AdsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_fragment, container, false);

        vRecycler = view.findViewById(R.id.vRecycler);
        vRecycler.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        vRecycler.setLayoutManager(mLayoutManager);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.vPullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPage();
                pullToRefresh.setRefreshing(false);
            }
        });

        vProgressBar = view.findViewById(R.id.vProgressBar);
        vEmptyText = view.findViewById(R.id.vEmptyText);

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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mViewModel.getAds() == null) {
            loadPage();
        } else {
            populateAdapter();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AdsDownloadXmlTask.AdsDownloadXmlMessageEvent event) {

        if (event.getException() == null) {
            // Success case, show results in RecyclerView
            mViewModel.setAds(event.getAds());
            populateAdapter();
        } else {
            // Failure case, show error message
            vProgressBar.setVisibility(View.INVISIBLE);
            vEmptyText.setVisibility(View.VISIBLE);
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.error)
                    .setMessage(event.getException().getMessage())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void loadPage() {
        vRecycler.setVisibility(View.INVISIBLE);
        vEmptyText.setVisibility(View.INVISIBLE);
        vProgressBar.setVisibility(View.VISIBLE);
        new AdsDownloadXmlTask().execute(URL);
    }

    private void populateAdapter() {
        vProgressBar.setVisibility(View.INVISIBLE);
        if (!mViewModel.getAds().isEmpty()) {
            vEmptyText.setVisibility(View.INVISIBLE);
            vRecycler.setVisibility(View.VISIBLE);
            mAdapter = new AdsAdapter(mViewModel.getAds());
            vRecycler.setAdapter(mAdapter);
        } else {
            vEmptyText.setVisibility(View.VISIBLE);
            vRecycler.setVisibility(View.INVISIBLE);
        }
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

