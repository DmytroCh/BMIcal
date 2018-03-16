package com.example.user.bmical;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;


public class BMIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConstraintLayout layout = findViewById(R.id.layout_bmi);
        final TextView bmiField=findViewById(R.id.bmi_field);
        Intent intent = getIntent();
        String bmi = intent.getStringExtra(MainActivity.bmiValue);

        if(Double.parseDouble(bmi) < 18.5 && Double.parseDouble(bmi) >= Config.MINBMI)
            layout.setBackgroundResource(R.color.below);
        else if(Double.parseDouble(bmi) >= 18.5 && Double.parseDouble(bmi) < 25)
            layout.setBackgroundResource(R.color.good);
        else if(Double.parseDouble(bmi) >= 25 && Double.parseDouble(bmi) < Config.MAXBMI)
            layout.setBackgroundResource(R.color.over);
        else
            layout.setBackgroundResource(R.color.wrong);


        bmiField.setText(bmi);
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
