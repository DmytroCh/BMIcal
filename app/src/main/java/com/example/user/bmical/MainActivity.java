package com.example.user.bmical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText weight;
    private TextView weight_kg;
    private EditText height;
    private TextView height_cm;
    private LinearLayout layout;
    private BMI objectBMI;
    private boolean isImperial;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declareElements();
        isImperial=isImperial();
        setDatasFromSharedPreferences();
        setUnits();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               buttonOnClick(v);
            }
        });

        button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });


        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
    }

    private void buttonOnClick(View v){
        boolean correct = isCorrectValues(weight.getText().toString(),height.getText().toString());
        if(correct)
            objectBMI = createObjectBMI(weight.getText().toString(),height.getText().toString());
        else
            objectBMI = null;
        openBMIActivity();
    }

    private void declareElements(){
        button = (Button) findViewById(R.id.button);
        weight = (EditText) findViewById(R.id.weight);
        weight_kg = (TextView) findViewById(R.id.weight_kg);
        height = (EditText) findViewById(R.id.height);
        height_cm = (TextView) findViewById(R.id.height_cm);
        layout = (LinearLayout) findViewById(R.id.layout);
    }

    private void setUnits(){
        if(isImperial){
            weight_kg.setText(R.string.weight_lb);
            height_cm.setText(R.string.height_in);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.three_dot_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.autor:
                openAutorActivity();
                break;
            case R.id.save:
                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Config.SP_WEIGHT, weight.getText().toString());
                editor.putString(Config.SP_HEIGHT, height.getText().toString());
                editor.commit();
                Toast.makeText(this, R.string.saved, Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    protected void hideKeyboard(View view){
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setDatasFromSharedPreferences(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        weight.setText(sharedPref.getString(Config.SP_WEIGHT, Config.DEFAULT_VALUE), TextView.BufferType.EDITABLE);
        height.setText(sharedPref.getString(Config.SP_HEIGHT,Config.DEFAULT_VALUE), TextView.BufferType.EDITABLE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        super.onSaveInstanceState(savedInstanceState);

        String w;
        String h;

        if(objectBMI != null){
            w = String.valueOf(objectBMI.getWeight());
            h = String.valueOf(objectBMI.getHeight());
        }
        else{
            w = weight.getText().toString();
            h = height.getText().toString();
        }
        savedInstanceState.putString(Config.IS_WEIGHT, w);
        savedInstanceState.putString(Config.IS_HEIGHT, h);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        weight.setText(savedInstanceState.getString(Config.IS_WEIGHT), TextView.BufferType.EDITABLE);
        height.setText(savedInstanceState.getString(Config.IS_HEIGHT), TextView.BufferType.EDITABLE);
    }

    public void openBMIActivity(){
        String weight;
        String height;
        String toSend = Config.ERROR_CODE;
        boolean isCorrectValues;

        if(objectBMI != null) {
            weight = String.valueOf(objectBMI.getWeight());
            height = String.valueOf(objectBMI.getHeight());
            isCorrectValues = isCorrectValues(weight, height);

            if(isCorrectValues) {
                toSend = String.valueOf(objectBMI.countBMI());
            }
        }
        BMIActivity.start(this,toSend);
    }

    public void openAutorActivity(){
        AutorActivity.start(this);
    }

    private boolean isCorrectValues(String weight, String height){
        double w;
        double h;
        boolean result=false;

        if(!weight.isEmpty() && !weight.equals(Config.POINT)
                && !height.isEmpty() && !height.equals(Config.POINT)){
            w = Double.parseDouble(weight);
            h = Double.parseDouble(height);
            if(!isImperial){
                if(h<Config.MAXHEIGHT_CM && h>Config.MINHEIGHT_CM && w<Config.MAXWEIGHT_KG && w>Config.MINWEIGHT_KG){
                    result=true;
                }
            }
            else{
                if(h<Config.MAXHEIGHT_IN && h>Config.MINHEIGHT_IN && w<Config.MAXWEIGHT_LB && w>Config.MINWEIGHT_LB){
                    result=true;
                }
            }
        }
        return result;
    }

    private boolean isImperial(){
        boolean result=false;
        String country = Locale.getDefault().getISO3Country();
        for(int i = 0; i < Config.COUNTRIES.length;i++){
            if(country.equals(Config.COUNTRIES[i]))
                result=true;
        }
        return result;
    }

    private BMI createObjectBMI(String weight, String height){
        if(isImperial)
            return new BmiImp(Double.parseDouble(weight), Double.parseDouble(height));
        else
            return new BmiMet(Double.parseDouble(weight), Double.parseDouble(height));
    }
}
