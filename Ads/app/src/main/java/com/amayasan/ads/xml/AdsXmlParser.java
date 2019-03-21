package com.amayasan.ads.xml;

import android.util.Xml;

import com.amayasan.ads.model.Ad;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AdsXmlParser {
    private static final String ns = null;
    private static final String ADS_START_TAG = "ads";
    private static final String AD_ELEMENT_TAG = "ad";

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List ads = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, ADS_START_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(AD_ELEMENT_TAG)) {
                ads.add(readAd(parser));
            } else {
                skip(parser);
            }
        }
        return ads;
    }

    private Ad readAd(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, AD_ELEMENT_TAG);
        Ad ad = new Ad();

        while (parser.next() != XmlPullParser.END_TAG) {

            try {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String element = parser.getName();

                switch (element) {
                    case Ad.APP_ID:
                        ad.setAppId(readElement(parser, element));
                        break;
                    case Ad.APP_PRIVACY_POLICY_URL:
                        ad.setAppPrivacyPolicyUrl(readElement(parser, element));
                        break;
                    case Ad.AVERAGE_RATING_IMAGE_URL:
                        ad.setAverageRatingImageUrl(readElement(parser, element));
                        break;
                    case Ad.BID_RATE:
                        ad.setBidRate(readElement(parser, element));
                        break;
                    case Ad.BILLING_TYPE_ID:
                        ad.setBillingTypeId(Integer.parseInt(readElement(parser, element)));
                        break;
                    case Ad.CALL_TO_ACTION:
                        ad.setCallToAction(readElement(parser, element));
                        break;
                    case Ad.CAMPAIGN_DISPLAY_ORDER:
                        ad.setCampaignDisplayOrder(Integer.parseInt(readElement(parser, element)));
                        break;
                    case Ad.CAMPAIGN_ID:
                        ad.setCampaignId(Long.parseLong(readElement(parser, element)));
                        break;
                    case Ad.CAMPAIGN_TYPE_ID:
                        ad.setCampaignTypeId(Integer.parseInt(readElement(parser, element)));
                        break;
                    case Ad.CATEGORY_NAME:
                        ad.setCategoryName(readElement(parser, element));
                        break;
                    case Ad.CLICK_PROXY_URL:
                        ad.setClickProxyUrl(readElement(parser, element));
                        break;
                    case Ad.CREATIVE_ID:
                        ad.setCreativeId(Long.parseLong(readElement(parser, element)));
                        break;
                    case Ad.HOME_SCREEN:
                        ad.setHomeScreen(Boolean.parseBoolean(readElement(parser, element)));
                        break;
                    case Ad.IMPRESSION_TRACKING_URL:
                        ad.setImpressionTrackingUrl(readElement(parser, element));
                        break;
                    case Ad.IS_RANDOM_PICK:
                        ad.setRandomPick(Boolean.parseBoolean(readElement(parser, element)));
                        break;
                    case Ad.MIN_OS_VERSION:
                        ad.setMinOSVersion(readElement(parser, element));
                        break;
                    case Ad.NUMBER_OF_RATINGS:
                        ad.setNumberOfRatings(readElement(parser, element));
                        break;
                    case Ad.PRODUCT_DESCRIPTION:
                        ad.setProductDescription(readElement(parser, element));
                        break;
                    case Ad.PRODUCT_ID:
                        ad.setProductId(Long.parseLong(readElement(parser, element)));
                        break;
                    case Ad.PRODUCT_NAME:
                        ad.setProductName(readElement(parser, element));
                        break;
                    case Ad.PRODUCT_THUMBNAIL:
                        ad.setProductThumbnail(readElement(parser, element));
                        break;
                    case Ad.RATING:
                        ad.setRating(Float.parseFloat(readElement(parser, element)));
                        break;
                    default:
                        skip(parser);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return ad;
    }

    private String readElement(XmlPullParser parser, String elementName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, elementName);
        String rating = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, elementName);
        return rating;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}