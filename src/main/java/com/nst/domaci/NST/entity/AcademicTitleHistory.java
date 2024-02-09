package com.nst.domaci.NST.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nst.domaci.NST.serialization.AcademicTitleHistorySerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "academic_title_history")
@JsonSerialize(using = AcademicTitleHistorySerializer.class)
public class AcademicTitleHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;
    @NotNull
    @PastOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "academic_title_id")
    private AcademicTitle academicTitle;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "scientific_field_id")
    private ScientificField scientificField;
}
