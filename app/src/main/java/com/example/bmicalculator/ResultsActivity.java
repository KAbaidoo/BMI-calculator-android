package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextView mHeightEditText, mWeightEditText,mResultTextView,mCommentTextView;
    private boolean isMale;
    private double height, weight;
    private Calculator.unit weightUnit, heightUnit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

//      grab Edit text inputs
        mHeightEditText = findViewById(R.id.height_editText);
        mWeightEditText = findViewById(R.id.weight_editText);
        mResultTextView = findViewById(R.id.results_value);
        mCommentTextView = findViewById(R.id.result_comment);
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



    public void createSpinner(Spinner spinner, int string_array) {
        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), string_array, android.R.layout.simple_spinner_item);
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

        Log.d("BMI Results:", Double.toString(mCalculator.compute(isMale,weight,weightUnit,height,heightUnit)));
    }
}
