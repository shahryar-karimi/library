package ir.shahryar.library.user.admin;

import ir.shahryar.library.util.props.Container;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class AdminProperties {
    public Properties properties;

    public AdminProperties() {
        try {
            this.properties = Container.getInstance().getMap().get("Admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
