package main.java.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ApplicationProperties extends Properties {

    public ApplicationProperties() {
        JSONObject jsonObject = loadConfigFile();
        readJsonObjectIntoProperties(jsonObject);
    }

    private void readJsonObjectIntoProperties(JSONObject jsonObject) {

        JSONObject configJsonObj = (JSONObject) jsonObject.get("config");

        Set<Object> keySet = configJsonObj.keySet();
        for (Object obj : keySet) {
            String key = (String) obj;
            this.place(key, configJsonObj.get(key));
        }
    }

    private JSONObject loadConfigFile() {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("src\\main\\resources\\config.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * Overriding this method to make it so other's cannot modify the application properties.
     * @param key
     * @param value
     * @return null
     */
    @Override
    public synchronized Object put(Object key, Object value) {
        return null;
    }

    /**
     * Overriding this method to make it so other's cannot modify the application properties.
     * @param t
     */
    @Override
    public synchronized void putAll(Map<?, ?> t) {
        return;
    }

    private Object place(Object key, Object value) {
        return super.put(key, value);
    }
}
