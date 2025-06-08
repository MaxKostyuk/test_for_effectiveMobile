package api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;

import java.io.InputStream;

@Getter
public class Config {
    private static final Config INSTANCE;
    private final String apiKey;
    private final String baseUrl;
    private final String createDashboardUrl;
    private final String defaultProjectName;


    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.yaml")) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            RawConfig raw = mapper.readValue(in, RawConfig.class);
            INSTANCE = new Config(raw.baseUrl, raw.apiKey, raw.createDashboardUrl, raw.defaultProjectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.yaml", e);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    private Config(String baseUrl, String apiKey, String createDashboardUrl, String defaultProjectName) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.createDashboardUrl = createDashboardUrl;
        this.defaultProjectName = defaultProjectName;
    }

    private static class RawConfig {
        public String baseUrl;
        public String apiKey;
        public String createDashboardUrl;
        public String defaultProjectName;
    }
}
