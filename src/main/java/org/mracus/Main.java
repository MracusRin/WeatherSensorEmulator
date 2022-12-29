package org.mracus;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        String postMeasurementUrl = "http://localhost:8080/measurements/add";
        String postSensorUrl = "http://localhost:8080/sensor/registration";
        String getMeasurementsUrl = "http://localhost:8080/measurements";

        SensorEmulator emulator = new SensorEmulator();
//        Map<String, String> sensor = emulator.getSensor("T20");
//        emulator.postSensor(postSensorUrl, sensor);
//        emulator.postRandomMeasurements(postMeasurementUrl, sensor, 10);
//        emulator.showMeasurements(getMeasurementsUrl);
//        emulator.showTemp(getMeasurementsUrl, 1000);
    }
}
