package pl.webd.dawid124.dailygym.layout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.WeakHashMap;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.animator.AnimationExecutor;
import pl.webd.dawid124.dailygym.database.engine.TExerciseEngine;
import pl.webd.dawid124.dailygym.database.engine.THistoryEngine;
import pl.webd.dawid124.dailygym.layout.adapter.TrainingItemAdapter;
import pl.webd.dawid124.dailygym.static_value.AppValue;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class TrainingActivity extends AppCompatActivity implements AbsListView.OnScrollListener{
    private ExpandableStickyListHeadersListView mListView;
    private TrainingItemAdapter trainingItemAdapter;
    private WeakHashMap<View, Integer> mOriginalViewHeightPool = new WeakHashMap<View, Integer>();
    private ImageView backgroundImage;
    private int lastTopValue = 0;
    private boolean isAddToHistory;
    private String date;
    private int mPageNumber;
    private Context ctx;
    private ViewGroup header;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.ctx = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.training_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, CreateTrainingActivity.class);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LayoutInflater inflater = getLayoutInflater();
        THistoryEngine historyEngine = new THistoryEngine(this);
        int countActiveTraining = historyEngine.getCountActivePlan();

        if (mListView == null) {
            mListView = (ExpandableStickyListHeadersListView) findViewById(R.id.list);
            mListView.setAnimExecutor(new AnimationExecutor(mOriginalViewHeightPool));
            trainingItemAdapter = new TrainingItemAdapter(this);
            mListView.setAdapter(trainingItemAdapter);

            header = (ViewGroup) inflater.inflate(R.layout.training_header_image, mListView, false);
            backgroundImage = (ImageView) header.findViewById(R.id.listHeaderImage);

            mListView.addHeaderView(header, null, false);

        } else {
            trainingItemAdapter = new TrainingItemAdapter(this);
            mListView.setAdapter(trainingItemAdapter);
        }


        if (countActiveTraining > 0) {
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            p.setAnchorId(View.NO_ID);
            fab.setLayoutParams(p);
            fab.setVisibility(View.GONE);
        } else {
            ViewGroup info = (ViewGroup) inflater.inflate(R.layout.training_header_no_training_fragment, mListView, false);
            mListView.addHeaderView(info, null, false);
        }

        setItemListOnClickListener();
        isAddToHistory();
    }

    private void isAddToHistory(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            isAddToHistory = bundle.getBoolean(AppValue.ADD_TO_HISTORY);
            date = bundle.getString(AppValue.DATE);
            mPageNumber = bundle.getInt(AppValue.PAGE_NUMBER);
        } else {
            isAddToHistory = false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(getBaseContext(), "Dodano ćwieczenie do planu", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    private void setItemListOnClickListener() {
        mListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                if (isAddToHistory) {
                    THistoryEngine historyEngine = new THistoryEngine(ctx);
                    if (!historyEngine.isPlanOnTheHistroyList(date, headerId)) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(AppValue.PLAN_ID, headerId);
                        returnIntent.putExtra(AppValue.DATE, date);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Trening jest już na liście", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (mListView.isHeaderCollapsed(headerId)) {
                        mListView.expand(headerId);
                    } else {
                        mListView.collapse(headerId);
                    }
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long exerciseId) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage("Wybierz co chcesz zrobić")
                        .setTitle("Opcje")
                        .setPositiveButton("Edytuj", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Usuń", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                dialog.dismiss();
                                TExerciseEngine exerciseEngine = new TExerciseEngine(ctx);
                                exerciseEngine.deleteExerciseFromPlan(exerciseId);
                                onResume();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Rect rect = new Rect();
        backgroundImage.getLocalVisibleRect(rect);
        if (lastTopValue != rect.top) {
            lastTopValue = rect.top;
            backgroundImage.setY((float) (rect.top / 2.0));
        }
    }
}
