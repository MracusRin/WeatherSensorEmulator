package org.mracus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.mracus.mapping.MeasurementDTO;
import org.mracus.mapping.MeasurementResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.IntStream;

public class SensorEmulator {
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, String> getSensor(String name) {
        Map<String, String> sensor = new HashMap<>();
        sensor.put("name", name);
        return sensor;
    }

    public void showMeasurementsObjectMapper(String getMeasurementsUrl) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = restTemplate.getForObject(getMeasurementsUrl, String.class);
        JsonNode jsonNode = objectMapper.readTree(response);

        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode value = jsonNode.get(i).get("value");
            JsonNode raining = jsonNode.get(i).get("raining");
            JsonNode sensor = jsonNode.get(i).get("sensor").get("name");
            System.out.println("Temperature: " + value +
                    "; Raining: " + raining +
                    "; Sensor name: " + sensor);
        }
    }

    public void showMeasurements(String getMeasurementsUrl) throws JsonProcessingException {
        MeasurementResponse response = restTemplate.getForObject(getMeasurementsUrl, MeasurementResponse.class);
        if (response != null) {
            for (MeasurementDTO measurement : response.getMeasurements()) {
                System.out.println("Sensor: " + measurement.getSensor().getName() +
                        "; Raining: " + measurement.getRaining() +
                        "; Temp: " + measurement.getValue());
            }
        } else {
            System.out.println("Response is NULL");
        }
    }

    public void showTemp(String getMeasurementsUrl, int count) throws JsonProcessingException {
        MeasurementResponse response = restTemplate.getForObject(getMeasurementsUrl, MeasurementResponse.class);
        if (response != null) {
            if (count == 0) {
                count = response.getMeasurements().size();
            }

            double[] yValue = response.getMeasurements().stream().mapToDouble(MeasurementDTO::getValue).toArray();
            double[] xValue = IntStream.range(0, response.getMeasurements().size()).asDoubleStream().toArray();

            XYChart xyChart = QuickChart.getChart("Temp measurements", "Count", "Degrees Celsius", "Temp", xValue, yValue);
            new SwingWrapper<>(xyChart).displayChart();
        } else {
            System.out.println("Response is NULL");
        }
    }

    public void postSensor(String addSensorUrl, Map<String, String> sensor) {
        HttpEntity<Map<String, String>> sensorQuery = new HttpEntity<>(sensor);
        String sensorResponse = restTemplate.postForObject(addSensorUrl, sensorQuery, String.class);
        System.out.println(sensorResponse);
    }

    public void postRandomMeasurements(String addMeasurementUrl, Map<String, String> sensorJsonData, int quantity) {
        Map<String, Object> measurementJsonData = new HashMap<>();
        Random random = new Random();
        double value;
        boolean raining;

        double minTemperature = -100;
        double maxTemperature = 100;

        for (int i = 0; i < quantity; i++) {
            value = minTemperature + random.nextFloat() * (maxTemperature - minTemperature);
            raining = random.nextBoolean();

            measurementJsonData.put("value", value);
            measurementJsonData.put("raining", raining);
            measurementJsonData.put("sensor", sensorJsonData);

            HttpEntity<Map<String, Object>> measurementQuery = new HttpEntity<>(measurementJsonData);
            String measurementResponse = restTemplate.postForObject(addMeasurementUrl, measurementQuery, String.class);
            System.out.println("Query " + (i + 1) + ": " + measurementResponse + "; "
                    + "Sensor: " + sensorJsonData.get("name") +
                    "; Raining: " + raining +
                    "; Temp: " + value);
        }
    }
}