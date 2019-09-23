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
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.SimpleDateFormat;

import java.util.Locale;


public class tab2_import extends Fragment {

    private double factor = 1;  //USD
    private Handler uiHandler;  //um UI aus thread zu ändern
    private String newDatum;
    private String oldDatum;
    private double _steuer = 19;  //Steuersatz
    private boolean euroflag = true;  //Berechnung der Abgaben in Euro
    private boolean euroflag2 = false;  //versand und rechnung in USD
    private double invoice,shipping;  //zur Vermeidung von Rundungsfehlern


    private final ImportTools tools = new ImportTools();
    //ToDo euro $ Umschalten ohne Wert

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2import, container, false);

        uiHandler = new Handler();

        final Spinner spin = rootView.findViewById((R.id.spinner));
        spin.setSelection(2);  //3%

        final Spinner spin_currency = rootView.findViewById(R.id.spinner2);
        spin_currency.setSelection(0);


        String Steuer = read("EUSt", "19");
        _steuer = tools.getNumber(Steuer);

         final EditText rech = rootView.findViewById(R.id.editText);  //rechnung
         final EditText vers = rootView.findViewById(R.id.editText2);  //versand


         final TextView betrag = rootView.findViewById(R.id.textView5);  //zollbetrag
         final TextView eust = rootView.findViewById(R.id.textView32);   //EUSt
        final TextView SummeAbg = rootView.findViewById(R.id.textView46);  //Abgabensumme
         final TextView gesamt = rootView.findViewById(R.id.textView7);  //gesamtbetrag
        final TextView Summe = rootView.findViewById(R.id.textViewSumme);  //summe
        final TextView SummeEu = rootView.findViewById(R.id.textView4);  //summe Euro


        final TextView usdollar = rootView.findViewById(R.id.textView9);  //factor
        final  TextView DatumEZB = rootView.findViewById((R.id.textView10));

        final   EditText usdollar2 = rootView.findViewById(R.id.editText7);

        final  EditText euro = rootView.findViewById(R.id.editText8);

        final Button ref_Button = rootView.findViewById(R.id.but_refresh);
        final TextView text_rechn= rootView.findViewById(R.id.textRECHN);
        final TextView text_vers= rootView.findViewById(R.id.textVERS);



        text_rechn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( text_rechn.getText().equals(getString(R.string.usdollar)))
                {
                    text_rechn.setText(getString(R.string.euro));
                    text_vers.setText(getString(R.string.euro));

                    if (!rech.getText().toString().isEmpty() ){
                        invoice /= factor;  //division durch NULL ???
                        shipping /= factor;
                    }


                    euroflag2 = true;

                }
                else {   //ToDo kein richtiges toggle
                    text_rechn.setText(getString(R.string.usdollar));
                    text_vers.setText(getString(R.string.usdollar));

                    if (!rech.getText().toString().isEmpty() ){
                        invoice *= factor;
                        shipping *= factor;
                    }


                    euroflag2 = false;

                }
                if(!rech.getText().toString().isEmpty()) {
                    rech.setText(String.format(Locale.US,"%.2f", invoice));
                    rech.performClick();  //event handler
                }
                if(!vers.getText().toString().isEmpty()) {
                    vers.setText(String.format(Locale.US,"%.2f", shipping));
                    vers.performClick();  //event handler
                }

            }
        });


        text_vers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( text_vers.getText().equals(getString(R.string.usdollar)))
                {
                    text_rechn.setText(getString(R.string.euro));
                    text_vers.setText(getString(R.string.euro));

                    if (!rech.getText().toString().isEmpty() ){
                        invoice /= factor;
                        shipping /= factor;
                    }


                    euroflag2 = true;

                }
                else {
                    text_rechn.setText(getString(R.string.usdollar));
                    text_vers.setText(getString(R.string.usdollar));
                    if (!rech.getText().toString().isEmpty() ){
                        invoice *= factor;
                        shipping *= factor;
                    }


                    euroflag2 = false;

                }
                if(!rech.getText().toString().isEmpty()) {
                    rech.setText(String.format(Locale.US,"%.2f", invoice));
                    rech.performClick();  //event handler
                }
                if(!vers.getText().toString().isEmpty()) {
                    vers.setText(String.format(Locale.US,"%.2f", shipping));
                    vers.performClick();  //event handler
                }

            }
        });




        ref_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _usd = read("USD", "1.0000");

                factor = tools.getNumber(_usd);

                //ToDo FEATURE REQ Button bei offline ausgrauen

                if (tools.isOnline() ){

                    usdollar.setText("0.0000");
  /*                  new Thread(new Runnable(){
                        public void run(){
                    factor = tools.USDRate();   //internet access nur in neuen thread
                        }
                    }).start();
                    */
// https://stackoverflow.com/questions/16624788/wait-for-a-thread-before-continue-on-android
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            factor = tools.USDRate();   //internet access nur in neuen thread
                        }});

                    t.start(); // spawn thread

                    try {
                        t.join(); //wait for thread finish
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY); //* hh:mm:ss*/
                    newDatum = dateFormat.format(new java.util.Date());  //Warum????
                    DatumEZB.setText(newDatum);
                    usdollar.setText(String.format(Locale.US,"%.4f", factor));

                    Animation anim = new AlphaAnimation(0.0f, 1.0f);
                    anim.setDuration(100); //You can manage the time of the blink with this parameter
                    anim.setStartOffset(20);
                    anim.setRepeatMode(Animation.REVERSE);
                    anim.setRepeatCount(4/*Animation.INFINITE*/);
                    usdollar.startAnimation(anim);


                    save("Datum", newDatum);
                    save("USD",String.format(Locale.US,"%.4f", factor) );

                    //tab1_conv_card fragment = (tab1_conv_card) getFragmentManager().findFragmentById(R.id.SCView);

                }

            }
        });

        rech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                invoice = tools.getNumber(rech.getText().toString());
                shipping = tools.getNumber(vers.getText().toString());

               // double imp1 = (tools.getNumber(rech.getText().toString())+tools.getNumber(vers.getText().toString()));  //exception at startup, acceptable

                double imp1 = invoice + shipping;
                double imp2  = ( tools.getNumber(String.valueOf(spin.getSelectedItem())) * 0.01);   //Spinner

                if (euroflag2){
                   imp1 *= factor;  //in USD umrechnen da calcZoll USD erwartet
                }

                double[] b = tools.calcZoll(imp1,imp2,factor,_steuer, euroflag);

                if (!rech.getText().toString().isEmpty()){

                    betrag.setText(String.format(Locale.US,"%.2f",b[0]));
                    gesamt.setText(String.format(Locale.US,"%.2f",b[1]));
                    eust.setText(String.format(Locale.US,"%.2f",b[2]));
                    SummeAbg.setText(String.format(Locale.US,"%.2f",b[3]));
                    Summe.setText(String.format(Locale.US,"%.2f"+"$",imp1));
                    SummeEu.setText(String.format(Locale.US,"%.2f"+"€",imp1/factor));
                }



            }
        });

        vers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                invoice = tools.getNumber(rech.getText().toString());
                shipping = tools.getNumber(vers.getText().toString());

                // double imp1 = (tools.getNumber(rech.getText().toString())+tools.getNumber(vers.getText().toString()));  //exception at startup, acceptable

                double imp1 = invoice + shipping;
                double imp2  = ( tools.getNumber(String.valueOf(spin.getSelectedItem())) * 0.01) ;   //Spinner

                if (euroflag2){
                    imp1 *= factor;  //in USD umrechnen da calcZoll USD erwartet
                }

                double[] b = tools.calcZoll(imp1,imp2,factor,_steuer, euroflag);

                if (!rech.getText().toString().isEmpty()){

                    betrag.setText(String.format(Locale.US,"%.2f",b[0]));
                    gesamt.setText(String.format(Locale.US,"%.2f",b[1]));
                    eust.setText(String.format(Locale.US,"%.2f",b[2]));
                    SummeAbg.setText(String.format(Locale.US,"%.2f",b[3]));
                    Summe.setText(String.format(Locale.US,"%.2f"+"$",imp1));
                    SummeEu.setText(String.format(Locale.US,"%.2f"+"€",imp1/factor));
                }


            }
        });

        spin_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //((TextView) parent.getChildAt(0)).setTextSize(20);
                //ToDo text grösse
                //((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                // String selectedItem = parent.getItemAtPosition(position).toString();


                {

                    int[] textViewIDs = new int[] {R.id.textCurr1, R.id.textCurr2, R.id.textCurr3, R.id.textCurr4 };


                    for (int textViewID : textViewIDs) {

                        TextView tv = rootView.findViewById(textViewID);

                        if (String.valueOf(spin_currency.getSelectedItem()).equals(getString(R.string.arrEuro))) {
                            tv.setText(getString(R.string.euro));
                            euroflag = true;

                        } else {
                            tv.setText(getString(R.string.usdollar));
                            euroflag = false;
                        }
                    }


                    if(!rech.getText().toString().isEmpty()) {
                       // rech.setText(String.format(Locale.US,"%.2f", rechnung));
                        rech.performClick();  //event handler
                   }
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
                spin_currency.clearFocus();
            }
        });




        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //((TextView) parent.getChildAt(0)).setTextSize(20);
                //ToDo text grösse
                //((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                // String selectedItem = parent.getItemAtPosition(position).toString();


                    if(!rech.getText().toString().isEmpty()) {
                        // rech.setText(String.format(Locale.US,"%.2f", rechnung));
                        rech.performClick();  //event handler
                    }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
                spin.clearFocus();
            }
        });

        usdollar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double _euro = tools.getNumber(usdollar2.getText().toString())/factor;

                euro.setText(String.format(Locale.US,"%.2f",_euro));


            }
        });


        euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double _usd2 = tools.getNumber(euro.getText().toString())*factor;

                usdollar2.setText(String.format(Locale.US,"%.2f",_usd2));

            }
        });





        new Thread(new Runnable(){
            public void run(){

                //nur bei Flag  =1

                String InterOK = read("Inter","1");
                oldDatum = read("Datum", "01.01.2017");
                String _usd = read("USD", "1.0000");

                factor = tools.getNumber(_usd);

                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Update your UI
                        DatumEZB.setText(oldDatum);  //Only the original thread that created a view hierarchy can touch its views.
                        usdollar.setText(String.format(Locale.US,"%.4f", factor));
                    }
                });



                if (InterOK.equals("1")) {   //nur bei Internet erlaubt

                    // check nur einmal pro tag

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY); //* hh:mm:ss*/
                    newDatum = dateFormat.format(new java.util.Date());  //Warum????

                    //22.1 bug
                    if ((!newDatum.equals(oldDatum) || factor==1) && tools.isOnline() )  {  //nur wenn Datum neu oder factor =1 und Online

                        factor = tools.USDRate();

                        //https://stackoverflow.com/questions/5499809/how-to-check-a-string-is-not-null
                     //   if (str!=null && !str.equals("")) {

                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Update your UI
                                    DatumEZB.setText(newDatum);
                                    usdollar.setText(String.format(Locale.US,"%.4f", factor));
                                }
                            });

                        save("Datum", newDatum);
                        save("USD",String.format(Locale.US,"%.4f", factor) );
                    }
                }
            }
        }).start();

        return rootView;
    }

    ///ab hier die Methoden
    //TODO auslagern?

    //save preferences string
    private void save(String valueKey, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(valueKey, value);
        edit.apply();   //apply()
    }

    //read preferences string
    private String read(String valueKey, String valueDefault) {

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        return prefs.getString(valueKey, valueDefault);
    }




}

