package pl.webd.dawid124.dailygym.layout.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.engine.TPlanEngine;
import pl.webd.dawid124.dailygym.static_value.AppValue;

public class CreatePlanActivity extends AppCompatActivity {
    private EditText nameField;
    private long trainingId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_plan_activity);

        nameField = (EditText) findViewById(R.id.planName);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            trainingId = bundle.getLong(AppValue.TRAINING_ID);
        }
    }

    public void addPlan(View view){
        TPlanEngine planEngine;

        String planName = nameField.getText().toString();
        if (planName.length() > 0) {
            planEngine = new TPlanEngine(this);
        } else {
            Toast.makeText(getBaseContext(), "Wprowadź nazwe planu", Toast.LENGTH_LONG).show();
            return;
        }

        planEngine.createPlan(planName, trainingId);

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
