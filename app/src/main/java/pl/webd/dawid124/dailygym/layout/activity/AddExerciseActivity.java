package pl.webd.dawid124.dailygym.layout.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.data.TSeriesData;
import pl.webd.dawid124.dailygym.database.engine.TExerciseEngine;
import pl.webd.dawid124.dailygym.layout.fragment.ExerciseItemFragment;
import pl.webd.dawid124.dailygym.static_value.AppValue;

/**
 * Created by Java on 2016-05-16.
 */
public class AddExerciseActivity extends FragmentActivity {
    private long planId = -1;
    private long exerciseId = -1;
    private ExerciseItemFragment mFragment;
    private LinearLayout exerciseTypeLL;
    private LinearLayout exerciseSeriesLL;
    private List<EditText> sereisNameList = new ArrayList<>();
    private List<EditText> sereisWeightList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_activity);

        exerciseTypeLL = (LinearLayout) findViewById(R.id.trainingLL);
        exerciseSeriesLL = (LinearLayout) findViewById(R.id.exerciseSeriesLL);
        planId = getPlanIdFromActivity();
        startActivityForExerciseId();
        addSeriesToActivity();

        ImageView addSeries = (ImageView) findViewById(R.id.series_add_btn);
        addSeries.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                addSeriesToActivity();
            }
        });
    }

    private long getPlanIdFromActivity(){
        long id = -1;
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            id = bundle.getLong(AppValue.PLAN_ID);
            return id;
        }
        return id;
    }

    private void startActivityForExerciseId() {
        if (planId != -1){
            Intent intent = new Intent(this, ExerciseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong(AppValue.PLAN_ID, planId);
            intent.putExtras(bundle);
            this.startActivityForResult(intent, 1);
        }else {
            Toast.makeText(getBaseContext(), "Spróbuj ponownie", Toast.LENGTH_LONG).show();
        }
    }

    private void setExerciseToActivity(){
        mFragment = new ExerciseItemFragment(this,exerciseId);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(exerciseTypeLL.getId(), mFragment).commit();
    }

    private void addSeriesToActivity(){
        LinearLayout.LayoutParams LLParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LLParam.setMargins(0,0,0,0);
        LinearLayout.LayoutParams FieldsPAram = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

        LinearLayout seriesItemLL = new LinearLayout(this);
        seriesItemLL.setOrientation(LinearLayout.HORIZONTAL);
        seriesItemLL.setPadding(30, 0, 30, 0);
        seriesItemLL.setBackgroundColor(Color.parseColor("#ffffffff"));
        seriesItemLL.setLayoutParams(LLParam);

        EditText seriesNmaeFields = new EditText(this);
        seriesNmaeFields.setHint("nazwa serii");
        seriesNmaeFields.setTextSize(15);
        seriesNmaeFields.setPadding(20, 0, 0, 0);
        seriesNmaeFields.setLayoutParams(FieldsPAram);
        seriesItemLL.addView(seriesNmaeFields);

        EditText seriesWeightFields = new EditText(this);
        seriesWeightFields.setHint("ciężar");
        seriesWeightFields.setTextSize(15);
        seriesWeightFields.setInputType(InputType.TYPE_CLASS_NUMBER);
        seriesWeightFields.setPadding(20, 0, 0, 0);
        seriesWeightFields.setLayoutParams(FieldsPAram);
        seriesItemLL.addView(seriesWeightFields);

        LinearLayout divider = new LinearLayout(this);
        divider.setBackgroundResource(R.color.colorPrimary);
        divider.setMinimumHeight(5);

        exerciseSeriesLL.addView(seriesItemLL);
        exerciseSeriesLL.addView(divider);

        sereisNameList.add(seriesNmaeFields);
        sereisWeightList.add(seriesWeightFields);
    }

    public void addExerciseToPlan(View view){
        TExerciseEngine exerciseEngine = new TExerciseEngine(this);
        List<TSeriesData> seriesList = new ArrayList<>();
        for (int i=0; i < sereisNameList.size(); i++){
            TSeriesData series = new TSeriesData();
            series.setName(sereisNameList.get(i).getText().toString());
            series.setWeight(Long.parseLong(sereisWeightList.get(i).getText().toString()));
            seriesList.add(series);
        }
        exerciseEngine.addExerciseToBasePlan(exerciseId, planId, seriesList);

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                exerciseId = data.getLongExtra("exerciseId",-1);
                planId = data.getLongExtra("planId",-1);
                setExerciseToActivity();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getBaseContext(), "Anulowano", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
