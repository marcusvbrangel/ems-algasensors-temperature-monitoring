package com.algaworks.algasensors.temperature.monitoring.api.controller;

import com.algaworks.algasensors.temperature.monitoring.api.dto.SensorAlertInput;
import com.algaworks.algasensors.temperature.monitoring.api.dto.SensorAlertOutput;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
public class SensorAlertController {

    private final SensorAlertRepository sensorAlertRepository;

    public SensorAlertController(SensorAlertRepository sensorAlertRepository) {
        this.sensorAlertRepository = sensorAlertRepository;
    }

    /*
    GET /api/sensors/{sensorId}/alert:
    Retorna SensorAlertOutput.
    Retorna 200 OK em caso de sucesso.
    Se a configuração não existir, retorna 404 Not Found.
    */
    @GetMapping("/{sensorId}/alert")
    @ResponseStatus(HttpStatus.OK)
    public SensorAlertOutput getSensorAlert(@PathVariable final TSID sensorId) {

        return sensorAlertRepository.findById(new SensorId(sensorId))
                .map(sensorAlert -> SensorAlertOutput.builder()
                        .id(sensorAlert.getId())
                        .maxTemperature(sensorAlert.getMaxTemperature())
                        .minTemperature(sensorAlert.getMinTemperature())
                        .build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor alert not found"));

    }

    /*
    PUT /api/sensors/{sensorId}/alert:
    Cria ou atualiza a configuração de alerta de um sensor.
    Retorna SensorAlertOutput.
    Retorna 200 OK em caso de sucesso.
    Se a configuração não existir, cria um novo registro.
     */
    @PutMapping("/{sensorId}/alert")
    @ResponseStatus(HttpStatus.OK)
    public SensorAlertOutput createOrUpdateSensorAlert(@PathVariable final TSID sensorId,
                                                       @RequestBody final SensorAlertInput sensorAlertInput) {

        var sensorAlert = sensorAlertRepository.findById(new SensorId(sensorId))
                .map(existing -> {
                    existing.setMaxTemperature(sensorAlertInput.getMaxTemperature());
                    existing.setMinTemperature(sensorAlertInput.getMinTemperature());
                    return existing;
                })
                .orElseGet(() -> {
                    return this.toDomain(new SensorId(sensorId), sensorAlertInput);
                });

        var saved = sensorAlertRepository.save(sensorAlert);

        return SensorAlertOutput.builder()
                .id(saved.getId())
                .maxTemperature(saved.getMaxTemperature())
                .minTemperature(saved.getMinTemperature())
                .build();

    }

    /*
    DELETE /api/sensors/{sensorId}/alert:
    Remove a configuração de alerta de um sensor.
    Retorna 204 No Content em caso de sucesso.
    Se a configuração não existir, retorna 404 Not Found.
    */
    @DeleteMapping("/{sensorId}/alert")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensorAlert(@PathVariable final TSID sensorId) {

        sensorAlertRepository.findById(new SensorId(sensorId))
                .ifPresentOrElse(
                        sensorAlertRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor alert not found"); });

    }

    private SensorAlert toDomain(final SensorId sensorId, final SensorAlertInput sensorAlertInput) {
        return SensorAlert.builder()
                .id(sensorId)
                .maxTemperature(sensorAlertInput.getMaxTemperature())
                .minTemperature(sensorAlertInput.getMinTemperature())
                .build();
    }

}
