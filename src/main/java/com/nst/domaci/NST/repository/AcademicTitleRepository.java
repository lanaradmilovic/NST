package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.AcademicTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicTitleRepository extends JpaRepository<AcademicTitle, Long> {
    Optional<AcademicTitle> findByName(String name);
}
