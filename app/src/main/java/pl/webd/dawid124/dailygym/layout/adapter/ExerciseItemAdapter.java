package pl.webd.dawid124.dailygym.layout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.data.TExerciseTypeData;
import pl.webd.dawid124.dailygym.database.engine.TExerciseTypeEngine;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ExerciseItemAdapter extends BaseAdapter implements
        StickyListHeadersAdapter {

    private final Context mContext;
    private LayoutInflater mInflater;
    ArrayList<TExerciseTypeData> exerciseList;

    public ExerciseItemAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        TExerciseTypeEngine exerciseTypeEngine = new TExerciseTypeEngine(context);
        exerciseList = exerciseTypeEngine.getAllExerciseType();
    }

    @Override
    public int getCount() {
        return exerciseList.size();
    }

    @Override
    public Object getItem(int position) {
        return exerciseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return exerciseList.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final TExerciseTypeData exercise = exerciseList.get(position);
        View vi = convertView;

        if(convertView==null) {
            vi = mInflater.inflate(R.layout.exercise_item_list_fragment, null);
        }

        ImageView exerciseImage = (ImageView)vi.findViewById(R.id.exerciseImage);
        TextView exerciseName = (TextView) vi.findViewById(R.id.exerciseName);
        TextView exerciseType = (TextView) vi.findViewById(R.id.exerciseType);
        TextView exerciseDescription = (TextView) vi.findViewById(R.id.exerciseDescription);

        exerciseName.setText(exercise.getName());
        exerciseDescription.setText(exercise.getDescriptions());
        exerciseType.setText(exercise.getType().stringValue());

        if(exercise.getImage() != null) {
            exerciseImage.setImageBitmap(exercise.getImage());
        }
        return vi;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        final String type = exerciseList.get(position).getType().stringValue();
        View vi = convertView;

        if (convertView == null) {
            vi = mInflater.inflate(R.layout.exercise_header_fragment, null);
        }

        TextView headerText = (TextView) vi.findViewById(R.id.header_text);

        headerText.setText(type);
        return vi;
    }

    @Override
    public long getHeaderId(int position) {
        return exerciseList.get(position).getType().value();
    }

    public void clear() {
        exerciseList = new ArrayList<>(0);
        notifyDataSetChanged();
    }
}
