package pl.webd.dawid124.dailygym.enum_type;

/**
 * Created by Java on 2016-05-16.
 */
public enum ExerciseTypeEnum {

        KLATKA(0,"Klatka piersiowa"),
        A_BICEPS(1, "Biceps"),
        C_BARKI(2, "Barki"),
        BRZUCH(3, "Brzuch"),
        PLECY(4, "Plecy"),
        TRICEPS(5, "Triceps"),
        NOGI(6, "Nogi");
    
        private final int _value;
        public final String _valueStr;
        ExerciseTypeEnum(int value, String s){_value = value; _valueStr = s;}


        public int value(){return _value;}
        public String valueStr(){return _valueStr;}


        public String stringValue(){return ExerciseTypeEnum.Get(ExerciseTypeEnum.Get(_value));}

    public static ExerciseTypeEnum Get(int i)
    {
        switch(i)
        {
            case 0:
                return ExerciseTypeEnum.KLATKA;
            case 1:
                return ExerciseTypeEnum.A_BICEPS;
            case 2:
                return ExerciseTypeEnum.C_BARKI;
            case 3:
                return ExerciseTypeEnum.BRZUCH;
            case 4:
                return ExerciseTypeEnum.PLECY;
            case 5:
                return ExerciseTypeEnum.TRICEPS;
            case 6:
                return ExerciseTypeEnum.NOGI;
            default:
                return null;
        }
    }

    public static String Get(ExerciseTypeEnum type)
    {
        switch(type)
        {
            case KLATKA:
                return "Klatka piersiowa";
            case A_BICEPS:
                return "Biceps";
            case C_BARKI:
                return "Barki";
            case BRZUCH:
                return "Brzuch";
            case PLECY:
                return "Plecy";
            case TRICEPS:
                return "Triceps";
            case NOGI:
                return "Nogi";
            default:
                return "";
        }
    }

    public static ExerciseTypeEnum Get(String type)
    {
        switch(type)
        {
            case "Klatka piersiowa":
                return KLATKA ;
            case "Biceps":
                return A_BICEPS ;
            case "Barki":
                return C_BARKI;
            case "Brzuch":
                return BRZUCH;
            case "Plecy":
                return PLECY;
            case "Tricecps":
                return TRICEPS;
            case "Nogi":
                return NOGI;
            default:
                return KLATKA;
        }
    }


    public static int toInt(ExerciseTypeEnum type)
    {
        switch(type)
        {
            case KLATKA:
                return 0;
            case A_BICEPS:
                return 1;
            case C_BARKI:
                return 2;
            case BRZUCH:
                return 3;
            case PLECY:
                return 4;
            case TRICEPS:
                return 5;
            case NOGI:
                return 6;
            default:
                return 0;
        }
    }

    public static String[] getFullName(ExerciseTypeEnum[] typeEnum) {
        String[] typeStrings = new String[typeEnum.length + 1];
        typeStrings[0] = "Typ ćwiczenia";
        for (int i = 1; i < typeEnum.length + 1; i++) {
            typeStrings[i] = typeEnum[i - 1].valueStr();
        }
        return  typeStrings;
    }
}
