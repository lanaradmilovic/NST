package com.nst.domaci.NST.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nst.domaci.NST.serialization.DepartmentSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonSerialize(using = DepartmentSerializer.class)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    @NotEmpty(message = "Department name is mandatory")
    @Size(min = 2, max = 100, message = "Department name must be between 2 and 100 characters long")
    @Column
    private String name;
    @NotNull
    @NotEmpty(message = "Department short name is mandatory")
    @Size(min = 2, max = 10, message = "Department short name must be between 1 and 10 characters long")
    @Column(name = "short_name", unique = true)
    private String shortName;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "current_leader_id", referencedColumnName = "id")
    private Member currentLeader;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "current_secretary_id", referencedColumnName = "id")
    private Member currentSecretary;
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Subject> subjects;
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LeaderHistory> leaderHistories;
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SecretaryHistory> secretaryHistories;
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Member> members;


}
