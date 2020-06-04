package network;

import mappers.JsonEntitiesMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class NetworkMapsServiceImpl implements NetworkMapsService {
    private static final String GEOCODE_PATH = "\n" +
            "http://open.mapquestapi.com/geocoding/v1/address";
    private static final String MAPS_API_KEY = "ek3Kh4zucauthYAcWmGpmkIhIx7KZlcD";

    @Override
    public Coordinates geocodeLocation(String address) {
        URL url;
        try {
            url = new URL(GEOCODE_PATH + "?key=" + MAPS_API_KEY + "&location=" + address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 200) {
                String json = readData(con);
                return new JsonEntitiesMapper().mapCoords(json);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String readData(HttpURLConnection connection) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(result).toString();
    }
}