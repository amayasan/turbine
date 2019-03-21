package com.amayasan.ads.asynctask;

import android.os.AsyncTask;

import com.amayasan.ads.model.Ad;
import com.amayasan.ads.xml.AdsXmlParser;

import org.greenrobot.eventbus.EventBus;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdsDownloadXmlTask extends AsyncTask<String, Void, List<Ad>> {

    public static class AdsDownloadXmlMessageEvent {
        private List<Ad> ads;

        public AdsDownloadXmlMessageEvent(List<Ad> ads) {
            setAds(ads);
        }

        public List<Ad> getAds() {
            return ads;
        }

        public void setAds(List<Ad> ads) {
            this.ads = ads;
        }
    }

    @Override
    protected List<Ad> doInBackground(String... urls) {
        try {
            return loadXmlFromNetwork(urls[0]);
        } catch (IOException e) {
            return null;
        } catch (XmlPullParserException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Ad> ads) {

        Collections.sort(ads, new Comparator<Ad>() {
            @Override
            public int compare(Ad lhs, Ad rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return Integer.compare(lhs.getCampaignDisplayOrder(), rhs.getCampaignDisplayOrder());
            }
        });

        EventBus.getDefault().post(new AdsDownloadXmlMessageEvent(ads));
    }

    private List<Ad> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        AdsXmlParser xmlParser = new AdsXmlParser();
        List<Ad> ads = null;

        try {
            stream = downloadUrl(urlString);
            ads = xmlParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return ads;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        java.net.URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}