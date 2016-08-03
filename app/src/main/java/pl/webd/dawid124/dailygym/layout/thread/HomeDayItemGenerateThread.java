package pl.webd.dawid124.dailygym.layout.thread;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.data.TExerciseData;
import pl.webd.dawid124.dailygym.database.data.THistoryData;
import pl.webd.dawid124.dailygym.database.data.TPlanData;
import pl.webd.dawid124.dailygym.database.data.TSeriesData;
import pl.webd.dawid124.dailygym.database.data.TTrainingData;
import pl.webd.dawid124.dailygym.database.engine.TSeriesEngine;
import pl.webd.dawid124.dailygym.database.engine.TTrainingEngine;
import pl.webd.dawid124.dailygym.layout.activity.TrainingActivity;
import pl.webd.dawid124.dailygym.layout.adapter.HomeItemAdapter;
import pl.webd.dawid124.dailygym.layout.fragment.HomeDayFragment;
import pl.webd.dawid124.dailygym.static_value.AppValue;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Java on 2016-07-19.
 */
public class HomeDayItemGenerateThread extends AsyncTask<Void, Void, Void> {
    private int mPageNumber;
    private Context ctx;
    private HomeDayFragment dayCtx;
    private Activity activity;
    private String date;
    private ViewGroup rootView;
    private ArrayList<TExerciseData> allExerciseList = new ArrayList<>();

    private ExpandableStickyListHeadersListView mListView;

    public HomeDayItemGenerateThread(Context ctx, HomeDayFragment dayCtx, String date, ViewGroup rootView, final int mPageNumber) {
        this.ctx = ctx;
        this.dayCtx = dayCtx;
        this.date = date;
        this.rootView = rootView;
        this.activity = (Activity) ctx;
        this.mPageNumber = mPageNumber;
        //this.linearLayout = (LinearLayout) rootView.findViewById(R.id.itemList);
    }

    public synchronized void generateItemView() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.home_day_fragment, null);
        rootView.removeAllViews();
        rootView.addView(view);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TrainingActivity.class);
                Intent i = new Intent(activity, TrainingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppValue.ADD_TO_HISTORY, true);
                bundle.putString(AppValue.DATE, date);
                bundle.putInt(AppValue.PAGE_NUMBER, mPageNumber);
                intent.putExtras(bundle);
                i.putExtras(bundle);

                activity.startActivityForResult(i, 1);
            }
        });

        TextView dateTextView = (TextView) rootView.findViewById(R.id.date);
        dateTextView.setText(date);

        HomeItemAdapter trainingHomeItemAdapter = new HomeItemAdapter(ctx, allExerciseList, date, this);

        mListView = (ExpandableStickyListHeadersListView) rootView.findViewById(R.id.list);
        mListView.setAdapter(trainingHomeItemAdapter);

        setItemListOnClickListener();
        try {
            Thread.sleep(0);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void setItemListOnClickListener() {
        mListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                if (mListView.isHeaderCollapsed(headerId)) {
                    mListView.expand(headerId);
                } else {
                    mListView.collapse(headerId);
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            List<EditText> seriesFieldsList = new ArrayList<>();

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long exerciseId) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                final List<TSeriesData> seriesList = allExerciseList.get(position).getSeriesList();
                builder.setTitle("Edycja")
                        .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                dialog.dismiss();
                                TSeriesEngine seriesEngine = new TSeriesEngine(ctx);
                                if (seriesEngine.updateSeriesForExerciseId(seriesList, seriesFieldsList)) {
                                    dayCtx.onResume();
                                }
                            }
                        });


                LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = new LinearLayout(ctx);
                layout.setOrientation(LinearLayout.VERTICAL);

                for (TSeriesData series : seriesList) {
                    View v = vi.inflate(R.layout.home_dialog_series_item, null);
                    final EditText weight = (EditText) v.findViewById(R.id.weightField);
                    TextView name = (TextView) v.findViewById(R.id.seriesName);
                    ImageButton minusBtn = (ImageButton) v.findViewById(R.id.minusWeight);
                    ImageButton plusBtn = (ImageButton) v.findViewById(R.id.plusWeight);

                    weight.setText(series.getWeight() + "");
                    name.setText(series.getName());

                    minusBtn.setOnClickListener(new Button.OnClickListener() {
                        public void onClick(View v) {
                            int number = Integer.parseInt(weight.getText().toString()) - 1;
                            weight.setText(number + "");
                        }
                    });
                    plusBtn.setOnClickListener(new Button.OnClickListener() {
                        public void onClick(View v) {
                            int number = Integer.parseInt(weight.getText().toString()) + 1;
                            weight.setText(number + "");
                        }
                    });

                    layout.addView(v);
                    seriesFieldsList.add(weight);
                }

                builder.setView(layout);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void getItemDate(String date) {
        TTrainingEngine trainingEngine = new TTrainingEngine(ctx);
        List<TTrainingData> trainingList;

        trainingList = trainingEngine.getTrainingsForDate(date);

        for (TTrainingData training : trainingList) {
            List<TPlanData> planList = training.getTrainingPlans();
            for (TPlanData plan : planList) {
                List<THistoryData> historyList = plan.getHistoryList();
                for (THistoryData history : historyList) {
                    List<TExerciseData> exerciseList = history.getExercise();
                    if (exerciseList.size() == 0) {
                        THistoryData historyData = new THistoryData();
                        historyData.setPlan(plan);
                        TExerciseData exerciseData = new TExerciseData();
                        exerciseData.setHistory(historyData);
                        allExerciseList.add(exerciseData);
                    }
                    for (TExerciseData exercise : exerciseList) {
                        allExerciseList.add(exercise);
                    }
                }
            }
        }
    }

    public void load() {
        getItemDate(date);
        generateItemView();
    }

    @Override
    protected Void doInBackground(Void... params) {
        getItemDate(date);

        generateItemView();

        return null;
    }

    public class createViewThread extends Thread {

        public createViewThread() {
        }

        public void run() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getItemDate(date);
                    generateItemView();
                }

                ;
            });


        }
    }
}
