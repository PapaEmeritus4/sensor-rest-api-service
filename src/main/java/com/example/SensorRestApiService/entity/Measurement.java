package com.example.SensorRestApiService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull(message = "Value should not be empty")
    @Min(-100)
    @Max(100)
    private double value;

    @Column(name = "raining")
    @NotNull(message = "Raining value should not be empty")
    private boolean raining;

    @ManyToOne
    @NotNull(message = "Name of SENSOR should not be empty")
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Measurement(double value, boolean raining) {
        this.value = value;
        this.raining = raining;
    }
}