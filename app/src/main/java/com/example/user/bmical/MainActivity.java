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
    public static final String bmiValue="bmiValue";
    private BMI objectBMI;
    private boolean isImperial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        final EditText weight=findViewById(R.id.weight);
        final TextView weight_kg=findViewById(R.id.weight_kg);
        final EditText height=findViewById(R.id.height);
        final TextView height_cm=findViewById(R.id.height_cm);


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        weight.setText( sharedPref.getString("weight", ""), TextView.BufferType.EDITABLE);
        height.setText(sharedPref.getString("height",""), TextView.BufferType.EDITABLE);

        isImperial=isImperial();

        if(isImperial){
            weight_kg.setText(R.string.weight_lb);
            height_cm.setText(R.string.height_in);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean correct = isCorrectValues(weight.getText().toString(),height.getText().toString());
                if(correct)
                    objectBMI = createObjectBMI(weight.getText().toString(),height.getText().toString());
                else
                    objectBMI = null;
                openBMIActivity();
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

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
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
                final EditText weight=findViewById(R.id.weight);
                final EditText height=findViewById(R.id.height);
                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("weight", weight.getText().toString());
                editor.putString("height", height.getText().toString());
                editor.commit();
                Toast.makeText(this, R.string.saved, Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        //super.onSaveInstanceState(savedInstanceState);
        if(objectBMI != null){
            savedInstanceState.putString("lastWeight", String.valueOf(objectBMI.getWeight()));
            savedInstanceState.putString("lastHeight", String.valueOf(objectBMI.getHeight()));
        }
        else{
            final EditText weight=findViewById(R.id.weight);
            final EditText height=findViewById(R.id.height);
            savedInstanceState.putString("lastWeight", weight.getText().toString());
            savedInstanceState.putString("lastHeight", height.getText().toString());
        }


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final EditText weight=findViewById(R.id.weight);
        final EditText height=findViewById(R.id.height);
        weight.setText(savedInstanceState.getString("lastWeight"), TextView.BufferType.EDITABLE);
        height.setText(savedInstanceState.getString("lastHeight"), TextView.BufferType.EDITABLE);
    }

    public void openBMIActivity(){
        String toSend;
        if(objectBMI!=null && isCorrectValues(String.valueOf(objectBMI.getWeight()), String.valueOf(objectBMI.getHeight())))
            toSend=String.valueOf(objectBMI.countBMI());
        else
            toSend = "-1";
        Intent intent=new Intent(this, BMIActivity.class);
        intent.putExtra(bmiValue, toSend);
        startActivity(intent);
    }

    public void openAutorActivity(){
        Intent intent=new Intent(this, AutorActivity.class);
        startActivity(intent);
    }

    private boolean isCorrectValues(String weight, String height){
        boolean result=false;
        if(!weight.isEmpty() && !weight.equals(".")
                && !height.isEmpty() && !height.equals(".")){
            double w=Double.parseDouble(weight);
            double h=Double.parseDouble(height);
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
