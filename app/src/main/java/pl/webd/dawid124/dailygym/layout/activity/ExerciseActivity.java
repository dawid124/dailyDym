package pl.webd.dawid124.dailygym.layout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import java.util.WeakHashMap;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.animator.AnimationExecutor;
import pl.webd.dawid124.dailygym.layout.adapter.ExerciseItemAdapter;
import pl.webd.dawid124.dailygym.static_value.AppValue;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ExerciseActivity extends AppCompatActivity {
    private ExpandableStickyListHeadersListView mListView;
    private ExerciseItemAdapter exerciseItemAdapter;
    private WeakHashMap<View, Integer> mOriginalViewHeightPool = new WeakHashMap<View, Integer>();
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.exercise_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, CreateExerciseActivity.class));
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        mListView = (ExpandableStickyListHeadersListView) findViewById(R.id.list);
        mListView.setAnimExecutor(new AnimationExecutor(mOriginalViewHeightPool));
        exerciseItemAdapter = new ExerciseItemAdapter(this);
        mListView.setAdapter(exerciseItemAdapter);
        setItemListOnClickListener();
    }

    private long getPlanIdFromActivity(){
        long planId = -1;
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            planId = bundle.getLong(AppValue.PLAN_ID);
            return planId;
        }
        return planId;
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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long planId = getPlanIdFromActivity();
                if (planId != -1) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(AppValue.EXERCISE_ID, id);
                    returnIntent.putExtra(AppValue.PLAN_ID, planId);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }
}
