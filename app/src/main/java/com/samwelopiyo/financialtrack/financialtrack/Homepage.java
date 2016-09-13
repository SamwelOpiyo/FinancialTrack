package com.samwelopiyo.financialtrack.financialtrack;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button submitIncome;
    private Button resetIncome;
    private EditText amountEditText;
    private EditText sourceEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText placeAcquiredIncomeEditText;
    private DataBaseHelper db;
    private String amountIncome;
    private String source;
    private String dateIncome;
    private String timeIncome;
    private String placeIncome;


    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        submitIncome = (Button) findViewById(R.id.submitIncome);
        resetIncome = (Button) findViewById(R.id.resetIncome);
        amountEditText = (EditText) findViewById(R.id.amountIncomeEditText);
        sourceEditText = (EditText) findViewById(R.id.sourceIncomeEditText);
        dateEditText = (EditText) findViewById(R.id.dateIncomeEditText);
        timeEditText = (EditText) findViewById(R.id.timeIncomeEditText);
        placeAcquiredIncomeEditText = (EditText) findViewById(R.id.placeAcquiredIncomeEditText);
        db = new DataBaseHelper(this);



        dateEditText.setFocusable(false);
        timeEditText.setFocusable(false);

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Homepage.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Homepage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeEditText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 12 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });




        resetIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEditText.setText("");
                sourceEditText.setText("");
                dateEditText.setText("");
                timeEditText.setText("");
                placeAcquiredIncomeEditText.setText("");
            }
        });
        submitIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountIncome = amountEditText.getText().toString();
                source = sourceEditText.getText().toString();
                dateIncome = dateEditText.getText().toString();
                timeIncome = timeEditText.getText().toString();
                placeIncome = placeAcquiredIncomeEditText.getText().toString();
                insertIncome();
            }
        });

        //insertIncome();
        //disAll();
        //update();
        //dis(3);
        //del(5);


    }

    private void insertIncome() {
        if (amountEditText.getText().toString().trim().length() == 0 || sourceEditText.getText().toString().trim().length() == 0
                || dateEditText.getText().toString().trim().length() == 0 || placeAcquiredIncomeEditText.getText().toString().trim().length() == 0){
            Toast.makeText(Homepage.this, "One or more of the Important Fields has been left blank.", Toast.LENGTH_LONG).show();
        }else {
            db.open();
            //long id;
        /*id = */
            db.insertTitleIncome(
                    amountIncome,
                    source,
                    dateIncome,
                    timeIncome,
                    placeIncome);
        /*id = db.insertTitleIncome(
                amountIncome,
                source,
                dateIncome,
                timeIncome,
                placeIncome);*/
            System.out.println("bool211");
            db.close();
            Toast.makeText(this, "Record Entered Successfully",
                    Toast.LENGTH_LONG).show();
            amountEditText.setText("");
            sourceEditText.setText("");
            dateEditText.setText("");
            timeEditText.setText("");
            placeAcquiredIncomeEditText.setText("");
        }
    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_record_income) {
            Intent recordIncome=new Intent(Homepage.this, Homepage.class);
            startActivity(recordIncome);
        } else if (id == R.id.nav_record_expenditure) {
            Intent recordExpenditure=new Intent(Homepage.this, register_expenditure.class);
            startActivity(recordExpenditure);
        } else if (id == R.id.nav_settings) {
            Intent settings=new Intent(Homepage.this, settings.class);
            startActivity(settings);

        } else if (id == R.id.nav_search_income) {
            Intent searchIncome=new Intent(Homepage.this, search_income.class);
            startActivity(searchIncome);

        } else if (id == R.id.nav_search_expenditure){
            Intent searchExpenditure=new Intent(Homepage.this, search_expenditure.class);
            startActivity(searchExpenditure);

        } else if (id == R.id.nav_send_mail) {

        }else if (id == R.id.nav_share){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}

