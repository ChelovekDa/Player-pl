package ru.hcc.player;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileRW {

    public void write(String[] args){
        JSONObject sampleObject = null;
        try {
            sampleObject = read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        sampleObject.put(args[0], args[1]);
        try {
            Files.write(Paths.get("plugins/PlayerPL/sounds.json"), sampleObject.toJSONString().getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
    }

    private JSONObject read() throws IOException, ParseException {
        FileReader reader = new FileReader("plugins/PlayerPL/sounds.json");
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(reader);
    }

    public HashMap<String, String> getAllSounds() {
        HashMap<String, String> map = new HashMap<>();

        JSONObject sampleObject = null;
        try {
            sampleObject = read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for (Object key: sampleObject.keySet()) {
            String one = (String) key;
            String two = (String) sampleObject.get(key);
            map.put(one, two);
        }

        return map;
    }

    public void createDir() throws IOException {
        try {
            if (Files.exists(Paths.get("plugins/PlayerPL"))) {
                if (Files.exists(Paths.get("plugins/PlayerPL/sounds.json"))) return;
                else new File("plugins/PlayerPL/sounds.json").createNewFile();
            }
            else {
                new File("plugins/PlayerPl").mkdirs();
                new File("plugins/PlayerPL/sounds.json").createNewFile();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
