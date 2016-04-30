package de.lukaskoerfer.hackathonviessmann.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import de.lukaskoerfer.hackathonviessmann.model.UserData;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;

/**
 * Created by Koerfer on 30.04.2016.
 */
public class CloudConnection {

    private static final String CLOUD_URL = "http://hackathon-viessmann.azurewebsites.net/userdata.php?";

    public static UserData GetUserData(String username) {
        try {
            String usernameValue = "user=" + username;
            String urlString = CLOUD_URL + usernameValue;
            URL requestUrl = new URL(urlString);
            InputStream stream = requestUrl.openStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            return new UserData((Element) doc.getElementsByTagName("userdata").item(0));
        } catch (MalformedURLException muex) {
            muex.printStackTrace();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (ParserConfigurationException pcex) {
            pcex.printStackTrace();
        } catch (SAXException saxex) {
            saxex.printStackTrace();
        }
        return null;
    }

}
