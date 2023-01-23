package com.comvous.demo.services;

import com.comvous.demo.data.repositories.ReportRepository;
import com.comvous.demo.data.models.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService extends BaseCrudService<ReportRepository, Report> {

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        super(reportRepository);
    }

    public List<Report> getReportByUserAndProject(Long userId, Long projectId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.linkedRepository.findAllByUserIdAndProjectId(userId, projectId, pageable).getContent();
    }
}
