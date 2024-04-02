package com.nst.domaci.NST.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nst.domaci.NST.entity.form.TeachingForm;
import com.nst.domaci.NST.serialization.EngagementSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table
@JsonSerialize(using = EngagementSerializer.class)
public class Engagement {
    @OneToMany(mappedBy = "engagement", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Lecture> lectures;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "engagement_year")
    private Long engagementYear;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @Enumerated(EnumType.STRING)
    private Set<TeachingForm> teachingForm;

}
