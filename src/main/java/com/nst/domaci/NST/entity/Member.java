package com.nst.domaci.NST.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nst.domaci.NST.serialization.DepartmentSerializer;
import com.nst.domaci.NST.serialization.MemberSerializer;
import com.nst.domaci.NST.service.impl.MemberServiceImpl;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize(using = MemberSerializer.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Member's first name is mandatory.")
    @Size(min = 2, max = 25, message = "Member's name must be between 2 and 25 characters long.")
    @Column(name = "firstname")
    private String firstName;
    @NotEmpty(message = "Member's last name name is mandatory.")
    @Size(min = 2, max = 25, message = "Member's last name must be between 2 and 25 characters long.")
    @Column(name = "lastname")
    private String lastName;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "academic_title_id", nullable = false)
    private AcademicTitle academicTitle;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "education_title_id", nullable = false)
    private EducationTitle educationTitle;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "scientific_field_id", nullable = false)
    private ScientificField scientificField;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<AcademicTitleHistory> academicTitleHistories;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Engagement> engagements;
}
