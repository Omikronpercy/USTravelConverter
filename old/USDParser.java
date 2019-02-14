package uscartools.USTravelConverter;

import android.os.Build;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class USDParser {
    public static final String ITEM = "Cube";
   // public static final String DATE = "time";
    public static final String TAG = "currency";
    public static final String TAG2 = "rate";
    private XmlPullParser parser;

    public static final String BANKURL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

  /*  public USDParser() throws XmlPullParserException {
        parser = XmlPullParserFactory.newInstance().newPullParser();
    }
    */

    //  <Cube currency="USD" rate="1.1849"/>



    /**
     * Parses the XML stream and takes currencies and rates and stores
     * them in an ExchangeRates object.
     * https://github.com/pinne/Exchange-Rate-Converter/blob/master/src/com/douchedata/exhange/MainActivity.java
     * https://stackoverflow.com/questions/37093723/how-to-add-an-android-studio-project-to-github
     * http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
     * The function returns true if the rates are up to date.
     */
    public double getUSRate(InputStream stream) throws XmlPullParserException, IOException {
        parser.setInput(stream, null);
        int parseEvent = parser.getEventType();
       // boolean upToDate = false;
        double USDfactor = 1;


        while (parseEvent != XmlPullParser.END_DOCUMENT) {
            switch (parseEvent) {
                case XmlPullParser.START_DOCUMENT:
                    //
                    break;
                case XmlPullParser.END_DOCUMENT:
                    //
                    break;
                case XmlPullParser.START_TAG:
                    String tagName = parser.getName();

                    if (tagName.equalsIgnoreCase(ITEM)) {   //cube
                        String currency = parser.getAttributeValue(null, TAG);  //currency
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            if (Objects.equals(currency, "USD")) {              //Abfrage nach USD?

                                //exchangeRates.addCurrency(currency);

                                String rate = parser.getAttributeValue(null, TAG2);  //rate
                                if (rate != null) {
                                    Double num = Double.valueOf(rate);
                                   // exchangeRates.addRate(num);
                                    USDfactor = num;
                                }
                            }
                        }

                    }
                    break;
                case XmlPullParser.END_TAG:
                    //
                    break;
                case XmlPullParser.TEXT:
                    //
                    break;
                default:
                    //
            }

            parseEvent = parser.next();
        }
        stream.close();
        return USDfactor;
    }





}
