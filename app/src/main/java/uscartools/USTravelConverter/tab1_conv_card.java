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

import android.content.SharedPreferences;
//import android.os.Handler;
import android.graphics.Color;
//import android.os.Handler;
import androidx.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

//import android.support.v4.widget.NestedScrollView;
//import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.TextView;

import java.util.Locale;


//TODO rycycler view
public class tab1_conv_card extends Fragment {


    private final ImportTools tools = new ImportTools();
    private int length_factor = 1;
    private double weight_factor = 2.2046;
    private double feet_factor = 1.60934;
    private double fluid_factor = 3.7854;
    private boolean feet_inch = false;
    private double car_factor = 0.0689; //16.387064;
    // --Commented out by Inspection (26.03.2020 22:12):private Handler uiHandler;  //um UI aus thread zu ändern




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_conv_card , container, false);

       // final NestedScrollView scroll = rootView.findViewById(R.id.SCView);



        final EditText ftlb = rootView.findViewById(R.id.editLBFT);
        final EditText Nm= rootView.findViewById(R.id.editNm);
        final EditText psi= rootView.findViewById(R.id.editPSI);
        final EditText bar= rootView.findViewById(R.id.editBar);
        final EditText far= rootView.findViewById(R.id.editFahrenheit);
        final EditText cel= rootView.findViewById(R.id.editCelsius);
        final EditText inch= rootView.findViewById(R.id.editInch);
        final EditText cm= rootView.findViewById(R.id.editCenti);
        final EditText gal= rootView.findViewById(R.id.editUSgal);
        final EditText liter= rootView.findViewById(R.id.editLiter);
        final EditText miles= rootView.findViewById(R.id.editMeilen);
        final EditText km= rootView.findViewById(R.id.editKilometer);
        final EditText mpg= rootView.findViewById(R.id.editMPG);
        final EditText lkm= rootView.findViewById(R.id.editLPK);
        final EditText mph= rootView.findViewById(R.id.editMPH);
        final EditText kmh= rootView.findViewById(R.id.editKMH);
        final EditText pfund= rootView.findViewById(R.id.editLB);
        final EditText kilo= rootView.findViewById(R.id.editKg);
        final EditText USd = rootView.findViewById(R.id.editUSdollar);
        final EditText euro= rootView.findViewById(R.id.editEuro);
        final EditText dolgal= rootView.findViewById(R.id.editDolgal);
        final EditText eurolit= rootView.findViewById(R.id.editEurolit);
        //Custom card
        final EditText cust1= rootView.findViewById(R.id.editUser1);
        final EditText cust2= rootView.findViewById(R.id.editUser2);
        final TextView textcust1= rootView.findViewById(R.id.textUser1);
        final TextView textcust2= rootView.findViewById(R.id.textUser2);

        //clickable EInheiten

        final TextView text_lb= rootView.findViewById(R.id.textLB);
        final TextView text_kg= rootView.findViewById(R.id.textKG);
        final TextView text_cm= rootView.findViewById(R.id.textCM);
        final TextView text_km= rootView.findViewById(R.id.textKM);
        final TextView text_mi= rootView.findViewById(R.id.textMI);
        final TextView text_gal= rootView.findViewById(R.id.textGAL);
        final TextView text_lit= rootView.findViewById(R.id.textLITER);
        final TextView text_inch= rootView.findViewById(R.id.textInch);
        final TextView text_psi= rootView.findViewById(R.id.textPSI);
        final TextView text_bar= rootView.findViewById(R.id.textBAR);

      //  final NestedScrollingChild sv = (NestedScrollingChild)   (R.id.SCView);
       // final ScrollView scroll = (ScrollView)rootView.findViewById(R.id.SCView);

        String _usd = read("USD", "1.0000");
        final double factor=tools.getNumber(_usd);

        //set hint with USD factor
        USd.setHintTextColor(Color.LTGRAY);
        USd.setHint(_usd);
        euro.setHintTextColor(Color.LTGRAY);
        euro.setHint("1");

        final String unit1 = read("Unit1","Unit1");
       String unit2 = read("Unit2","Unit2");
        final String userfaktor1 = read("userFaktor1","1.0000");



//Umschaltung Einheiten

        text_psi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (text_psi.getText().toString()){
                    case "psi":
                        text_psi.setText("cui");
                        text_bar.setText("ml");
                        car_factor = 16.387064;
                        break;

                    case "cui":
                        text_psi.setText("psi");
                        text_bar.setText("bar");

                        car_factor = 0.0689;
                        break;

                }
                psi.setText("");
                bar.setText("");
             //   if(!cm.getText().toString().isEmpty())   cm.performClick();  //event handler

            }
        });

        text_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (text_bar.getText().toString()){
                    case "bar":
                        text_psi.setText("cui");
                        text_bar.setText("ml");

                        car_factor = 16.387064;
                        break;

                    case "ml":
                        text_psi.setText("psi");
                        text_bar.setText("bar");
                        car_factor = 0.0689;
                        break;

                }
                psi.setText("");
                bar.setText("");
                //   if(!cm.getText().toString().isEmpty())   cm.performClick();  //event handler

            }
        });


        text_inch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (text_inch.getText().toString()){
                    case "inch":
                        text_inch.setText("feet-inch");
                        feet_inch = true;
                        break;

                    case "feet-inch":
                        text_inch.setText("inch");
                        feet_inch = false;
                        break;

                }

                if(!cm.getText().toString().isEmpty())   cm.performClick();  //event handler

            }
        });

        text_cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        switch (text_cm.getText().toString()){
            case "cm":
                text_cm.setText("mm");
                length_factor = 10;
                break;

            case "mm":
                text_cm.setText("cm");
                length_factor = 1;
                break;

        }

                if(!inch.getText().toString().isEmpty())   inch.performClick();  //event handler
            }
        });

        text_lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            switch (text_lb.getText().toString()){
                case "lb":
                    text_lb.setText("oz");
                    text_kg.setText("g ");
                    weight_factor = 1/28.3495;
                    break;

                case "oz":
                    text_lb.setText("lb");
                    text_kg.setText("kg");
                    weight_factor = 2.2046;
                    break;
            }

                if(!pfund.getText().toString().isEmpty()) pfund.performClick();  //event handler
            }
        });

        text_kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            switch (text_kg.getText().toString()){
                case "kg":
                    text_lb.setText("oz");
                    text_kg.setText("g");
                    weight_factor = 1/28.3495;
                    break;

                case "g":
                    text_lb.setText("lb");
                    text_kg.setText("kg");
                    weight_factor = 2.2046;
                    break;
            }

                if(!kilo.getText().toString().isEmpty()) kilo.performClick();  //event handler
            }
        });

        text_km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            switch (text_km.getText().toString()){
                case "km":
                    text_mi.setText("feet");
                    text_km.setText("m");
                    feet_factor = 0.3048;
                    break;

                case "m":
                    text_mi.setText(getString(R.string.meilen));
                    text_km.setText("km");
                    feet_factor = 1.60934;
                    break;

            }

                if(!miles.getText().toString().isEmpty()) miles.performClick();  //event handler
            }
        });

        text_mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

 /*           switch (text_mi.getText().toString()){
                case getResources().getString(R.string.meilen):
                    text_mi.setText("feet");
                    text_km.setText("m ");
                    feet_factor = 0.3048;
                    break;

                case "feet":
                    text_mi.setText(getString(R.string.meilen));
                    text_km.setText("km");
                    feet_factor = 1.60934;
                    break;
            }*/
                //PS switch kann keine strings verarbeiten

                if (text_mi.getText().equals(getString(R.string.meilen))){
                    text_mi.setText("feet");
                    text_km.setText("m ");
                    feet_factor = 0.3048;

                }
                else if (text_mi.getText().equals("feet"))
                {
                    text_mi.setText(getString(R.string.meilen));
                    text_km.setText("km");
                    feet_factor = 1.60934;
                }
                if(!km.getText().toString().isEmpty()) km.performClick();  //event handler
            }
        });

        text_gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (text_gal.getText().equals(getString(R.string.us_gal))){
                    text_gal.setText("fl oz");
                    text_lit.setText("ml");
                    fluid_factor = 29.5735;

                }
                else if (text_gal.getText().equals("fl oz")) {
                    text_gal.setText(getString(R.string.us_gal));
                    text_lit.setText("liter");
                    fluid_factor = 3.7854;
                }
                if(!liter.getText().toString().isEmpty()) liter.performClick();  //event handler
            }
        });

        text_lit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (text_lit.getText().equals(getString(R.string.liter))){
                   // text_gal.setText("\u2bc8"+"fl oz");  //ToDo unicode test
                    text_gal.setText("fl oz");  //ToDo unicode test
                    text_lit.setText("ml");
                    fluid_factor = 29.5735;

                }
                else {
                    text_gal.setText(getString(R.string.us_gal));
                    text_lit.setText("liter");
                    fluid_factor = 3.7854;
                }
                if(!gal.getText().toString().isEmpty()) gal.performClick();  //event handler
            }
        });

//customer card
        textcust1.setText(unit1);
        textcust2.setText(unit2);

        cust1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
              //  scrollToBottom(nestscroll, usercard);
                double _cust2 = tools.getNumber(cust1.getText().toString())*tools.getNumber(userfaktor1);

                cust2.setText(String.format(Locale.US,"%.2f",_cust2));
                cust2.requestFocus();
                cust1.requestFocus();
            }
        });

        cust2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _cust1 = tools.getNumber(cust2.getText().toString())/tools.getNumber(userfaktor1);

                cust1.setText(String.format(Locale.US,"%.2f",_cust1));

            }
        });

//US Dollar nach Euro

        USd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                USd.setHint("");
                euro.setHint("");

                double _euro = tools.getNumber(USd.getText().toString())/factor;

                euro.setText(String.format(Locale.US,"%.2f",_euro));
            }
        });

        euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                USd.setHint("");
                euro.setHint("");

                double _usdoll = tools.getNumber(euro.getText().toString())*factor;

                USd.setText(String.format(Locale.US,"%.2f",_usdoll));

            }
        });

// Benzinpreis liter Gallonen
        dolgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _eurolit = tools.getNumber(dolgal.getText().toString())/factor/3.7854;

                eurolit.setText(String.format(Locale.US,"%.2f",_eurolit));
                eurolit.requestFocus();
                dolgal.requestFocus();
            }
        });

        eurolit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _dollgal = tools.getNumber(eurolit.getText().toString())*factor*3.7854;

                dolgal.setText(String.format(Locale.US,"%.2f",_dollgal));

            }
        });

        //Drehmoment Nm ftlb
        ftlb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 

                double _Nm = tools.getNumber(ftlb.getText().toString())/0.7375;

                Nm.setText(String.format(Locale.US,"%.2f",_Nm));
                Nm.requestFocus();
                ftlb.requestFocus();


            }
        });

        Nm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _ftlb = tools.getNumber(Nm.getText().toString())*0.7375;

                ftlb.setText(String.format(Locale.US,"%.2f",_ftlb));

            }
        });

//Druck psi bar

        psi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _bar = tools.getNumber(psi.getText().toString())*car_factor;

                bar.setText(String.format(Locale.US,"%.3f",_bar));
                bar.requestFocus();
                psi.requestFocus();

            }
        });

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _psi = tools.getNumber(bar.getText().toString())/car_factor /*/.0689*/;

                psi.setText(String.format(Locale.US,"%.3f",_psi));

            }
        });

        //Temperatur Fahrenheit Celsius
        far.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _cel = (tools.getNumber(far.getText().toString())-32)*0.55555;

                cel.setText(String.format(Locale.US,"%.2f",_cel));
                cel.requestFocus();
                far.requestFocus();

            }
        });

        cel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _far = tools.getNumber(cel.getText().toString())*1.8 + 32;

                far.setText(String.format(Locale.US,"%.2f",_far));


            }
        });

//Laenge inch cm

        inch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double _cm;
                if (!feet_inch){
                    String _fractinch = inch.getText().toString();
                    String[] tokens = _fractinch.split("-");   //um alle keyboards zu haben "/"
                    if(tokens.length==2) {
                        //throw new IllegalArgumentException();}
                        String _zaehler = tokens[0];
                        String _nenner = tokens[1];
                        _cm = (tools.getNumber(_zaehler) / tools.getNumber(_nenner)) * 2.54  * length_factor;

                    }
                    else { _cm = tools.getNumber(inch.getText().toString())*2.54 * length_factor;}


                }
                else {
                    String _feetinch = inch.getText().toString();
                    String[] tokens = _feetinch.split("-");
                    if(tokens.length==2) {
                        //throw new IllegalArgumentException();}
                        String _feet = tokens[0];
                        String _inch = tokens[1];

                        _cm = (tools.getNumber(_feet) * 30.48 + tools.getNumber(_inch) * 2.54) * length_factor;
                    }
                    else { _cm=0;}
                }
                cm.setText(String.format(Locale.US,"%.5f",_cm));
                cm.requestFocus();
                inch.requestFocus();

            }
        });


        cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _inch, x, y;

                if (!feet_inch){
                    _inch = tools.getNumber(cm.getText().toString())/(2.54* length_factor);
                    inch.setText(String.format(Locale.US,"%.5f",_inch));
                }
               else {
                    x = Math.floor(tools.getNumber(cm.getText().toString()) / 30.48);
                    y = (tools.getNumber(cm.getText().toString()) % 30.48) / 2.54;  //modulo inch anteil
                    inch.setText(String.format(Locale.US,"%.0f",x) + "-"+    String.format(Locale.US,"%.2f",y));
                }



            }
        });

//Volumen Gallonen liter
        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 

                double _liter = tools.getNumber(gal.getText().toString())*fluid_factor; //  3.7854;

                liter.setText(String.format(Locale.US,"%.3f",_liter));
                liter.requestFocus();

                gal.requestFocus();

            }
        });

        liter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _gal = tools.getNumber(liter.getText().toString())/fluid_factor; //  3.7854;

                gal.setText(String.format(Locale.US,"%.3f",_gal));

            }
        });

//Entfernung Meilen Kilometer
        miles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _km = tools.getNumber(miles.getText().toString())*feet_factor;

                km.setText(String.format(Locale.US,"%.3f",_km));
                km.requestFocus();
                miles.requestFocus();

            }
        });

        km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _miles = tools.getNumber(km.getText().toString())/feet_factor;

                miles.setText(String.format(Locale.US,"%.3f",_miles));

            }
        });

        // Benzinverbrauch MPG nach liter/100km
        mpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _lkm = 3.87/(tools.getNumber(mpg.getText().toString())*1.60934)*100;

                lkm.setText(String.format(Locale.US,"%.2f",_lkm));
                lkm.requestFocus();
                mpg.requestFocus();

            }
        });

        lkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _mpg = 3.87/(tools.getNumber(lkm.getText().toString())*1.60934)*100;

                mpg.setText(String.format(Locale.US,"%.2f",_mpg));

            }
        });

//Geschwindigkeit Meilen /stunde  Kilometer /h
        mph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _kmh = tools.getNumber(mph.getText().toString())*1.60934;

                kmh.setText(String.format(Locale.US,"%.2f",_kmh));
        /*        scroll.postDelayed(new Runnable() {
                    public void run() {
                     //   scroll.smoothScrollTo(0, kmh.getBottom()+250);  //ToDo  absolute position raus bekommen?
                        View lastChild = scroll.getChildAt(scroll.getChildCount() - 1);
                        int bottom = lastChild.getBottom() + scroll.getPaddingBottom();
                        int sy = scroll.getScrollY();
                        int sh = scroll.getHeight();
                        int delta = bottom - (sy + sh);
                        scroll.smoothScrollBy(0, delta);
                   }
                },200); */
       // scroll.scrollBy(10,100);
             //   kmh.requestFocus();

              //  mph.requestFocus();


            }
        });

        kmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _mph = tools.getNumber(kmh.getText().toString())/1.60934;

                mph.setText(String.format(Locale.US,"%.2f",_mph));

            }
        });

//Gewicht Pfund kilo
        pfund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _kilo = tools.getNumber(pfund.getText().toString())/ weight_factor; // 2.2046;

                kilo.setText(String.format(Locale.US,"%.3f",_kilo));
              //  kilo.getParent().requestChildFocus(kilo,kilo);
                kilo.requestFocus();
                pfund.requestFocus();

            }
        });

        kilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 
                double _pfund = tools.getNumber(kilo.getText().toString())* weight_factor; //2.2046;

                pfund.setText(String.format(Locale.US,"%.3f",_pfund));

            }
        });

   /*     new Thread(new Runnable(){
            public void run() {

                final String _usd = read("USD", "1.0000");
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        USd.setHint(_usd);
                    }
                });

            }
        }).start();
*/



        return rootView;
    }

    //read preferences string
    private String read(String valueKey, String valueDefault) {

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        return prefs.getString(valueKey, valueDefault);
    }

    @Override
    public void onResume() {
        super.onResume();

       // View rootView = inflater.inflate(R.layout.tab1_conv_card , container, false);
       // final EditText USd = .findViewById(R.id.editUSdollar);
        final String _usd = read("USD", "1.0000");
       /* uiHandler.post(new Runnable() {
            @Override
            public void run() {
                USd.setHint(_usd);
            }
        });*/
// PERFORM YOUR OPERATION OVER HERE
    }

}
