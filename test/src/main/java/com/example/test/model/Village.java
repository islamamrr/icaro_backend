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
@Table(name = "Village")
public class Village {
    @Id
    @Column(name = "Village_ID")
    private Integer villageId;

    @Column(name = "Village_Name")
    private String villageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Center_ID")
    private Center center;

    @Column(name = "Village_Type")
    private String villageType;
}
