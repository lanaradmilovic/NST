package com.nst.domaci.NST.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "scientific_field")
public class ScientificField {
    @OneToMany(mappedBy = "scientificField", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Member> members;
    @OneToMany(mappedBy = "scientificField", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AcademicTitleHistory> academicTitleHistories;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Scientific field name is mandatory")
    @Size(min = 2, max = 35, message = "Scientific field name is between 2 and 35 characters long")
    private String name;

    public ScientificField(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }
}
