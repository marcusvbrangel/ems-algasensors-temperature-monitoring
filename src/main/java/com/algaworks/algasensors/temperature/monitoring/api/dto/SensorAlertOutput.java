package com.algaworks.algasensors.temperature.monitoring.api.dto;

import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorAlertOutput {
    private SensorId id;
    private Double maxTemperature;
    private Double minTemperature;
}
