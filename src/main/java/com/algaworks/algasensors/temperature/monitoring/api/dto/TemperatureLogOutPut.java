package com.algaworks.algasensors.temperature.monitoring.api.dto;

import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class TemperatureLogOutPut {
    private UUID id;
    private OffsetDateTime registeredAt;
    private Double value;
    private SensorId sensorId;
}
