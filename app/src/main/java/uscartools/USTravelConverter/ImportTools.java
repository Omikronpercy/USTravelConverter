/*
 *      GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 *         This program converts some imperial units to SI units for travel in the USA
 *         Copyright (C) <2019>  <Github: Omikronpercy>
 *
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *         GNU General Public License for more details.
 *
 *         You should have received a copy of the GNU General Public License
 *         along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * /
 *
 *
 */




package uscartools.USTravelConverter;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import java.io.InputStream;
import java.net.URL;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


class ImportTools {

    private static final String CURRENCY = "currency";
    private static final String CUBE_NODE = "//Cube/Cube/Cube";
    private static final String RATE = "rate";

    private Context context;


    public double USDRate(){

        double _usdrate = 1;  // wenn ecb nicht erreichbar wird prefs ausgelesen

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document;
        String spec = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";  //ERROR https FIXED

        try {
            URL url = new URL(spec);
            InputStream is = url.openStream();
            assert builder != null;
            document = builder.parse(is);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(CUBE_NODE);
            NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                NamedNodeMap attribs = node.getAttributes();
                if (attribs.getLength() > 0) {
                    Node currencyAttrib = attribs.getNamedItem(CURRENCY);
                    if (currencyAttrib != null) {
                        String currencyTxt = currencyAttrib.getNodeValue();
                        String rateTxt = attribs.getNamedItem(RATE).getNodeValue();

                        if (currencyTxt.equals("USD")){
                            _usdrate = getNumber(rateTxt);
                            break;
                        }

                    }
                }
            }
        } catch (SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }

        if (_usdrate == 1){  //something wrong, get saved USD rate
              _usdrate=getNumber(read("USD", "1.0000"));
        }
        return _usdrate;
    }





    ///internet abfrage
    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;


    }



  
    ///methode Zoll Berechnung Rückgabe in EURO
    /// Invoice in USD, Zollsatz als Kommazahl, factor, Steuer in %, Flag ob Ausgabe in Euro
    public double[] calcZoll(double Invoice, double Zollsatz, double factor, double Steuer, boolean currency)
    {
        double Gesamtbetrag; //Gesamtbetrag
        double Zollabgaben; //Zollbetrag
        double EinfuhrSt;

        if (Invoice <= (22*factor)  /*|| numberBox31.Text ==""*/)  //keine Abgaben   || to avoid exception at startup
        {
            Gesamtbetrag = Invoice;
            Zollabgaben = 0;
            EinfuhrSt = 0;
        }
        else if (Invoice > (22*factor) && Invoice <= (150*factor))  //kein Zoll
        {
            EinfuhrSt = (Invoice * Steuer * 0.01)  ;
            Gesamtbetrag = Invoice + EinfuhrSt ;
            Zollabgaben =0;

        }
        else  //Zoll + EuSt
        {
            Zollabgaben= Invoice*Zollsatz ;
            EinfuhrSt= (Zollabgaben + Invoice)*Steuer * 0.01 ;
            //Gesamtbetrag = ((Zollsatz * 0.19) + Zollsatz) / factor;
            Gesamtbetrag = Invoice + Zollabgaben + EinfuhrSt;
        }

        if (currency){   //spin_currency = euro
            Zollabgaben /=factor;
            EinfuhrSt /= factor;
            Gesamtbetrag /= factor;
        }

        return new double[]{Zollabgaben,Gesamtbetrag,EinfuhrSt,Zollabgaben+EinfuhrSt};

    }






    //Method String to Double
    public double getNumber(String textnumber) {

        double m;
        try {
            m = Double.parseDouble(textnumber);
            return m;

        } catch (NumberFormatException ex) {
            m = 0;
            return m;
        }
    }


    //read preferences string
    private String read(String valueKey, String valueDefault) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context );
        return prefs.getString(valueKey, valueDefault);
    }


}
