package de.inces.hackathonviessmann.db;

/**
 * Created by Koerfer on 29.04.2016.
 */
public class DbContracts {

    public static class WeatherForecastTable {

        public static final String TABLE_FORECAST = "forecast";

        public static final String COLUMN_START = "start";
        public static final String COLUMN_END = "end";
        public static final String COLUMN_MIN_TEMP = "min_temp";
        public static final String COLUMN_MAX_TEMP = "max_temp";

        public static String SqlCreate() {
            return "CREATE TABLE " + TABLE_FORECAST + " ( " +
                    COLUMN_START + " TEXT, " +
                    COLUMN_END + " TEXT, " +
                    COLUMN_MIN_TEMP + " REAL, " +
                    COLUMN_MAX_TEMP + " REAL );";
        }

        public static String SqlDelete() {
            return "DROP TABLE " + TABLE_FORECAST + " IF EXISTS;";
        }

    }


}
