package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {
    public static void run(WebDriver webDriver) {
        try {
            webDriver.get("https://api.ipify.org/?format=json");
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String ip = parseIp(elem.getText());

            System.out.println("IP: " + ip);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error in Task 2");
            e.printStackTrace();
        }
    }

    static String parseIp(String json) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        Object ip = obj.get("ip");
        return ip == null ? "" : ip.toString();
    }
}
