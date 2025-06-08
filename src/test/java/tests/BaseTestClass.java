package tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseTestClass {

    @BeforeEach
    protected void clearLog() {
        try (FileWriter writer = new FileWriter("logs/test.log", false)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    protected void attachLogsToAllure() {
        Path logPath = Paths.get("logs/test.log");
        if (Files.exists(logPath)) {
            try {
                Allure.addAttachment("Test Log", Files.newInputStream(logPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
