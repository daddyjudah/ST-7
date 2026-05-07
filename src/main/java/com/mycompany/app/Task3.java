package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task3 {
    private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast"
            + "?latitude=56&longitude=44"
            + "&hourly=temperature_2m,rain"
            + "&current=cloud_cover"
            + "&timezone=Europe%2FMoscow"
            + "&forecast_days=1"
            + "&wind_speed_unit=ms";

    private static final Path FORECAST_FILE = Path.of("result", "forecast.txt");

    public static void run(WebDriver webDriver) {
        try {
            webDriver.get(FORECAST_URL);
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String table = buildForecastTable(elem.getText());

            System.out.println(table);
            writeForecast(table);
        } catch (Exception e) {
            System.out.println("Error in Task 3");
            e.printStackTrace();
        }
    }

    static String buildForecastTable(String json) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        JSONObject hourly = (JSONObject) obj.get("hourly");
        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains = (JSONArray) hourly.get("rain");

        StringBuilder table = new StringBuilder();
        table.append(String.format("%-3s | %-16s | %-13s | %-11s%n",
                "№", "Дата/время", "Температура", "Осадки (мм)"));
        table.append("----|------------------|---------------|------------").append(System.lineSeparator());

        for (int i = 0; i < times.size(); i++) {
            table.append(String.format("%-3d | %-16s | %-13s | %-11s%n",
                    i + 1, times.get(i), temperatures.get(i), rains.get(i)));
        }

        return table.toString();
    }

    private static void writeForecast(String table) throws IOException {
        Files.createDirectories(FORECAST_FILE.getParent());
        Files.writeString(FORECAST_FILE, table, StandardCharsets.UTF_8);
    }
}
