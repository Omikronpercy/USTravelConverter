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

import android.content.Intent;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



//TODO Backup wie?
//ToDo FEATURE REQ  auf recycler view umbauen
//ToDo keyboard view


//ToDo rechnen in eingabe
//ToDo nachkommastellen für custom

//ToDo Activity action view
//ToDO what if internet but no ecb?
//ToDo save and read in Importtools sowie usd und datum schreiben




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setTheme(R.style.AppTheme_NoActionBar); //Theme for splash
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

 //     ViewPager mImageViewPager = (ViewPager) findViewById(R.id.pager);

        //MB tab nur strich
             TabLayout tabLayout = findViewById(R.id.tabs);

 //     tabLayout.setupWithViewPager(mImageViewPager, true);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent3 = new Intent();
                settingsIntent3.setClass(this, user_setting.class);
                startActivity(settingsIntent3);

                ///  clearForm((ViewGroup) findViewById(R.id.gridLayout));
                return true;


            case R.id.action_settings2:
                Intent settingsIntent = new Intent();
                settingsIntent.setClass(this, InfoActivity.class);
                startActivity(settingsIntent);

                ///  clearForm((ViewGroup) findViewById(R.id.gridLayout));
                return true;
            case R.id.action_settings3:

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

 //deleted  PlaceholderFragment class

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


                switch (position) {

                    case 0:

                        return new tab1_conv_card();

                    case 1:

                        return new tab2_import();

                    default:
                        return null;
                }


        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CONVERT";
                case 1:
                    return "IMPORT";

            }
            return null;
        }
    }


// --Commented out by Inspection START (07.05.2018 21:38):
// /*   @Override
//    public void onResume(){
//        super.onResume();
//
//        // put your code here...
//
//    }
//*/
//    //read preferences string
//    private String read(String valueKey, String valueDefault) {
//
//        SharedPreferences prefs = PreferenceManager
//                .getDefaultSharedPreferences(getApplicationContext());
//        return prefs.getString(valueKey, valueDefault);
//    }
// --Commented out by Inspection STOP (07.05.2018 21:38)



}
