/*<one line to give the program's name and a brief idea of what it does.>
        Copyright (C) <2019>  <Github: Omikronpercy>

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>.


*/


package uscartools.USTravelConverter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;




public class user_setting extends Activity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting);

        //View View = inflater.inflate(R.layout.user_setting, container, false);

        final EditText Usertext1= findViewById(R.id.editTextE1);
        final EditText Usertext2= findViewById(R.id.editTextE2);
        final EditText editfaktor1= findViewById(R.id.editFaktor1);
        final EditText Steuersatz=findViewById(R.id.editTextSt);
        final Button ButtonOK= findViewById(R.id.button3);

        final SwitchCompat switchTabs= findViewById(R.id.switch1);
        final SwitchCompat switchInternet= findViewById(R.id.switch2);

        //Context context = getApplicationContext();

        String checkflag = read("Check","1");  //cardview
        String interflag = read("Inter", "1");  //internet

        if (checkflag.equals("1")){
            switchTabs.setChecked(true);
        }
        else {
            switchTabs.setChecked(false);
        }


        if (interflag.equals("1")){
            switchInternet.setChecked(true);
        }
        else {
            switchInternet.setChecked(false);
        }

        String unit1 = read("Unit1","Unit1");
        String unit2 = read("Unit2","Unit2");
        final String userfaktor1 = read("userFaktor1","1.0000");
        String steuer = read("EUSt","19");

        Usertext1.setText(unit1);
        Usertext2.setText(unit2);
        editfaktor1.setText(userfaktor1);
        Steuersatz.setText(steuer);

        switchTabs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                String _check;

                if(isChecked){
                    _check="1";
                }else{
                    _check="0";
                }
                save("Check",_check);
            }
        });

        switchInternet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                String _check;

                if(isChecked){
                    _check="1";
                }else{
                    _check="0";
                }
                save("Inter",_check);
            }
        });


        ButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // was machen;
                String _unit1= Usertext1.getText().toString();
                String _unit2=Usertext2.getText().toString();
                String _userfaktor1=editfaktor1.getText().toString();
                String _steuer=Steuersatz.getText().toString();
                save("Unit1",_unit1);
                save("Unit2",_unit2);
                save("EUSt",_steuer);




                if (TextUtils.isEmpty(_userfaktor1)){
                    _userfaktor1="1.0000";
                }
                save("userFaktor1",_userfaktor1);

                if (TextUtils.isEmpty(_steuer)){
                    _steuer="19";
                }
                save("EUSt",_steuer);


                //getActivity().finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
                //startActivity(intent); //nur fuer tab

            }
        });




    }




    //save preferences string
    private void save(String valueKey, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(valueKey, value);
        edit.apply();
    }

    //read preferences string
    private String read(String valueKey, String valueDefault) {

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        return prefs.getString(valueKey, valueDefault);
    }


}