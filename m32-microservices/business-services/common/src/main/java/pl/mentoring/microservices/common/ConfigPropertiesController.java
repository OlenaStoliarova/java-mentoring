package pl.mentoring.microservices.common;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigPropertiesController {

    private final DynamicStringProperty propertyOneWithDynamic = DynamicPropertyFactory.getInstance()
        .getStringProperty("mentoring.archaius.properties.one", "not found!");

    @GetMapping("/property-from-dynamic-management")
    public String getPropertyValue() {
        return propertyOneWithDynamic.getName() + ": " + propertyOneWithDynamic.get();
    }
}
