package pl.webd.dawid124.dailygym.layout.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.converter.MyDate;
import pl.webd.dawid124.dailygym.layout.thread.HomeDayItemGenerateThread;
import pl.webd.dawid124.dailygym.static_value.AppValue;

/**
 * Created by Java on 2016-05-10.
 */
public class HomeDayFragment extends Fragment {
    private static Context ctx;
    private HomeDayFragment dayCtx;
    private ViewGroup rootView;
    private String date = null;
    private int mPageNumber;
    private int addDays;
    private boolean visible;
    private boolean isLoad;

    public static HomeDayFragment create(final int position, Context context) {
        ctx = context;
        HomeDayFragment fragment = new HomeDayFragment();
        fragment.addHomeDayContext(fragment);
        Bundle args = new Bundle();
        args.putInt(AppValue.ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void addHomeDayContext(HomeDayFragment fragment){
        dayCtx = fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(AppValue.ARG_POSITION);
        addDays = mPageNumber - AppValue.NUM_PAGES/2;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        date = MyDate.addDays(MyDate.getToday(), addDays);
        rootView = (ViewGroup) inflater.inflate(R.layout.progress_bar_fragment, container, false);
        return rootView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.visible = isVisibleToUser;
        if (isVisibleToUser) {
            if (date != null && !isLoad) {
                onResume();
            }
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        if (visible) {
            loadData();
        }
    }

    public void loadData() {
        if (MyDate.getToday().equals(date)) {
            HomeDayItemGenerateThread exerciseItem = new HomeDayItemGenerateThread(ctx, dayCtx, date, rootView, mPageNumber);
            exerciseItem.load();
            this.isLoad = true;
        } else {
            HomeDayItemGenerateThread exerciseItem = new HomeDayItemGenerateThread(ctx, dayCtx, date, rootView, mPageNumber);
            exerciseItem.new createViewThread().start();
            this.isLoad = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(ctx, "Wprowadż nazwę", Toast.LENGTH_LONG).show();
    }
}
