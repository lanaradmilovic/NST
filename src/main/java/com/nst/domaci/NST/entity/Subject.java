package com.nst.domaci.NST.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Subject name is mandatory")
    @Size(min = 2, max = 10, message = "Subject name is between 2 and 10 characters long")
    private String name;
    private String espb;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Engagement> engagements;


}