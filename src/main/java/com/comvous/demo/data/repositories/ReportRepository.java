package com.comvous.demo.data.repositories;

import com.comvous.demo.data.models.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Page<Report> findAllByUserIdAndProjectId(Long userId, Long projectId, Pageable pageable);
}
