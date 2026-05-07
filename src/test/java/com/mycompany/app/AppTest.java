package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {
    @Test
    public void parseIpReturnsAddressFromJson() throws Exception {
        String ip = Task2.parseIp("{\"ip\":\"203.0.113.10\"}");

        assertEquals("203.0.113.10", ip);
    }

    @Test
    public void buildForecastTableContainsHourlyRows() throws Exception {
        String json = "{"
                + "\"hourly\":{"
                + "\"time\":[\"2026-05-07T00:00\",\"2026-05-07T01:00\"],"
                + "\"temperature_2m\":[12.5,13.0],"
                + "\"rain\":[0.0,0.2]"
                + "}"
                + "}";

        String table = Task3.buildForecastTable(json);

        assertTrue(table.contains("Дата/время"));
        assertTrue(table.contains("2026-05-07T00:00"));
        assertTrue(table.contains("12.5"));
        assertTrue(table.contains("0.2"));
    }
}
