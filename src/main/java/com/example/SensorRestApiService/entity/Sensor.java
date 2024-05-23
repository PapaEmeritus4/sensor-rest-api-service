package com.example.SensorRestApiService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "sensor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "sensor")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Measurement> measurements;
}