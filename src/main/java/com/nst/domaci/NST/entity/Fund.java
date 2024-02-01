package com.nst.domaci.NST.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fund {
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    @MapsId
    private Subject subject;
    private int lecture;
    private int exercise;
    private int lab;

}
