package pl.mentoring.currencyexchange.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class SampleDataLoader<T> {

    private static final Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);
    private final ObjectMapper objectMapper;

    public T getEntityFromSampleDataFile(String filename, Class<T> entityType) {
        try {
            File src = getSampleDataFile(filename);
            return objectMapper.readValue(src, entityType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> List<T> getEntitiesList(T[] array) {
        if (array != null) {
            return Arrays.asList(array);
        } else {
            return new ArrayList<>();
        }
    }

    private static File getSampleDataFile(String filename) {
        URL resource = SampleDataLoader.class.getClassLoader().getResource("sampleData\\" + filename);
        if (resource != null) {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                logger.error(e.getMessage(), e);
                return new File("");
            }
        }
        return new File("");
    }

}
