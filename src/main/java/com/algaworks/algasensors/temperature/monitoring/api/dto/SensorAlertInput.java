package com.algaworks.algasensors.temperature.monitoring.api.dto;

import lombok.Data;

@Data
public class SensorAlertInput {
    private Double maxTemperature;
    private Double minTemperature;
}
