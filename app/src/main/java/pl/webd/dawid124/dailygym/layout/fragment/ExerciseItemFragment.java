package pl.webd.dawid124.dailygym.layout.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.data.TExerciseTypeData;
import pl.webd.dawid124.dailygym.database.engine.TExerciseTypeEngine;

/**
 * Created by Java on 2016-05-17.
 */
public class ExerciseItemFragment extends Fragment {
    private Context ctx;
    private long exerciseId = -1;

    public ExerciseItemFragment(Context context, long exerciseId){
        ctx = context;
        this.exerciseId = exerciseId;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.exercise_item_list_fragment, container, false);
        ImageView exerciseImage = (ImageView) rootView.findViewById(R.id.exerciseImage);
        TextView exerciseName = (TextView) rootView.findViewById(R.id.exerciseName);
        TextView exerciseType = (TextView) rootView.findViewById(R.id.exerciseType);
        TextView exerciseDescription = (TextView) rootView.findViewById(R.id.exerciseDescription);

        TExerciseTypeEngine exerciseTypeEngine = new TExerciseTypeEngine(ctx);
        TExerciseTypeData exercise = exerciseTypeEngine.getExercise(exerciseId);

        exerciseName.setText(exercise.getName());
        exerciseDescription.setText(exercise.getDescriptions());
        exerciseType.setText(exercise.getType().stringValue());

        if(exercise.getImage() != null) {
            exerciseImage.setImageBitmap(exercise.getImage());
        }

        return rootView;
    }
}
