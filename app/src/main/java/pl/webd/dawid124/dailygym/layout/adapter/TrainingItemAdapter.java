package pl.webd.dawid124.dailygym.layout.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.data.TExerciseData;
import pl.webd.dawid124.dailygym.database.data.THistoryData;
import pl.webd.dawid124.dailygym.database.data.TPlanData;
import pl.webd.dawid124.dailygym.database.data.TSeriesData;
import pl.webd.dawid124.dailygym.database.data.TTrainingData;
import pl.webd.dawid124.dailygym.database.engine.TPlanEngine;
import pl.webd.dawid124.dailygym.layout.activity.AddExerciseActivity;
import pl.webd.dawid124.dailygym.layout.activity.CreatePlanActivity;
import pl.webd.dawid124.dailygym.layout.activity.CreateTrainingActivity;
import pl.webd.dawid124.dailygym.static_value.AppValue;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Java on 2016-05-16.
 */
public class TrainingItemAdapter extends BaseAdapter implements
        StickyListHeadersAdapter {

    private final Context ctx;
    private LayoutInflater mInflater;
    List<TPlanData> planList;
    ArrayList<TExerciseData> allExerciseList = new ArrayList<>();

    public TrainingItemAdapter(Context context) {
        ctx = context;
        mInflater = LayoutInflater.from(ctx);
        TPlanEngine planEngine = new TPlanEngine(context);
        planList = planEngine.getAllPlans();


        for(TPlanData plan : planList){
            List<THistoryData> historyList = plan.getHistoryList();
            for(THistoryData history : historyList){
                List<TExerciseData> exerciseList = history.getExercise();
                if (exerciseList.size() == 0){
                    THistoryData historyData = new THistoryData();
                    historyData.setPlan(plan);
                    TExerciseData exerciseData = new TExerciseData();
                    exerciseData.setHistory(historyData);
                    allExerciseList.add(exerciseData);
                }
                for(TExerciseData exercise : exerciseList){
                    allExerciseList.add(exercise);
                }
            }
        }
    }

    @Override
    public int getCount() {
        return allExerciseList.size();
    }

    @Override
    public Object getItem(int position) {
        return allExerciseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return allExerciseList.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (allExerciseList.get(position).getHistory().getDate() != null) {
            final TExerciseData exercise = allExerciseList.get(position);
            boolean prevIsNullExercise = false;
            if (position > 0){
                prevIsNullExercise = allExerciseList.get(position - 1).getHistory().getDate() == null;
            }
            if (convertView == null || !prevIsNullExercise){
                vi = mInflater.inflate(R.layout.training_item_list_fragment, null);
            }

            LinearLayout seriesLL = (LinearLayout) vi.findViewById(R.id.seriesLL);
            seriesLL.removeAllViews();
            TextView trainingName = (TextView) vi.findViewById(R.id.exerciseName);
            TextView trainingType = (TextView) vi.findViewById(R.id.exerciseType);
            ImageView trainingImage = (ImageView) vi.findViewById(R.id.exerciseImage);





            LinearLayout seriesItemLL = new LinearLayout(ctx);
            seriesItemLL.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams LLParamWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            seriesItemLL.setLayoutParams(LLParamWrap);

            LinearLayout seriesNoLL = new LinearLayout(ctx);
            seriesNoLL.setOrientation(LinearLayout.VERTICAL);
            seriesNoLL.setGravity(Gravity.RIGHT);
            seriesItemLL.addView(seriesNoLL);

            LinearLayout seriesNmaeLL = new LinearLayout(ctx);
            seriesNmaeLL.setOrientation(LinearLayout.VERTICAL);
            seriesNmaeLL.setGravity(Gravity.RIGHT);
            seriesItemLL.addView(seriesNmaeLL);

            LinearLayout seriesWeightLL = new LinearLayout(ctx);
            seriesWeightLL.setOrientation(LinearLayout.VERTICAL);
            seriesWeightLL.setGravity(Gravity.RIGHT);
            seriesItemLL.addView(seriesWeightLL);



            List<TSeriesData> exerciseList = exercise.getSeriesList();
            for (int i = 0; i < exerciseList.size(); i++ ){
                TSeriesData series = exerciseList.get(i);
                TextView seriesNumber = new TextView(ctx);
                TextView seriesWeight = new TextView(ctx);
                int no = i +1;

                /*
                seriesNumber.setText(String.valueOf(series.getName()) + " : " + String.valueOf(series.getWeight()));
                //seriesNumber.setTextColor(Color.parseColor("#000000"));
                seriesNumber.setTextSize(12);
                seriesNumber.setPadding(20, 0, 0, 20);

*/






                LinearLayout.LayoutParams LLParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                LLParam.setMargins(0, 10, 0, 10);
                LinearLayout.LayoutParams FieldsPAram = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);


                RelativeLayout seriesNoRL = new RelativeLayout(ctx);
                RelativeLayout.LayoutParams RLParam = new RelativeLayout.LayoutParams(41, 41);
                seriesNoRL.setLayoutParams(RLParam);
                seriesNoRL.setPadding(4, 4, 4, 4);
                seriesNoLL.addView(seriesNoRL);

                ImageView image = new ImageView(ctx);
                image.setBackgroundResource(R.drawable.circle2);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                seriesNoRL.addView(image);

                TextView seriesNo = new TextView(ctx);
                seriesNo.setText(no + "");
                seriesNo.setTextSize(8);
                seriesNo.setHeight(40);
                seriesNo.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                seriesNo.setTextColor(Color.parseColor("#FF000000"));
                seriesNo.setGravity(Gravity.CENTER);
                seriesNo.setLayoutParams(FieldsPAram);
                seriesNoRL.addView(seriesNo);




                TextView seriesNmaeFields = new TextView(ctx);
                seriesNmaeFields.setText(series.getName() + " :");
                seriesNmaeFields.setTextSize(11);
                seriesNmaeFields.setHeight(40);
                seriesNmaeFields.setMinimumWidth(120);
                seriesNmaeFields.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                seriesNmaeFields.setPadding(40, 0, 20, 0);
                seriesNmaeFields.setLayoutParams(FieldsPAram);
                seriesNmaeLL.addView(seriesNmaeFields);


                TextView seriesWeightFields = new TextView(ctx);
                seriesWeightFields.setText(series.getWeight() + "");
                seriesWeightFields.setTextSize(11);
                seriesWeightFields.setHeight(40);
                seriesWeightFields.setTypeface(null, Typeface.BOLD);
                seriesWeightFields.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                seriesWeightFields.setPadding(30, 0, 20, 0);
                seriesWeightFields.setLayoutParams(FieldsPAram);
                seriesWeightLL.addView(seriesWeightFields);
            }
            seriesLL.addView(seriesItemLL);

            if(exercise.getImage() != null) {
                trainingImage.setImageBitmap(exercise.getImage());
            }

            trainingName.setText(exercise.getName());
            trainingType.setText(exercise.getType().stringValue());
//        exerciseDescription.setText((int) plan.getId());
        } else {
            vi = mInflater.inflate(R.layout.training_info_fragment, null);
        }


        return vi;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        final TTrainingData training = allExerciseList.get(position).getHistory().getPlan().getTraining();
        final TPlanData plan = allExerciseList.get(position).getHistory().getPlan();
        View vi = convertView;

        if (convertView == null) {
            vi = mInflater.inflate(R.layout.training_header_fragment, null);
        }

        final TextView headerTraining = (TextView) vi.findViewById(R.id.header_plan);
        final TextView headerPlan = (TextView) vi.findViewById(R.id.header_training);
        final ImageView addBtn = (ImageView) vi.findViewById(R.id.training_add_btn);

        headerTraining.setText(training.getName());
        headerPlan.setText(plan.getName());

        addMenuListner(plan, training, addBtn);

        return vi;
    }

    @Override
    public long getHeaderId(int position) {
        return allExerciseList.get(position).getHistory().getPlan().getId();
    }

    public void clear() {
        allExerciseList = new ArrayList<>(0);
        notifyDataSetChanged();
    }

    private void newTraining(){
        Intent intent = new Intent(ctx, CreateTrainingActivity.class);
        ctx.startActivity(intent);
    }

    private void newPlan(long trainingId){
        Intent intent = new Intent(ctx, CreatePlanActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(AppValue.TRAINING_ID, trainingId);
        intent.putExtras(bundle);
        ctx.startActivity(intent);
    }

    private void addExercise(long planId) {
        Intent intent = new Intent(ctx, AddExerciseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(AppValue.PLAN_ID, planId);
        intent.putExtras(bundle);
        ((Activity) ctx).startActivityForResult(intent, 1);
        //ctx.startActivity(intent);
    }

    private void deletePlan(long planId){
        TPlanEngine planEngine = new TPlanEngine(ctx);
        planEngine.deletePlan(planId);
        Activity activity = (Activity) ctx;
        activity.finish();
        activity.startActivity(activity.getIntent());
    }

    private  void addMenuListner(final TPlanData plan, final TTrainingData training, final ImageView addBtn){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(ctx, addBtn);
                popup.getMenuInflater().inflate(R.menu.new_training_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case  R.id.new_exercise:
                                addExercise(plan.getId());
                                break;
                            case R.id.new_plan:
                                newPlan(training.getId());
                                break;
                            case R.id.new_training:
                                newTraining();
                                break;
                            case R.id.delete_plan:
                                deletePlan(plan.getId());
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

}
