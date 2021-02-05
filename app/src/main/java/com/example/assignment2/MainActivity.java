package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try { this.getSupportActionBar().hide(); } catch(NullPointerException e){}

        //create spinners
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.temp_units_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.temp_units_array, android.R.layout.simple_spinner_item);


        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        spinner1.setAdapter(adapter1);

        //create EditText
        final EditText input1 = (EditText) findViewById(R.id.input1);
        final EditText input2 = (EditText) findViewById(R.id.input2);

        //Changes data in the Right EditText after user enters data into Left EditText
        input1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        double input = Double.parseDouble(input1.getText().toString());
                        double output = TemperatureConverter(input, spinner1.getSelectedItemPosition(), spinner2.getSelectedItemPosition());
                        input2.setText(Double.toString(output));
                        return true;
                    } catch (java.lang.NumberFormatException e){
                            return true;
                        }

                }
                return false;
            }
        });

        //Changes data in the Left EditText after user enters data into Right EditText
        input2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                    double input = Double.parseDouble(input2.getText().toString());
                    double output = TemperatureConverter(input, spinner2.getSelectedItemPosition(), spinner1.getSelectedItemPosition());
                    input1.setText(Double.toString(output));
                    return true;
                } catch(java.lang.NumberFormatException e) {
                        return true;
                    }
                }
                return false;
            }
        });


        //create ImageButton
        ImageButton reverseButton = findViewById(R.id.SwapButton);

        //create listener, which waits for user to click on ImageButton and to run onClick event
        reverseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int holder;
                holder = spinner1.getSelectedItemPosition();
                spinner1.setSelection(spinner2.getSelectedItemPosition());
                spinner2.setSelection(holder);
            }
        });

        //looks for changes in the Left Spinner to Change output in the Right Text View
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                try {
                    double input = Double.parseDouble(input1.getText().toString());
                    double output = TemperatureConverter(input, spinner1.getSelectedItemPosition(), spinner2.getSelectedItemPosition());
                    input2.setText(Double.toString(output));
                } catch(java.lang.NumberFormatException e) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //looks for changes in the Right Spinner to Change output in the Left Text View
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                try {
                    double input = Double.parseDouble(input1.getText().toString());
                    double output = TemperatureConverter(input, spinner1.getSelectedItemPosition(), spinner2.getSelectedItemPosition());
                    input2.setText(Double.toString(output));
                } catch(java.lang.NumberFormatException e) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public static double TemperatureConverter(double input, int tempUnit1, int tempUnit2){

        if((tempUnit1 == 0)&&(tempUnit2 == 1)){
            return round(((input * 9/5) + 32), 2);
        }
        else if((tempUnit1 == 0)&&(tempUnit2 == 2)){
            return round((input + 273.15), 2);
        } else if((tempUnit1 == 1)&&(tempUnit2 == 0)){
            return round(((input - 32)*5/9),2);
        } else if((tempUnit1 == 1)&&(tempUnit2 == 2)){
            return round(((input - 32)*5/9+273.15),2);
        } else if((tempUnit1 == 2)&&(tempUnit2 ==  0)){
            return round((input - 273.15), 2);
        } else if((tempUnit1 == 2)&&(tempUnit2 == 1)){
            return round((input - 273.15)*9/5+32, 2);
        } else if((tempUnit1 == 0)&&(tempUnit2 == 0)){
            return input;
        } else if((tempUnit1 == 1)&&(tempUnit2 == 1)){
            return input;
        } else if((tempUnit1 == 2)&&(tempUnit2 == 2)){
          return input;
        };
        return 0;

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
