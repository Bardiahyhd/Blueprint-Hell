package Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public static int generalid = 1;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    ;
    public static Map<String, Object> Config = new HashMap();

    public static void LoadConfig() throws Exception {
        URL resource = Config.class.getResource("config.json");
        if (resource == null) {
            throw new FileNotFoundException("Could not find config.json");
        }

        File file = new File(resource.toURI());

        try (FileReader reader = new FileReader(file)) {
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            Config = gson.fromJson(reader, mapType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void RefreshConfig() throws Exception {
        URL resource = Config.class.getResource("config.json");
        if (resource == null) {
            throw new FileNotFoundException("Could not find config.json");
        }

        File file = new File(resource.toURI());

        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(Config, writer);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}