package com.example.dilkidias.gramatica;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

import com.example.dilkidias.gramatica.Data.DataContract;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toast toast;
    private ScrollerViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Find the view pager that will allow the user to swipe between fragments

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager = (ScrollerViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();

        // just set viewPager
        SpringIndicator indicator = (SpringIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        fab.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_search) {
            Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(searchIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_search:
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.nav_newVerb:
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
                break;
            case R.id.nav_allVerbs:
                viewPager.setCurrentItem(0);
                break;
            case R.id.nav_allTenses:
                this.viewPager.setCurrentItem(1);
                break;
            case R.id.nav_gra_verbs:
                this.viewPager.setCurrentItem(0);
                break;
            case R.id.nav_gra_tenses:
                this.viewPager.setCurrentItem(1);
                break;
            case R.id.nav_set_preferences:
            break;
            case R.id.nav_set_deleteAllVerbs:
                showDeleteConfirmationDialog();
                break;
            case R.id.nav_set_default:
                setDefault();
                break;
        }// TODO: 10-Aug-17 set preferences

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setDefault() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_all_msg);
        builder.setPositiveButton(R.string.delete_all, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked the "Delete" button, so delete the pet.
                deleteAllData();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked the "Cancel" button, so dismiss the dialog
                        // and continue editing the pet
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteAllData() {
        Boolean s = false;
        // note: it has a return value!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            s = Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT &&
                    ((ActivityManager) MainActivity.this.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
        }

        // Show a toast message depending on whether or not the update was successful.
        if (s) {
            // Otherwise, the update was successful and we can display a toast.
            toastView(getString(R.string.successfully_delete_all_verbs));
        } else {
            // If no rows were affected, then there was an error with the update.
            toastView(getString(R.string.delete_all_verbs_failed));
        }
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllVerbs() {

        int rowsAffected = getContentResolver().delete(DataContract.DataEntry.VERBS_CONTENT_URI, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {

            // If no rows were affected, then there was an error with the update.
            toastView(getString(R.string.delete_all_verbs_failed));
        } else {
            // Otherwise, the update was successful and we can display a toast.
            toastView(getString(R.string.successfully_delete_all_verbs));
        }
    }

    private void showDeleteConfirmationDialog() {

        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_all_msg);
        builder.setPositiveButton(R.string.delete_all, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                // User clicked the "Delete" button, so delete the pet.
                deleteAllVerbs();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void toastView(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
