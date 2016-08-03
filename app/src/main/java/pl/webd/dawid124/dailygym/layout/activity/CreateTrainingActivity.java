package pl.webd.dawid124.dailygym.layout.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.data.TPlanData;
import pl.webd.dawid124.dailygym.database.engine.TTrainingEngine;

public class CreateTrainingActivity extends AppCompatActivity {

    private LinearLayout plansLL;
    private List<EditText> plansList = new ArrayList<>();
    private EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_training_activity);
        plansLL = (LinearLayout) findViewById(R.id.plansLL);
        addSeriesToActivity();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        nameField = (EditText) findViewById(R.id.trainingName);
        ImageView addPlanBtn = (ImageView) findViewById(R.id.addPlanBtn);
        addPlanBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                addSeriesToActivity();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void addSeriesToActivity(){
        LinearLayout.LayoutParams LLParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LLParam.setMargins(0,10,0,10);
        LinearLayout.LayoutParams FieldsPAram = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

        LinearLayout seriesItemLL = new LinearLayout(this);
        seriesItemLL.setOrientation(LinearLayout.HORIZONTAL);
        seriesItemLL.setPadding(30, 0, 30, 0);
        seriesItemLL.setBackgroundColor(Color.parseColor("#ffffffff"));
        seriesItemLL.setLayoutParams(LLParam);

        EditText planNameFields = new EditText(this);
        planNameFields.setHint("nazwa serii");
        planNameFields.setTextColor(Color.parseColor("#000000"));
        planNameFields.setTextSize(15);
        planNameFields.setPadding(20, 0, 0, 0);
        planNameFields.setLayoutParams(FieldsPAram);
        seriesItemLL.addView(planNameFields);

        plansLL.addView(seriesItemLL);

        plansList.add(planNameFields);
    }

    public void addTraining(View view){
        TTrainingEngine trainingEngine;
        List<TPlanData> pList = new ArrayList<>();
        String trainingName = nameField.getText().toString();
        if (trainingName.length() > 0) {
            trainingEngine = new TTrainingEngine(this);
        } else {
            Toast.makeText(getBaseContext(), "Wprowadź nazwe treningu", Toast.LENGTH_LONG).show();
            return;
        }
        for (int i=0; i < plansList.size(); i++){
            String name = plansList.get(i).getText().toString();
            if (name.length() > 0 ) {
                TPlanData plan = new TPlanData();
                plan.setName(name);
                pList.add(plan);
            } else {
                Toast.makeText(getBaseContext(), "Wprowadź nazwy planów", Toast.LENGTH_LONG).show();
                return;
            }
        }
        trainingEngine.createTraining(pList, nameField.getText().toString(), null);

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
