package com.samwelopiyo.financialtrack.financialtrack;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by root on 5/5/16.
 */
public class search_expenditure extends AppCompatActivity {
    private DataBaseHelper db;
    private ListView listViewExpenditure;
    private final Calendar myCalendar = Calendar.getInstance();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_expenditure);
        AutoCompleteTextView searchExpenditure=(AutoCompleteTextView) findViewById(R.id.searchExpenditure);
        listViewExpenditure=(ListView) findViewById(R.id.listViewExpenditure);
        db = new DataBaseHelper(this);
        disAllExpenditure();



        db.open();
        final Cursor c = db.getAllTitlesExpenditure();
        final CustomAdapter adapter = new CustomAdapter(this, c);
        listViewExpenditure.setAdapter(adapter);
        listViewExpenditure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                c.moveToPosition(position);
                final String updateExpenditureID=c.getString(0);
                String updateExpenditureAmount = c.getString(1);
                String updateExpenditureSource = c.getString(2);
                String updateExpenditureDate = c.getString(3);
                String updateExpenditureTime = c.getString(4);
                String updateExpenditurePlace = c.getString(5);
                final AlertDialog.Builder subDialog;
//subdialog
                subDialog = new AlertDialog.Builder(search_expenditure.this)
                        .setTitle("Incomplete!")
                        .setMessage("Please enter all the details!!!")
                        .setCancelable(false)
                        .setIcon(R.drawable.mint)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlg2, int which) {
                                dlg2.cancel();
                            }
                        });
//maindialog
                LayoutInflater li = LayoutInflater.from(search_expenditure.this);
                View promptsView = li.inflate(R.layout.update_expenditure, null);
                AlertDialog.Builder mainDialog = new AlertDialog.Builder(search_expenditure.this);
                mainDialog.setView(promptsView);
                mainDialog.setTitle("Update Expenditure Record");
                mainDialog.setIcon(R.drawable.mint);
                final EditText updateIDExpenditure = (EditText) promptsView.findViewById(R.id.updateIDExpenditure);
                final EditText updateAmountExpenditure = (EditText) promptsView.findViewById(R.id.updateAmountExpenditure);
                final EditText updateUsageExpenditure = (EditText) promptsView.findViewById(R.id.updateUsageExpenditure);
                final EditText updateDateExpenditure = (EditText) promptsView.findViewById(R.id.updateDateExpenditure);
                final EditText updateTimeExpenditure = (EditText) promptsView.findViewById(R.id.updateTimeExpenditure);
                final EditText updatePlaceExpenditure = (EditText) promptsView.findViewById(R.id.updatePlaceExpenditure);
                updateIDExpenditure.setText(updateExpenditureID);
                updateAmountExpenditure.setText(updateExpenditureAmount);
                updateUsageExpenditure.setText(updateExpenditureSource);
                updateDateExpenditure.setText(updateExpenditureDate);
                updateTimeExpenditure.setText(updateExpenditureTime);
                updatePlaceExpenditure.setText(updateExpenditurePlace);
                updateDateExpenditure.setFocusable(false);
                updateTimeExpenditure.setFocusable(false);

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        updateDateExpenditure.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                updateDateExpenditure.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(search_expenditure.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                updateTimeExpenditure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(search_expenditure.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                updateTimeExpenditure.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, false);//Yes 12 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });
                mainDialog.setCancelable(false)
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog dialog = mainDialog.create();
                dialog.show();
                Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (updateIDExpenditure.getText().toString().trim().length() == 0 || updateAmountExpenditure.getText().toString().trim().length() == 0
                                || updateUsageExpenditure.getText().toString().trim().length() == 0 || updateDateExpenditure.getText().toString().trim().length() == 0 || updateTimeExpenditure.getText().toString().trim().length() == 0
                                || updatePlaceExpenditure.getText().toString().trim().length() == 0) {
                            subDialog.show();


                        } else {
                            AlertDialog.Builder update = new AlertDialog.Builder(search_expenditure.this);
                            update.setTitle("Update Expenditure Record.");
                            update.setMessage("Are you sure you want to update record " + updateExpenditureID + " ?");
                            update.setCancelable(false);
                            update.setIcon(R.drawable.mint);
                            update.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    int rowId = Integer.parseInt(updateExpenditureID);
                                    if (db.updateTitleExpenditure(rowId, updateAmountExpenditure.getText().toString(), updateUsageExpenditure.getText().toString(), updateDateExpenditure.getText().toString(), updateTimeExpenditure.getText().toString(), updatePlaceExpenditure.getText().toString())) {
                                        Toast.makeText(search_expenditure.this, "Record updated successfully!", Toast.LENGTH_LONG).show();
                                        c.requery();
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(search_expenditure.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            update.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert = update.create();
                            alert.show();
                            dialog.cancel();
                        }
                    }
                });
            }
        });
        listViewExpenditure.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            String str=null;
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                c.moveToPosition(position);
                str=c.getString(0);
                Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(search_expenditure.this);
                builder.setTitle("Delete Expenditure Record.");
                builder.setMessage("Are you sure you want to delete record "+str+" ?");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.mint);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        int rowId = Integer.parseInt(c.getString(0)); //Column 0 of the cursor is the id
                        if (db.deleteTitleExpenditure(rowId)){
                            Toast.makeText(search_expenditure.this, "Record deleted successfully!", Toast.LENGTH_LONG).show();
                            c.requery();
                            adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(search_expenditure.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                } );
                AlertDialog alert=builder.create();
                alert.show();
                return true;


            }
        });

    }
    private void disAllExpenditure(){
        db.open();
        /*Cursor c = db.getAllTitles();
        if (c.moveToFirst())
        {
            do {
            	System.out.println("bool2");
                DisplayTitle(c);
            } while (c.moveToNext());
        }
        else
        	System.out.println("boo3l");*/
        try{
            Cursor c = db.getAllTitlesExpenditure();
            if (c.getCount() == 0){
                Toast.makeText(search_expenditure.this, "No records have been entered.", Toast.LENGTH_SHORT).show();
            }else {
                if (c.moveToFirst()) {
                    do {
                        DisplayTitle(c);
                    } while (c.moveToNext());
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
        db.close();
    }
    private void DisplayTitle(Cursor c)
    {
        System.out.println("bool");
        Toast.makeText(this,
                "ID: " + c.getString(0) + "\n" +
                        "AMOUNT: " + c.getString(1) + "\n" +
                        "SOURCE: " + c.getString(2) + "\n" +
                        "DATE:  " + c.getString(3) + "\n" +
                        "TIME: " + c.getString(4) + "\n" +
                        "PLACE: " + c.getString(5),
                Toast.LENGTH_LONG).show();
    }
    public void onBackPressedSearchExpenditure(){
        super.onBackPressed();
    }


    class CustomAdapter extends CursorAdapter {
        // CursorAdapter will handle all the moveToFirst(), getCount() logic for you :)

        public CustomAdapter(Context context, Cursor c) {
            super(context, c);
        }

        public void bindView(View view, Context context, Cursor c) {
            /*if(c.getPosition()%2==1) {
                view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
            else {
                view.setBackgroundColor(context.getResources().getColor(R.color.list_item_bg));
            }*/
            TextView listExpenditureId = (TextView) view.findViewById(R.id.listExpenditureId);
            listExpenditureId.setText(c.getString(c.getColumnIndex(c.getColumnName(0))));

            TextView listExpenditureAmount = (TextView) view.findViewById(R.id.listExpenditureAmount);
            listExpenditureAmount.setText(c.getString(c.getColumnIndex(c.getColumnName(1))));

            TextView listExpenditureUsage = (TextView) view.findViewById(R.id.listExpenditureUsage);
            listExpenditureUsage.setText(c.getString(c.getColumnIndex(c.getColumnName(2))));

            TextView listExpenditureDate = (TextView) view.findViewById(R.id.listExpenditureDate);
            listExpenditureDate.setText(c.getString(c.getColumnIndex(c.getColumnName(3))));

            TextView listExpenditureTime = (TextView) view.findViewById(R.id.listExpenditureTime);
            listExpenditureTime.setText(c.getString(c.getColumnIndex(c.getColumnName(4))));

            TextView listExpenditurePlace = (TextView) view.findViewById(R.id.listExpenditurePlace);
            listExpenditurePlace.setText(c.getString(c.getColumnIndex(c.getColumnName(5))));
        }

        public View newView(Context context, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            return inflater.inflate(R.layout.search_expenditure_cell, parent, false);
        }
    }
}
