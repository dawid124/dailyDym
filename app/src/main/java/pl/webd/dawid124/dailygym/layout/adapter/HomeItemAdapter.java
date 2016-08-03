package pl.webd.dawid124.dailygym.layout.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.data.TExerciseData;
import pl.webd.dawid124.dailygym.database.data.TPlanData;
import pl.webd.dawid124.dailygym.database.data.TSeriesData;
import pl.webd.dawid124.dailygym.database.data.TTrainingData;
import pl.webd.dawid124.dailygym.layout.thread.HomeDayItemGenerateThread;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Java on 2016-05-16.
 */
public class HomeItemAdapter extends BaseAdapter implements
        StickyListHeadersAdapter {

    private final Context ctx;
    private HomeDayItemGenerateThread homeExerciseItem;
    private LayoutInflater mInflater;
    private List<TExerciseData> allExerciseList = new ArrayList<>();
    private String date;

    public HomeItemAdapter(Context context, List<TExerciseData> allExerciseList, String date, HomeDayItemGenerateThread homeExerciseItem) {
        this.date = date;
        this.ctx = context;
        this.mInflater = LayoutInflater.from(ctx);
        this.homeExerciseItem = homeExerciseItem;
        this.allExerciseList = allExerciseList;
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
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (allExerciseList.get(position).getHistory().getDate() != null) {
            if (convertView == null ){
                final TExerciseData exercise = allExerciseList.get(position);
                vi = mInflater.inflate(R.layout.training_item_list_fragment, null);

                LinearLayout seriesLL = (LinearLayout) vi.findViewById(R.id.seriesLL);
                seriesLL.removeAllViews();
                TextView trainingName = (TextView) vi.findViewById(R.id.exerciseName);
                trainingName.setTextColor(Color.BLACK);
                TextView trainingType = (TextView) vi.findViewById(R.id.exerciseType);
                trainingType.setTextColor(Color.BLACK);
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
                    int no = i +1;

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
                    seriesNo.setTextColor(Color.BLACK);
                    seriesNo.setText(no + "");
                    seriesNo.setTextSize(8);
                    seriesNo.setHeight(40);
                    seriesNo.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    seriesNo.setTextColor(Color.parseColor("#FF000000"));
                    seriesNo.setGravity(Gravity.CENTER);
                    seriesNo.setLayoutParams(FieldsPAram);
                    seriesNoRL.addView(seriesNo);


                    TextView seriesNmaeFields = new TextView(ctx);
                    seriesNmaeFields.setTextColor(Color.BLACK);
                    seriesNmaeFields.setText(series.getName() + " :");
                    seriesNmaeFields.setTextSize(11);
                    seriesNmaeFields.setHeight(40);
                    seriesNmaeFields.setMinimumWidth(120);
                    seriesNmaeFields.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    seriesNmaeFields.setPadding(40, 0, 20, 0);
                    seriesNmaeFields.setLayoutParams(FieldsPAram);
                    seriesNmaeLL.addView(seriesNmaeFields);


                    TextView seriesWeightFields = new TextView(ctx);
                    seriesWeightFields.setTextColor(Color.BLACK);
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
            }
        } else {
            vi = mInflater.inflate(R.layout.training_info_fragment, null);
        }


        return vi;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final TTrainingData training = allExerciseList.get(position).getHistory().getPlan().getTraining();
            final TPlanData plan = allExerciseList.get(position).getHistory().getPlan();
            convertView = mInflater.inflate(R.layout.home_header_training_fragment, null);
            final TextView headerTraining = (TextView) convertView.findViewById(R.id.header_plan);
            final TextView headerPlan = (TextView) convertView.findViewById(R.id.header_training);
            final TextView dateTextView = (TextView) convertView.findViewById(R.id.date);

            headerTraining.setText(training.getName());
            headerPlan.setText(plan.getName());
            dateTextView.setText(date);
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return allExerciseList.get(position).getHistory().getPlan().getId();
    }

    public void clear() {
        allExerciseList = new ArrayList<>(0);
        notifyDataSetChanged();
    }
}
