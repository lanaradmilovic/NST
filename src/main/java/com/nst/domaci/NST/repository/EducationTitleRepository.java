package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.EducationTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationTitleRepository extends JpaRepository<EducationTitle, Long> {
    Optional<EducationTitle> findByName(String name);

}
