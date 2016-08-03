package pl.webd.dawid124.dailygym.database;

import android.provider.BaseColumns;

/**
 * Created by Java on 2016-05-09.
 */
public class ColumnName {
    /**
     * TBL_EXERCISE_TYPE
     */
    public static abstract class ExerciseType implements BaseColumns {
        public static final String ID = "EXERCISE_ID";
        public static final String NAME = "NAME";
        public static final String DESCRIPTIONS = "DESCRIPTIONS";
        public static final String IMAGE= "IMAGE";
        public static final String TYPE = "TYPE";
    }

    /**
     * TBL_USER
     */
    public static abstract class User implements BaseColumns {
        public static final String ID = "USER_ID";
        public static final String NAME = "NAME";
        public static final String DATE = "DATE_CREATED";

    }
    /**
     * TBL_TRAINING
     */
    public static abstract class Training implements BaseColumns {
        public static final String ID = "TRAINING_ID";
        public static final String NAME = "NAME";
        public static final String DESCRITPIONS = "DESCRITPIONS";
        public static final String ACTIVE = "ACTIVE";
        public static final String DATE = "DATE";
    }

    /**
     * TBL_PLAN
     */
    public static abstract class Plan implements BaseColumns {
        public static final String ID = "PLAN_ID";
        public static final String NAME = "NAME";
        public static final String TRAINING_FK = "TRAINING_FK";

    }

    /**
     * TBL_EXERCISES
     */
    public static abstract class Exercise implements BaseColumns {
        public static final String ID = "EXERCISES_ID";
        public static final String HISTORY_FK = "HISTORY_FK";
        public static final String EXERCISE_TYPE_FK = "EXERCISE_TYPE_FK";
    }

    /**
     * TBL_EXERCISES_SERIES
     */
    public static abstract class ExrciseSeries implements BaseColumns {
        public static final String ID = "EXERCISES_SERIES_ID";
        public static final String SERIES_TYPE_FK = "SERIES_TYPE_FK";
        public static final String EXERCISES_FK = "EXERCISES_FK";
    }

    /**
     * TBL_SERIES_TYPE
     */
    public static abstract class SeriesType implements BaseColumns {
        public static final String ID = "SERIES_TYPE_ID";
        public static final String NUMBER = "NUMBER";
        public static final String WEIGHT = "WEIGHT";
    }

    /**
     * TBL_HISTORY
     */
    public static abstract class History implements BaseColumns {
        public static final String ID = "HISTORY_ID";
        public static final String PLAN_FK = "PLAN_FK";
        public static final String DATE = "DATE";
    }
}
