package com.comvous.demo.controllers;

import com.comvous.demo.data.models.Report;
import com.comvous.demo.data.models.projections.ReportDTO;
import com.comvous.demo.data.models.projections.ReportWithUserDTO;
import com.comvous.demo.data.repositories.ReportRepository;
import com.comvous.demo.services.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/report")
public class ReportController extends BaseSecuredCrudController<ReportService, ReportRepository, Report, ReportDTO> {
    @Autowired
    public ReportController(ReportService baseCrudService, ModelMapper modelMapper) {
        super(baseCrudService, modelMapper, ReportDTO.class);
    }

    @GetMapping("/user/{id}/project/{id}/page/{page}/size/{size}")
    public List<ReportWithUserDTO> getReportByUserAndProject(@PathVariable("id") Long userId, @PathVariable("id") Long projectId, @PathVariable("page") int page, @PathVariable("size") int size) {
        return this.linkedService.getReportByUserAndProject(userId, projectId, page, size).stream().map(report -> this.modelMapper.map(report, ReportWithUserDTO.class)).collect(Collectors.toList());
    }
}
