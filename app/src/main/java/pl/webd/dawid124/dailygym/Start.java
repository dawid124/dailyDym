package pl.webd.dawid124.dailygym;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.webd.dawid124.dailygym.database.DB_SqlLiteDbHelper;
import pl.webd.dawid124.dailygym.layout.activity.HomeActivity;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        new goHome(this).execute();


        DB_SqlLiteDbHelper db = new DB_SqlLiteDbHelper(this);
        db.openDataBase();

        /*
        TExerciseTypeEngine engine = new TExerciseTypeEngine(this);
        List<TExerciseTypeData> lista = new ArrayList<TExerciseTypeData>();
        lista = engine.getAllExerciseType();
        TUserEngine userEngine = new TUserEngine(this);

        for(TExerciseTypeData exerciseList : lista){
            text.setText(text.getText() + exerciseList.getName() + "\n");
        }

        userEngine.createUser("Dawid");

        TUserEngine userEngine2 = new TUserEngine(this);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(userEngine2.getUuser().getCreatedDate());
        text.setText(text.getText() + s);

        TTrainingEngine trainingEngine = new TTrainingEngine(this);
        long traiingId = trainingEngine.creteTraining("training1","desc",true);
        TPlanEngine trainingPlanEngine = new TPlanEngine(this);
        trainingPlanEngine.createTrainingPlan("Plan A", traiingId);
*/
        String dateStr = "2016-05-08";
/*
            TTrainingEngine trainingEngine = new TTrainingEngine(this);
            List<TTrainingData> list = trainingEngine.getTrainingsForDate(dateStr);
      // System.out.println(list);
            trainingEngine.close();
*/
    }

    public class goHome extends AsyncTask<Void, Void, Void> {

        Activity activity;

        public goHome(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(activity, HomeActivity.class));
            activity.finish();
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

        }

    }
}
