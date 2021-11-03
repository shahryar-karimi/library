package ir.shahryar.library.util.props;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Component
public class MyProperties extends Properties {
    private MyProperties() throws IOException {
        File file = new File("src/main/java/ir/shahryar/library/util/props/Const.properties");
        FileReader fileReader = new FileReader(file);
        load(fileReader);
        fileReader.close();
    }
}
