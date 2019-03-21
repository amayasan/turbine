package com.amayasan.ads.model;

// Ads have the following XML data structure
//<ad>
//  <appId>com.zynga.ozmatch</appId>
//  <averageRatingImageUrl>
//      https://cdn1.appia.com/cdn/adpub/appwallv1/rated-5-5.png
//  </averageRatingImageUrl>
//  <bidRate>0.000</bidRate>
//  <billingTypeId>1</billingTypeId>
//  <callToAction>Install Now</callToAction>
//  <campaignDisplayOrder>1</campaignDisplayOrder>
//  <campaignId>24058</campaignId>
//  <campaignTypeId>2</campaignTypeId>
//  <categoryName>Puzzle</categoryName>
//  <clickProxyURL>
//      http://prlds.appia.com/v2/preloadAd.jsp?siteId=10777&deviceId=4230&spotId=&sessionId=techtestsession&campaignId=24058&creativeId=483970&packageName=com.zynga.ozmatch&fulfillmentT
//  </clickProxyURL>
//  <creativeId>483970</creativeId>
//  <homeScreen>false</homeScreen>
//  <impressionTrackingURL>
//      https://imps.appia.com/v2/impressionAd.jsp?siteId=10777&campaignId=24058&creativeId=483970&campaignDisplayOrder=1&ts=16998b6a39b&sessionId=techtestsession&packageName=com.zynga.ozma
//  </impressionTrackingURL>
//  <isRandomPick>false</isRandomPick>
//  <numberOfRatings>10,000+</numberOfRatings>
//  <productDescription>
//      Match your way to meet the wonderful Wizard of Oz in this amazing puzzle adventure!
//  </productDescription>
//  <productId>15338</productId>
//  <productName>Wizard of Oz: Magic Match</productName>
//  <productThumbnail>
//      https://prod-static-images.s3.amazonaws.com/shared/creatives/15338/1385be772f424a3cb42cd8f07747b05b.png
//  </productThumbnail>
//  <rating>5.0</rating>
//</ad>

import com.amayasan.ads.xml.AdsXmlParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;

import static com.amayasan.ads.xml.AdsXmlParser.AD_ELEMENT_TAG;
import static com.amayasan.ads.xml.AdsXmlParser.readElement;
import static com.amayasan.ads.xml.AdsXmlParser.skip;

public class Ad implements Serializable {
    public static final String
            APP_ID = "appId",
            APP_PRIVACY_POLICY_URL = "appPrivacyPolicyUrl",
            AVERAGE_RATING_IMAGE_URL = "averageRatingImageURL",
            BID_RATE = "bidRate",
            BILLING_TYPE_ID = "billingTypeId",
            CALL_TO_ACTION = "callToAction",
            CAMPAIGN_DISPLAY_ORDER = "campaignDisplayOrder",
            CAMPAIGN_ID = "campaignId",
            CAMPAIGN_TYPE_ID = "campaignTypeId",
            CATEGORY_NAME = "categoryName",
            CLICK_PROXY_URL = "clickProxyURL",
            CREATIVE_ID = "creativeId",
            HOME_SCREEN = "homeScreen",
            IMPRESSION_TRACKING_URL = "impressionTrackingURL",
            IS_RANDOM_PICK = "isRandomPick",
            MIN_OS_VERSION = "minOSVersion",
            NUMBER_OF_RATINGS = "numberOfRatings",
            PRODUCT_DESCRIPTION = "productDescription",
            PRODUCT_ID = "productId",
            PRODUCT_NAME = "productName",
            PRODUCT_THUMBNAIL = "productThumbnail",
            RATING = "rating";


    private String appId;
    private String appPrivacyPolicyUrl;
    private String averageRatingImageUrl;
    private String bidRate;
    private int billingTypeId;
    private String callToAction;
    private int campaignDisplayOrder;
    private long campaignId;
    private int campaignTypeId;
    private String categoryName;
    private String clickProxyUrl;
    private long creativeId;
    private boolean homeScreen;
    private String minOSVersion;
    private String impressionTrackingUrl;
    private boolean isRandomPick;
    private String numberOfRatings;
    private String productDescription;
    private long productId;
    private String productName;
    private String productThumbnail;
    private float rating;

    public static Ad parse(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, AdsXmlParser.ns, AD_ELEMENT_TAG);
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAverageRatingImageUrl() {
        return averageRatingImageUrl;
    }

    public void setAverageRatingImageUrl(String averageRatingImageUrl) {
        this.averageRatingImageUrl = averageRatingImageUrl;
    }

    public String getBidRate() {
        return bidRate;
    }

    public void setBidRate(String bidRate) {
        this.bidRate = bidRate;
    }

    public int getBillingTypeId() {
        return billingTypeId;
    }

    public void setBillingTypeId(int billingTypeId) {
        this.billingTypeId = billingTypeId;
    }

    public String getCallToAction() {
        return callToAction;
    }

    public void setCallToAction(String callToAction) {
        this.callToAction = callToAction;
    }

    public int getCampaignDisplayOrder() {
        return campaignDisplayOrder;
    }

    public void setCampaignDisplayOrder(int campaignDisplayOrder) {
        this.campaignDisplayOrder = campaignDisplayOrder;
    }

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    public int getCampaignTypeId() {
        return campaignTypeId;
    }

    public void setCampaignTypeId(int campaignTypeId) {
        this.campaignTypeId = campaignTypeId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getClickProxyUrl() {
        return clickProxyUrl;
    }

    public void setClickProxyUrl(String clickProxyUrl) {
        this.clickProxyUrl = clickProxyUrl;
    }

    public long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(long creativeId) {
        this.creativeId = creativeId;
    }

    public boolean isHomeScreen() {
        return homeScreen;
    }

    public void setHomeScreen(boolean homeScreen) {
        this.homeScreen = homeScreen;
    }

    public String getImpressionTrackingUrl() {
        return impressionTrackingUrl;
    }

    public void setImpressionTrackingUrl(String impressionTrackingUrl) {
        this.impressionTrackingUrl = impressionTrackingUrl;
    }

    public boolean isRandomPick() {
        return isRandomPick;
    }

    public void setRandomPick(boolean randomPick) {
        isRandomPick = randomPick;
    }

    public String getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(String numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(String productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAppPrivacyPolicyUrl() {
        return appPrivacyPolicyUrl;
    }

    public void setAppPrivacyPolicyUrl(String appPrivacyPolicyUrl) {
        this.appPrivacyPolicyUrl = appPrivacyPolicyUrl;
    }

    public String getMinOSVersion() {
        return minOSVersion;
    }

    public void setMinOSVersion(String minOSVersion) {
        this.minOSVersion = minOSVersion;
    }
}