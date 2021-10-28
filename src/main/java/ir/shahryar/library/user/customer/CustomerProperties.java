package ir.shahryar.library.user.customer;

import ir.shahryar.library.util.props.Container;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class CustomerProperties {
    public Properties properties;

    public CustomerProperties() {
        try {
            this.properties = Container.getInstance().getMap().get("Customer");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
