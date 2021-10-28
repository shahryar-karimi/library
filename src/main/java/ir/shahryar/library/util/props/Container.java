package ir.shahryar.library.util.props;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Container {
    private static Container instance;
    private final HashMap<String, Properties> map;

    private Container() throws IOException {
        this.map = new HashMap<>();
        init();
    }

    public static Container getInstance() throws IOException {
        if (instance == null) instance = new Container();
        return instance;
    }

    private void init() throws IOException {
        FileReader userFileReader = new FileReader("User.properties");
        addProp("User");
        addProp("Customer").load(userFileReader);
        addProp("Admin").load(userFileReader);
    }

    private Properties addProp(String key) throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(key + ".properties");
        properties.load(fileReader);
        return map.put(key, properties);
    }

    public HashMap<String, Properties> getMap() {
        return new HashMap<>(map);
    }
}
