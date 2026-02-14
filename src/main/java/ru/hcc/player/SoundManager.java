package ru.hcc.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public sealed class SoundManager extends util permits MusicCommand {

    private static final String PATH = "%s/sounds.json".formatted(getPath());

    public void write(String[] args) {
        JSONObject sampleObject;
        try {
            sampleObject = read();
            assert sampleObject != null;
            sampleObject.put(args[0], args[1]);
            try {
                Files.write(Paths.get(PATH), sampleObject.toJSONString().getBytes());
            } catch (IOException e) {
                System.out.println(e.getMessage() + "\n" + e.getCause());
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    private JSONObject read() throws IOException, ParseException {
        try {
            String path = getPath();
            if (!Files.exists(Path.of(path))) {
                new File(path).mkdirs();
                new File(PATH).createNewFile();
            }
            return (JSONObject) new JSONParser().parse(new FileReader(PATH));
        } catch (Exception e) {
            return null;
        }
    }

    @NotNull
    public HashMap<String, String> getAllSounds() {
        HashMap<String, String> map = new HashMap<>();

        JSONObject sampleObject;
        try {
            sampleObject = read();
            assert sampleObject != null;
            for (Object key: sampleObject.keySet()) {
                String one = (String) key;
                String two = (String) sampleObject.get(key);
                map.put(one, two);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

}
