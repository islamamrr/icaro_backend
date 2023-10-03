package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Accumulated")
public class Accumulated {

    @EmbeddedId
    private AccumulatedCompositeKey id;

    @Column(name = "Percentage")
    private Integer percentage;

    @Column(name = "AccumulatedWeight")
    private Integer accumulatedWeight;
}