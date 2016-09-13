package com.samwelopiyo.financialtrack.financialtrack;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by root on 4/21/16.
 */
public class register_expenditure extends AppCompatActivity{
    private Button submitExpenditure;
    private Button resetExpenditure;
    private EditText amountEditText;
    private EditText whereSpentEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText placeSpentExpenditureEditText;
    private DataBaseHelper db;
    private String amountExpenditure;
    private String usage;
    private String dateExpenditure;
    private String timeExpenditure;
    private String placeExpenditure;
    private final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_expenditure);
        submitExpenditure=(Button)findViewById(R.id.submitExpenditure);
        resetExpenditure=(Button)findViewById(R.id.resetExpenditure);
        amountEditText=(EditText)findViewById(R.id.amountExpenditureEditText);
        whereSpentEditText=(EditText)findViewById(R.id.whereSpentEditText);
        dateEditText=(EditText)findViewById(R.id.dateExpenditureEditText);
        timeEditText=(EditText)findViewById(R.id.timeExpenditureEditText);
        placeSpentExpenditureEditText=(EditText)findViewById(R.id.placeSpentExpenditureEditText);
        db = new DataBaseHelper(this);
        dateEditText.setFocusable(false);
        timeEditText.setFocusable(false);

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(register_expenditure.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeEditText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 12 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(register_expenditure.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        resetExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEditText.setText("");
                whereSpentEditText.setText("");
                dateEditText.setText("");
                timeEditText.setText("");
                placeSpentExpenditureEditText.setText("");
            }
        });
        submitExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountExpenditure=amountEditText.getText().toString();
                usage=whereSpentEditText.getText().toString();
                dateExpenditure=dateEditText.getText().toString();
                timeExpenditure=timeEditText.getText().toString();
                placeExpenditure=placeSpentExpenditureEditText.getText().toString();
                insertExpenditure();
            }
        });

        //insertExpenditure();
        //disAll();
        //update();
        //dis(3);
        //del(5);
    }
    private void insertExpenditure(){
        if (amountEditText.getText().toString().trim().length() == 0 || whereSpentEditText.getText().toString().trim().length() == 0
                || dateEditText.getText().toString().trim().length() == 0 || placeSpentExpenditureEditText.getText().toString().trim().length() == 0){
            Toast.makeText(register_expenditure.this, "One or more of the Important Fields has been left blank.", Toast.LENGTH_LONG).show();
        }else {
            db.open();
            //long id;
        /*id =*/
            db.insertTitleExpenditure(
                    amountExpenditure,
                    usage,
                    dateExpenditure,
                    timeExpenditure,
                    placeExpenditure);
        /*id = db.insertTitleExpenditure(
                amountExpenditure,
                usage,
                dateExpenditure,
                timeExpenditure,
                placeExpenditure);*/
            System.out.println("bool211");
            db.close();
            Toast.makeText(this, "Record Entered Successfully",
                    Toast.LENGTH_LONG).show();
            amountEditText.setText("");
            whereSpentEditText.setText("");
            dateEditText.setText("");
            timeEditText.setText("");
            placeSpentExpenditureEditText.setText("");
        }
    }
    public void onBackPressedRegisterExpenditure(){
        super.onBackPressed();
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

}
