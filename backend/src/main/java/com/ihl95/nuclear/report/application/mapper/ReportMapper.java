package com.ihl95.nuclear.report.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.report.application.dto.ReportDTO;
import com.ihl95.nuclear.report.domain.Report;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    ReportDTO toReportDTO(Report report);

    Report toReport(ReportDTO reportDTO);
}
