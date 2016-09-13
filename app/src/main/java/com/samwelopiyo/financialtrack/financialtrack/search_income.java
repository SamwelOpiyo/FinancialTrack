package com.samwelopiyo.financialtrack.financialtrack;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
public class search_income extends AppCompatActivity {
    private DataBaseHelper db;
    private ListView listViewIncome;
    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_income);
        AutoCompleteTextView searchIncome = (AutoCompleteTextView) findViewById(R.id.searchIncome);
        listViewIncome = (ListView) findViewById(R.id.listViewIncome);
        db = new DataBaseHelper(this);
        disAllIncome();
        db.open();
        final Cursor c = db.getAllTitlesIncome();
        final CustomAdapterIncome adapter = new CustomAdapterIncome(this, c);
        listViewIncome.setAdapter(adapter);
        searchIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
        listViewIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                c.moveToPosition(position);
                final String updateIncomeID = c.getString(0);
                String updateIncomeAmount = c.getString(1);
                String updateIncomeSource = c.getString(2);
                String updateIncomeDate = c.getString(3);
                String updateIncomeTime = c.getString(4);
                String updateIncomePlace = c.getString(5);
                final AlertDialog.Builder subDialog;
//subdialog
                    subDialog = new AlertDialog.Builder(search_income.this)
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
                    LayoutInflater li = LayoutInflater.from(search_income.this);
                    View promptsView = li.inflate(R.layout.update_income, null);
                    AlertDialog.Builder mainDialog = new AlertDialog.Builder(search_income.this);
                    mainDialog.setView(promptsView);
                    mainDialog.setTitle("Update Income Record");
                    mainDialog.setIcon(R.drawable.mint);
                    final EditText updateIDIncome = (EditText) promptsView.findViewById(R.id.updateIDIncome);
                    final EditText updateAmountIncome = (EditText) promptsView.findViewById(R.id.updateAmountIncome);
                    final EditText updateSourceIncome = (EditText) promptsView.findViewById(R.id.updateSourceIncome);
                    final EditText updateDateIncome = (EditText) promptsView.findViewById(R.id.updateDateIncome);
                    final EditText updateTimeIncome = (EditText) promptsView.findViewById(R.id.updateTimeIncome);
                    final EditText updatePlaceIncome = (EditText) promptsView.findViewById(R.id.updatePlaceIncome);
                    updateIDIncome.setText(updateIncomeID);
                    updateAmountIncome.setText(updateIncomeAmount);
                    updateSourceIncome.setText(updateIncomeSource);
                    updateDateIncome.setText(updateIncomeDate);
                    updateTimeIncome.setText(updateIncomeTime);
                    updatePlaceIncome.setText(updateIncomePlace);
                    updateDateIncome.setFocusable(false);
                    updateTimeIncome.setFocusable(false);

                    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String myFormat = "MM/dd/yy"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                            updateDateIncome.setText(sdf.format(myCalendar.getTime()));
                        }

                    };
                    updateDateIncome.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(search_income.this, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });
                    updateTimeIncome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar mcurrentTime = Calendar.getInstance();
                            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                            int minute = mcurrentTime.get(Calendar.MINUTE);
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(search_income.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    updateTimeIncome.setText( selectedHour + ":" + selectedMinute);
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
                            if (updateIDIncome.getText().toString().trim().length() == 0 || updateAmountIncome.getText().toString().trim().length() == 0
                                    || updateSourceIncome.getText().toString().trim().length() == 0 || updateDateIncome.getText().toString().trim().length() == 0 || updateTimeIncome.getText().toString().trim().length() == 0
                                    || updatePlaceIncome.getText().toString().trim().length() == 0) {
                                subDialog.show();
                            } else {
                                AlertDialog.Builder update = new AlertDialog.Builder(search_income.this);
                                update.setTitle("Update Income Record.");
                                update.setMessage("Are you sure you want to update record " + updateIncomeID + " ?");
                                update.setCancelable(false);
                                update.setIcon(R.drawable.mint);
                                update.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        int rowId = Integer.parseInt(updateIncomeID);
                                        if (db.updateTitleIncome(rowId, updateAmountIncome.getText().toString(), updateSourceIncome.getText().toString(), updateDateIncome.getText().toString(), updateTimeIncome.getText().toString(), updatePlaceIncome.getText().toString())) {
                                            Toast.makeText(search_income.this, "Record updated successfully!", Toast.LENGTH_LONG).show();
                                            c.requery();
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            Toast.makeText(search_income.this, "Update Failed!", Toast.LENGTH_SHORT).show();
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

        listViewIncome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            String str = null;

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                c.moveToPosition(position);
                str = c.getString(0);
                Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(search_income.this);
                builder.setTitle("Delete Income Record.");
                builder.setMessage("Are you sure you want to delete record " + str + " ?");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.mint);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        int rowId = Integer.parseInt(c.getString(0)); //Column 0 of the cursor is the id
                        if (db.deleteTitleIncome(rowId)) {
                            Toast.makeText(search_income.this, "Record deleted successfully!", Toast.LENGTH_LONG).show();
                            c.requery();
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(search_income.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;


            }
        });
    }

    private void disAllIncome() {
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
        try {
            Cursor c = db.getAllTitlesIncome();
            if (c.getCount() == 0) {
                Toast.makeText(search_income.this, "No records have been entered.", Toast.LENGTH_SHORT).show();
            } else {
                if (c.moveToFirst()) {
                    do {
                        DisplayTitle(c);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        db.close();
    }

    private void DisplayTitle(Cursor c) {
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

    public void onBackPressedSearchIncome() {
        super.onBackPressed();
    }

    class CustomAdapterIncome extends CursorAdapter {
        // CursorAdapter will handle all the moveToFirst(), getCount() logic for you :)

        public CustomAdapterIncome(Context context, Cursor c) {
            super(context, c);
        }

        public void bindView(View view, Context context, Cursor c) {
            /*if(c.getPosition()%2==1) {
                view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
            else {
                view.setBackgroundColor(context.getResources().getColor(R.color.list_item_bg));
            }*/
            TextView listIncomeId = (TextView) view.findViewById(R.id.listIncomeId);
            listIncomeId.setText(c.getString(c.getColumnIndex(c.getColumnName(0))));

            TextView listIncomeAmount = (TextView) view.findViewById(R.id.listIncomeAmount);
            listIncomeAmount.setText(c.getString(c.getColumnIndex(c.getColumnName(1))));

            TextView listIncomeSource = (TextView) view.findViewById(R.id.listIncomeSource);
            listIncomeSource.setText(c.getString(c.getColumnIndex(c.getColumnName(2))));

            TextView listIncomeDate = (TextView) view.findViewById(R.id.listIncomeDate);
            listIncomeDate.setText(c.getString(c.getColumnIndex(c.getColumnName(3))));

            TextView listIncomeTime = (TextView) view.findViewById(R.id.listIncomeTime);
            listIncomeTime.setText(c.getString(c.getColumnIndex(c.getColumnName(4))));

            TextView listIncomePlace = (TextView) view.findViewById(R.id.listIncomePlace);
            listIncomePlace.setText(c.getString(c.getColumnIndex(c.getColumnName(5))));
        }

        public View newView(Context context, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            return inflater.inflate(R.layout.search_income_cell, parent, false);
        }
    }


}