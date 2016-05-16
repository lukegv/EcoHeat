package de.inces.hackathonviessmann.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import de.inces.hackathonviessmann.model.GlobalLocation;
import de.inces.hackathonviessmann.model.WeatherForecast;

/**
 * Created by Koerfer on 29.04.2016.
 */
public class OpenWeather {

    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    private static final String API_KEY = "appid=96870d7985e4a4fc607331c0fd2726e9";
    private static final String MODE = "mode=xml";

    public static List<WeatherForecast> GetForecast(GlobalLocation coordinate) {
        List<WeatherForecast> results = new ArrayList<>();
        try {
            String latValue = "lat=" + Double.toString(coordinate.getLatitude());
            String longValue = "lon=" + Double.toString(coordinate.getLongitude());
            String urlString = FORECAST_URL + API_KEY + "&" + MODE + "&" + latValue + "&" + longValue;
            URL requestUrl = new URL(urlString);
            InputStream stream = requestUrl.openStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            NodeList timeNodes = doc.getElementsByTagName("time");
            for (int i = 0; i < timeNodes.getLength(); i++) {
                results.add(new WeatherForecast((Element) timeNodes.item(i)));
            }
        } catch (MalformedURLException muex) {
            muex.printStackTrace();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (ParserConfigurationException pcex) {
            pcex.printStackTrace();
        } catch (SAXException saxex) {
            saxex.printStackTrace();
        }
        return results;
    }
}
