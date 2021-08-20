package com.example.bmicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import static android.R.layout.simple_spinner_item;

public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView mHeightEditText;
    private TextView mWeightEditText;
    private TextView mResultTextView, mCommentTextView;
    private boolean isMale;
    private double height, weight;
    private Calculator.unit weightUnit, heightUnit;
//    public static String EXTRA_RESULT =".ResultsActivity.extra.RESULT";
//    public static String EXTRA_FLAG =".ResultsActivity.extra.FLAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        String str = (String) intent.getStringExtra("extra_results");
        changeTheme(str);

        setContentView(R.layout.activity_results);

//        Show results from MainActivity
        mResultTextView = findViewById(R.id.results_value);
        mCommentTextView = findViewById(R.id.result_comment);

        mResultTextView.setText(intent.getStringExtra("extra_results"));
        mCommentTextView.setText(intent.getStringExtra("extra_flag"));

//      grab Edit text inputs
        mHeightEditText = findViewById(R.id.height_editText);
        mWeightEditText = findViewById(R.id.weight_editText);
        mResultTextView = findViewById(R.id.results_value);


        isMale = true;
        weightUnit = Calculator.unit.KG;
        heightUnit = Calculator.unit.CM;


//       create gender Spinner
        Spinner genderSpinner = findViewById(R.id.gender_spinner);
        if (genderSpinner != null) {
            createSpinner(genderSpinner, R.array.gender_array);
            genderSpinner.setOnItemSelectedListener(this);
        }

//        create height spinner
        Spinner heightSpinner = findViewById(R.id.height_spinner);
        if (heightSpinner != null) {
            createSpinner(heightSpinner, R.array.height_array);
            heightSpinner.setOnItemSelectedListener(this);
        }

//        create Weight spinner
        Spinner weightSpinner = findViewById(R.id.weight_spinner);
        if (weightSpinner != null) {
            createSpinner(weightSpinner, R.array.weight_array);
            weightSpinner.setOnItemSelectedListener(this);
        }

    }

    private void changeTheme(String results) {
        int theme;
        Double res = Double.parseDouble(results);
        if (res < 18.5) {
            theme = R.style.Theme_BMIcalculator_dark_blue;
        } else if (res >= 18.5 && res <= 24.9) {
            theme = R.style.Theme_BMIcalculator_dark_green;
        } else if (res >= 25.0 && res <= 29.9) {
            theme = R.style.Theme_BMIcalculator_dark_yellow;
        } else {
            theme = R.style.Theme_BMIcalculator_dark_red;
        }
        setTheme(theme);
    }

    //Implement back button
    public void back(View view) {
        onBackPressed();
    }


    public void createSpinner(Spinner spinner, int string_array) {
        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), string_array, simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), "Oops! " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()) {
            case R.id.gender_spinner:
                isMale = setGender(parent.getItemAtPosition(position).toString());
                break;

            case R.id.height_spinner:
                heightUnit = setUnit(parent.getItemAtPosition(position).toString());
                break;

            case R.id.weight_spinner:
                weightUnit = setUnit(parent.getItemAtPosition(position).toString());
                break;
            default:
                break;
        }
    }

    private Calculator.unit setUnit(String unitString) {
        Calculator.unit unit;
        switch (unitString.toLowerCase()) {
            case "cm":
                unit = Calculator.unit.CM;
                break;
            case "kg":
                unit = Calculator.unit.KG;
                break;
            case "lb":
                unit = Calculator.unit.LB;
                break;
            default:
                unit = Calculator.unit.FT;
                break;
        }
        return unit;
    }

    private boolean setGender(String gender) {
        return gender.toLowerCase().equals("male");
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void calculateBMI(View view) {
        if (mHeightEditText.getText().toString().equals("")) {
            displayToast("Enter a valid height!");
        } else {
            height = Double.parseDouble(mHeightEditText.getText().toString());
        }

        if (mWeightEditText.getText().toString().equals("")) {
            displayToast("Enter a valid weight!");
        } else {
            weight = Double.parseDouble(mWeightEditText.getText().toString());
        }
        Calculator mCalculator = new Calculator();
        double value = Math.round(mCalculator.compute(isMale, weight, weightUnit, height, heightUnit));
        Calculator.flag flag = mCalculator.getFlag(value);
        String res = Integer.toString((int) value);

        HashMap<Calculator.flag, String> flagStringHashMap = new HashMap<Calculator.flag, String>();
        flagStringHashMap.put(Calculator.flag.HW, "You are in great shape");
        flagStringHashMap.put(Calculator.flag.OW, "You are not in good shape time for some exercise");
        flagStringHashMap.put(Calculator.flag.UW, "Time for some more healthy snacks");
        flagStringHashMap.put(Calculator.flag.OB, "You're in bad shape time to make lifestyle changes");

        String flagString = flagStringHashMap.get(flag);

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("extra_results", res);
        intent.putExtra("extra_flag", flagString);
        startActivity(intent);

    }


}

