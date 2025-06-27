package com.algaworks.algasensors.temperature.monitoring.api.dto;

import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorAlertOutput {
    private TSID id;
    private Double maxTemperature;
    private Double minTemperature;
}
