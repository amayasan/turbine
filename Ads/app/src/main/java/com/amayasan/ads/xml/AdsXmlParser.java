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
    public static final String ns = null;
    public static final String ADS_START_TAG = "ads";
    public static final String AD_ELEMENT_TAG = "ad";

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
                ads.add(Ad.parse(parser));
            } else {
                skip(parser);
            }
        }
        return ads;
    }

    public static String readElement(XmlPullParser parser, String elementName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, elementName);
        String rating = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, elementName);
        return rating;
    }

    // For the tags title and summary, extracts their text values.
    public static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    public static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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