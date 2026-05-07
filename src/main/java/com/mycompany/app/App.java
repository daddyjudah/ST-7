package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        configureChromeDriver();

        ChromeOptions options = new ChromeOptions();
        WebDriver webDriver = new ChromeDriver(options);

        try {
            runTask1(webDriver);
            Task2.run(webDriver);
            Task3.run(webDriver);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            webDriver.quit();
        }
    }

    private static void runTask1(WebDriver webDriver) {
        try {
            webDriver.get("https://www.calculator.net/password-generator.html");

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#resultid .verybigtext b")
            ));

            System.out.println("Сгенерированный пароль: " + passwordElement.getText());
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error in Task 1");
            e.printStackTrace();
        }
    }

    private static void configureChromeDriver() {
        if (hasDriverProperty()) {
            return;
        }

        String envDriverPath = System.getenv("CHROMEDRIVER_PATH");
        if (isFile(envDriverPath)) {
            System.setProperty("webdriver.chrome.driver", envDriverPath);
            return;
        }

        List<String> commonDriverPaths = List.of(
                "C:/Program Files/Google/Chrome/Application/chromedriver.exe",
                "C:/Program Files/ChromeDriver/chromedriver.exe",
                "C:/chromedriver/chromedriver.exe",
                "drivers/chromedriver.exe"
        );

        for (String driverPath : commonDriverPaths) {
            if (isFile(driverPath)) {
                System.setProperty("webdriver.chrome.driver", driverPath);
                return;
            }
        }

        Optional<Path> cachedDriver = findCachedChromeDriver();
        cachedDriver.ifPresent(path -> System.setProperty("webdriver.chrome.driver", path.toString()));
    }

    private static boolean hasDriverProperty() {
        return isFile(System.getProperty("webdriver.chrome.driver"));
    }

    private static boolean isFile(String path) {
        return path != null && !path.isBlank() && Files.isRegularFile(Path.of(path));
    }

    private static Optional<Path> findCachedChromeDriver() {
        Path seleniumCache = Path.of(System.getProperty("user.home"), ".cache", "selenium", "chromedriver");
        if (!Files.isDirectory(seleniumCache)) {
            return Optional.empty();
        }

        try (Stream<Path> paths = Files.walk(seleniumCache)) {
            return paths
                    .filter(path -> Files.isRegularFile(path)
                            && "chromedriver.exe".equalsIgnoreCase(path.getFileName().toString()))
                    .max(Comparator.comparing(Path::toString));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
