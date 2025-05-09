package Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();;
    public static Map<String, Object> Config = new HashMap();

    public static void LoadConfig() throws IOException {
        try (FileReader reader = new FileReader("config.json")) {
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            Config = gson.fromJson(reader, mapType);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void RefreshConfig() throws IOException {
        try {
            FileWriter writer = new FileWriter("config.json");
            gson.toJson(Config, writer);
            writer.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}