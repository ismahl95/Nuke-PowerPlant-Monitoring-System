package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.ReportDTO;
import com.ihl95.nuclear.model.Report;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    ReportDTO toReportDTO(Report report);

    Report toReport(ReportDTO reportDTO);
}
