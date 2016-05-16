package de.inces.hackathonviessmann.model;



import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Philipp on 30.04.2016.
 */
public class LinearForecastInterpolation {
    static final Integer timesteps = 100;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private static List<Float> calculateTime(float startTime, float endTime){
        List<Float> time = new ArrayList();
        for(int i=0; i<timesteps; i++){
            time.add((float)i*(endTime-startTime)/(float) timesteps);
        }

        return time;
    }

    private static List<Float> calculateTemp(List<WeatherForecast> weatherData, List<Float> timeStamps){
        List<Float> interpolatedPoints= new ArrayList();
        for(int i=0; i<timesteps; i++){
            for(int j=0; j<weatherData.size()-1;j++){
                float curTime = timeStamps.get(i);

                float startTime = stringToSecond(weatherData.get(j).getStartTime());
                float endTime = stringToSecond(weatherData.get(j).getEndTime());
                float midTime = (startTime + endTime) /2;

                double interpolatedTemp;
                if ( (startTime < curTime) && (curTime < endTime)) {

                    if ( curTime < midTime ) {
                        if (j-1 >= 0) {
                            float startTimeMinus;
                            startTimeMinus = stringToSecond(weatherData.get(j - 1).getStartTime());
                            float endTimeMinus = stringToSecond(weatherData.get(j-1).getEndTime());
                            float midTimeMinus = (startTimeMinus + endTimeMinus) /2;

                            float ratio = 1 - (midTime - curTime) / (midTime - midTimeMinus);
                            float ratioM = 1 - (curTime - midTimeMinus) / (midTime - midTimeMinus);
                            interpolatedTemp =  (weatherData.get(j).getAvgTemperature() * ratio +  weatherData.get(j-1).getAvgTemperature() * ratioM);
                        }
                        else{
                            interpolatedTemp = weatherData.get(j).getAvgTemperature();
                        }

                        interpolatedPoints.add( (float) interpolatedTemp);
                        break;
                    }
                    else{
                        float startTimePlus = stringToSecond(weatherData.get(j+1).getStartTime());
                        float endTimePlus = stringToSecond(weatherData.get(j+1).getEndTime());
                        float midTimePlus = (startTimePlus + endTimePlus) /2;

                        float ratio = 1 - (curTime - midTime) / (midTimePlus - midTime);
                        float ratioP = 1 - (midTimePlus - curTime) / (midTimePlus - midTime);
                        interpolatedTemp =  ( weatherData.get(j).getAvgTemperature() * ratio +  weatherData.get(j+1).getAvgTemperature() * ratioP);
                        interpolatedPoints.add((float) interpolatedTemp);
                        break;
                    }
                }
            }

        }
        return interpolatedPoints;
    }

    public static float stringToSecond(String text){
        Date date = new Date();
        try {
            date = format.parse(text);
            //System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date currentDate = new Date();
        return (float) (date.getTime()/(long)1000 - currentDate.getTime()/(long)1000);
    }

    public static List<PredictionDataPoint> getPredictionPoints(List<WeatherForecast> weatherData) {
        List<PredictionDataPoint> predictionData = new ArrayList();
        List<Float> timeStamps = new ArrayList();
        List<Float> interpolatedPoints;

        float startTime = stringToSecond(weatherData.get(0).getStartTime());

        float endTime = stringToSecond(weatherData.get(weatherData.size()-1).getEndTime());
        long time1 = System.currentTimeMillis();
        timeStamps = calculateTime(startTime, endTime);
        long time2 = System.currentTimeMillis();
        interpolatedPoints = calculateTemp(weatherData,timeStamps);
        long time3 = System.currentTimeMillis();

        Log.d("LinPol",String.valueOf( time2-time1) + "  " + String.valueOf( time3-time2));

        for (int i=0;i<interpolatedPoints.size();i++){
            predictionData.add(new PredictionDataPoint(timeStamps.get(i),interpolatedPoints.get(i)));
        }
        return predictionData;
    }

}
