package com.example.arijit.loginapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class User extends AppCompatActivity {
    //Intent intent = new Intent(this,MyListAdapter.class);
    //Fragment fragment = new MyListAdapter();
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private Date blueDate;
    private Date greenDate;
    private Date colDate;
    final Intent fback = new Intent(this,feedback.class);


    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -3);
        blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 4);
        greenDate = cal.getTime();
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 6);
        colDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable orange = new ColorDrawable(getResources().getColor(R.color.orange));

            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setBackgroundDrawableForDate(orange, colDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
                if (formatter.format(greenDate).equals(formatter.format(date)))
                {
                    //updateEventListView();
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(User.this);
                //builderSingle.setIcon(R.drawable.ic_launcher);
                builderSingle.setTitle("Today's Assignment Deadlines");
                //builderSingle.setView(layout);


                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        User.this,
                        android.R.layout.simple_list_item_1);
                //arrayAdapter.add("CS207");
                arrayAdapter.add("CS213");
                //arrayAdapter.add("CS215");
                //arrayAdapter.add("EE101");
                //arrayAdapter.add("Gatti");

                builderSingle.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(
                        arrayAdapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = arrayAdapter.getItem(which);
                                //System.out.println(which);
                                LinearLayout layout = new LinearLayout(User.this);
                                final TextView input = new TextView(User.this);
                                input.setText("Please complete assignment on heaps and priority queues");

                                input.setPadding(10, 5, 5, 5);
                                AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                        User.this);
                                builderInner.setMessage(strName);
                                builderInner.setTitle("Assignment");
                                builderInner.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {


                                                dialog.dismiss();
                                            }
                                        });
                                builderInner.setView(input);

                                builderInner.show();
                            }
                        });
                //builderSingle.getWindow().setLayout(600, 400);
                builderSingle.show();

                caldroidFragment.refreshView();

                //Fragment fragment = new MainFragment();
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(User.this, android.R.layout.activity_my_list_adapter, R.array.arraye);
                //setListAdapter(adapter);

                //ListView lv = getListView();
            }
                if (formatter.format(blueDate).equals(formatter.format(date)))
                {
                    //updateEventListView();
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(User.this);
                    //builderSingle.setIcon(R.drawable.ic_launcher);
                    builderSingle.setTitle("Today's feedback Deadlines");
                    //builderSingle.setView(layout);


                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            User.this,
                            android.R.layout.simple_list_item_1);
                    arrayAdapter.add("CS207");
                    //arrayAdapter.add("CS213");
                    arrayAdapter.add("CS215");
                    //arrayAdapter.add("EE101");
                    //arrayAdapter.add("Gatti");

                    builderSingle.setNegativeButton(
                            "cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builderSingle.setAdapter(
                            arrayAdapter,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String strName = arrayAdapter.getItem(which);
                                    //System.out.println(which);
                                          startActivity(fback);
                                    AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                            User.this);
                                    builderInner.setMessage(strName);
                                    builderInner.setTitle("Feedback deadlines");


                                    builderInner.setPositiveButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {

                                                    System.out.println("working");

                                                    dialog.dismiss();
                                                }
                                            });
                                    builderInner.show();
                                }
                            });
                    //builderSingle.getWindow().setLayout(600, 400);
                    builderSingle.show();

                    caldroidFragment.refreshView();

                    //Fragment fragment = new MainFragment();
                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(User.this, android.R.layout.activity_my_list_adapter, R.array.arraye);
                    //setListAdapter(adapter);

                    //ListView lv = getListView();
                }
                if (formatter.format(colDate).equals(formatter.format(date)))
                {
                    //updateEventListView();
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(User.this);
                    //builderSingle.setIcon(R.drawable.ic_launcher);
                    builderSingle.setTitle("Today's Deadlines");
                    //builderSingle.setView(layout);


                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            User.this,
                            android.R.layout.simple_list_item_1);
                    arrayAdapter.add("CS207");
                    arrayAdapter.add("CS213");
                    arrayAdapter.add("CS215");
                    arrayAdapter.add("EE101");
                    //arrayAdapter.add("Gatti");

                    builderSingle.setNegativeButton(
                            "cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builderSingle.setAdapter(
                            arrayAdapter,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String strName = arrayAdapter.getItem(which);
                                    //System.out.println(which);

                                    AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                            User.this);
                                    builderInner.setMessage(strName);
                                    builderInner.setTitle("Misc");
                                    
                                    builderInner.setPositiveButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {


                                                    dialog.dismiss();
                                                }
                                            });
                                    builderInner.show();
                                }
                            });
                    //builderSingle.getWindow().setLayout(600, 400);
                    builderSingle.show();

                    caldroidFragment.refreshView();

                    //Fragment fragment = new MainFragment();
                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(User.this, android.R.layout.activity_my_list_adapter, R.array.arraye);
                    //setListAdapter(adapter);

                    //ListView lv = getListView();
                }
        }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        final TextView textView = (TextView) findViewById(R.id.textview);

        final Button customizeButton = (Button) findViewById(R.id.customize_button);

        // Customize the calendar
        customizeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (undo) {
                    customizeButton.setText(getString(R.string.customize));
                    textView.setText("");

                    // Reset calendar
                    caldroidFragment.clearDisableDates();
                    caldroidFragment.clearSelectedDates();
                    caldroidFragment.setMinDate(null);
                    caldroidFragment.setMaxDate(null);
                    caldroidFragment.setShowNavigationArrows(true);
                    caldroidFragment.setEnableSwipe(true);
                    caldroidFragment.refreshView();
                    undo = false;
                    return;
                }

                // Else
                undo = true;
                customizeButton.setText(getString(R.string.undo));
                Calendar cal = Calendar.getInstance();

                // Min date is last 7 days
                cal.add(Calendar.DATE, -7);
                Date minDate = cal.getTime();

                // Max date is next 7 days
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 14);
                Date maxDate = cal.getTime();

                // Set selected dates
                // From Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                Date fromDate = cal.getTime();

                // To Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 3);
                Date toDate = cal.getTime();

                // Set disabled dates
                ArrayList<Date> disabledDates = new ArrayList<Date>();
                for (int i = 5; i < 8; i++) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, i);
                    disabledDates.add(cal.getTime());
                }

                // Customize
                caldroidFragment.setMinDate(minDate);
                caldroidFragment.setMaxDate(maxDate);
                caldroidFragment.setDisableDates(disabledDates);
                caldroidFragment.setSelectedDates(fromDate, toDate);
                caldroidFragment.setShowNavigationArrows(false);
                caldroidFragment.setEnableSwipe(false);

                caldroidFragment.refreshView();

                // Move to date
                // cal = Calendar.getInstance();
                // cal.add(Calendar.MONTH, 12);
                // caldroidFragment.moveToDate(cal.getTime());

               /* String text = "Today: " + formatter.format(new Date()) + "\n";
                text += "Min Date: " + formatter.format(minDate) + "\n";
                text += "Max Date: " + formatter.format(maxDate) + "\n";
                text += "Select From Date: " + formatter.format(fromDate)
                        + "\n";
                text += "Select To Date: " + formatter.format(toDate) + "\n";
                for (Date date : disabledDates) {
                    text += "Disabled Date: " + formatter.format(date) + "\n";
                }

                textView.setText(text);*/
            }
        });

        Button showDialogButton = (Button) findViewById(R.id.show_dialog_button);

        final Bundle state = savedInstanceState;
        showDialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Setup caldroid to use as dialog
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);

                // If activity is recovered from rotation
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                if (state != null) {
                    dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getSupportFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                } else {
                    // Setup arguments
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }

                dialogCaldroidFragment.show(getSupportFragmentManager(),
                        dialogTag);
            }
        });
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

}


