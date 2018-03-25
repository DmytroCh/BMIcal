package com.example.user.bmical;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;


public class BMIActivity extends AppCompatActivity {
    private static final String BMI_VALUE ="BMI_VALUE";
    private ConstraintLayout layout;
    private TextView bmiField;
    private Intent intent;
    String bmi;

    public static void start(Context context, String bmi) {
        Intent starter = new Intent(context, BMIActivity.class);
        starter.putExtra(BMI_VALUE, bmi);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        declareElements();
        declareValues();
        doFormat();
        showBMI();
    }



    private void declareElements(){
        layout = (ConstraintLayout) findViewById(R.id.layout_bmi);
        bmiField = (TextView) findViewById(R.id.bmi_field);
    }

    private void declareValues(){
        intent = getIntent();
        bmi = intent.getStringExtra(BMI_VALUE);
    }

    private void doFormat(){
        if(Double.parseDouble(bmi) < Config.BOTTOM_BMI_BORDER && Double.parseDouble(bmi) >= Config.MINBMI)
            StyleBMI.BELOW.format(layout);
        else if(Double.parseDouble(bmi) >= Config.BOTTOM_BMI_BORDER && Double.parseDouble(bmi) < Config.TOP_BMI_BORDER)
            StyleBMI.GOOD.format(layout);
        else if(Double.parseDouble(bmi) >= Config.TOP_BMI_BORDER && Double.parseDouble(bmi) < Config.MAXBMI)
            StyleBMI.OVER.format(layout);
        else
            StyleBMI.WRONG.format(layout);
    }

    private void showBMI(){
        double support = Double.parseDouble(bmi);
        if(support < 0)
            bmiField.setText(R.string.wrong_value);
        else{
            DecimalFormat df1 = new DecimalFormat(".##");
            bmiField.setText(String.valueOf(df1.format(support)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
