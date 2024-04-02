package com.nst.domaci.NST.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "academic_title")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AcademicTitle {
    @OneToMany(mappedBy = "academicTitle", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<AcademicTitleHistory> academicTitleHistories;
    @OneToMany(mappedBy = "academicTitle", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Member> members;
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Academic title name is mandatory")
    @Size(min = 2, max = 35, message = "Academic title name is between 2 and 35 characters long")
    private String name;
}
