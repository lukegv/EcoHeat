package de.lukaskoerfer.hackathonviessmann.data;

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

import de.lukaskoerfer.hackathonviessmann.model.HistoryElement;
import de.lukaskoerfer.hackathonviessmann.model.UserData;

/**
 * Created by Koerfer on 30.04.2016.
 */
public class CloudConnection {

    private static final String USERDATA_URL = "http://hackathon-viessmann.azurewebsites.net/userdata.php?";

    public static UserData GetUserData(String username) {
        try {
            String usernameValue = "user=" + username;
            String urlString = USERDATA_URL + usernameValue;
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

    private static final String HISTORY_URL = "http://hackathon-viessmann.azurewebsites.net/housedata.php?";

    public static List<HistoryElement> GetHistory(int houseId) {
        List<HistoryElement> history = new ArrayList<>();
        try {
            String usernameValue = "houseid=" + Integer.toString(houseId);
            String urlString = HISTORY_URL + usernameValue;
            URL requestUrl = new URL(urlString);
            InputStream stream = requestUrl.openStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            NodeList nodes = doc.getElementsByTagName("entry");
            for (int i = 0; i < nodes.getLength(); i++) {
                history.add(new HistoryElement((Element) nodes.item(i)));
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
        return history;
    }

}
