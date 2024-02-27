package GorkemKoc.reportingApplication.business.concretes;

import GorkemKoc.reportingApplication.business.abstracts.ReportService;
import GorkemKoc.reportingApplication.business.reponses.*;
import GorkemKoc.reportingApplication.business.requests.CreateReportRequest;
import GorkemKoc.reportingApplication.business.requests.UpdateReportRequest;
import GorkemKoc.reportingApplication.core.utilities.mappers.ModelMapperService;
import GorkemKoc.reportingApplication.dataAccess.abstracts.LaborantRepository;
import GorkemKoc.reportingApplication.dataAccess.abstracts.ReportRepository;
import GorkemKoc.reportingApplication.entities.concretes.Laborant;
import GorkemKoc.reportingApplication.entities.concretes.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;


@AllArgsConstructor
@Service
public class ReportManager implements ReportService {
    private ReportRepository reportRepository;
    private LaborantRepository laborantRepository;
    private ModelMapperService modelMapperService;

    @Override
    public int getTotalPages(int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Report> firstPage = reportRepository.findAll(pageable);
        return firstPage.getTotalPages();
    }
    @Override
    public boolean doesReportExist(String fileNumber) {
        return reportRepository.existsByFileNumber(fileNumber);
    }

    @Override
    public Page<GetAllReportResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reportPage = reportRepository.findAll(pageable);

        return reportPage.map(report -> {
            GetAllReportResponse response = this.modelMapperService.forResponse().map(report, GetAllReportResponse.class);
            response.setReportPicture(report.getReportPicture());
            return response;
        });
    }
    @Override
    public Page<GetAllActiveReportResponse> getAllActiveReports(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reportPage = reportRepository.findAllByIsActiveTrue(pageable);

        return reportPage.map(report -> {
            GetAllActiveReportResponse response = this.modelMapperService.forResponse().map(report, GetAllActiveReportResponse.class);
            response.setReportPicture(report.getReportPicture());
            return response;
        });
    }
    @Override
    public Page<GetByAscendingReportResponse> getByAscendingReport(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reportRepository.findAllByIsActiveTrueOrderByReportDateAsc(pageable)
                .map(report -> {
                    GetByAscendingReportResponse response = modelMapperService.forResponse().map(report, GetByAscendingReportResponse.class);
                    response.setReportPicture(report.getReportPicture());
                    return response;
                });
    }
    @Override
    public Page<GetByDescendingReportResponse> getByDescendingReport(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reportRepository.findAllByIsActiveTrueOrderByReportDateDesc(pageable)
                .map(report -> {
                    GetByDescendingReportResponse response = modelMapperService.forResponse().map(report, GetByDescendingReportResponse.class);
                    response.setReportPicture(report.getReportPicture());
                    return response;
                });
    }
    @Override
    public GetByReportIdResponse getByReportId(int reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);

        GetByReportIdResponse reportResponse = this.modelMapperService
                .forResponse()
                .map(report, GetByReportIdResponse.class);

        reportResponse.setReportPicture(report.getReportPicture());

        return reportResponse;
    }
    @Override
    public Page<GetByPatientIdNumberResponse> getByPatientIdNumber(String patientIdNumber, int page, int size, Boolean asc) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reportPage;

        if (asc) {
            reportPage = this.reportRepository.findByPatientIdNumberAndIsActiveTrueOrderByReportDateAsc(patientIdNumber, pageable);
        } else {
            reportPage = this.reportRepository.findByPatientIdNumberAndIsActiveTrueOrderByReportDateDesc(patientIdNumber, pageable);
        }

        return reportPage.map(report -> {
            GetByPatientIdNumberResponse response = this.modelMapperService.forResponse().map(report, GetByPatientIdNumberResponse.class);
            response.setReportPicture(report.getReportPicture());
            return response;
        });
    }

    @Override
    public Page<GetByPatientNameResponse> getByPatientName(String patientFirstName, String patientLastName, int page, int size, Boolean asc) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reportPage;

        if (asc) {
            reportPage = this.reportRepository.findByPatientFullNameOrderByReportDateAscContainingIgnoreCase(patientFirstName + " " + patientLastName, pageable);
        } else {
            reportPage = this.reportRepository.findByPatientFullNameContainingIgnoreCaseOrderByReportDateDesc(patientFirstName + " " + patientLastName, pageable);
        }

        return reportPage.map(report -> {
            GetByPatientNameResponse response = this.modelMapperService.forResponse().map(report, GetByPatientNameResponse.class);
            response.setReportPicture(report.getReportPicture());
            return response;
        });
    }

    @Override
    public Page<GetByLaborantNameResponse> getByLaborantName(String laborantFirstName, String laborantLastName, int page, int size, Boolean asc) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Report> reportPage;

        if (asc) {
            reportPage = this.reportRepository.findByLaborantFullNameOrderByReportDateAscContainingIgnoreCase(laborantFirstName+ " " + laborantLastName, pageable);
        } else {
            reportPage = this.reportRepository.findByLaborantFullNameOrderByReportDateDescContainingIgnoreCase(laborantFirstName+ " " + laborantLastName, pageable);
        }

        return reportPage.map(report -> {
            GetByLaborantNameResponse response = this.modelMapperService.forResponse().map(report, GetByLaborantNameResponse.class);
            response.setReportPicture(report.getReportPicture());
            return response;
        });
    }

    @Override
    public void add(CreateReportRequest createReportRequest) {
        Report report = new Report();

        report.setFileNumber(createReportRequest.getFileNumber());
        report.setPatientFirstName(createReportRequest.getPatientFirstName());
        report.setPatientLastName(createReportRequest.getPatientLastName());
        report.setPatientIdNumber(createReportRequest.getPatientIdNumber());
        report.setDiagnosisTitle(createReportRequest.getDiagnosisTitle());
        report.setDiagnosisDetails(createReportRequest.getDiagnosisDetails());

        String base64String = createReportRequest.getReportPicture().split(",")[1];

        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        report.setReportPicture(decodedBytes);

        Laborant laborant = laborantRepository.getById(createReportRequest.getLaborantId());
        report.setLaborant(laborant);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate reportDate = LocalDate.parse(createReportRequest.getReportDate(), formatter);
        report.setReportDate(reportDate);

        this.reportRepository.save(report);
    }
    @Override
    @Transactional
    public void update(UpdateReportRequest updateReportRequest) {
        Report report = new Report();

        report.setId(updateReportRequest.getId());
        report.setFileNumber(updateReportRequest.getFileNumber());
        report.setPatientFirstName(updateReportRequest.getPatientFirstName());
        report.setPatientLastName(updateReportRequest.getPatientLastName());
        report.setPatientIdNumber(updateReportRequest.getPatientIdNumber());
        report.setDiagnosisTitle(updateReportRequest.getDiagnosisTitle());
        report.setDiagnosisDetails(updateReportRequest.getDiagnosisDetails());
        report.setIsActive(true);

        if(updateReportRequest.getReportPicture().contains(","))
        {
            String base64String = updateReportRequest.getReportPicture().split(",")[1];
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);
            report.setReportPicture(decodedBytes);
        }else
        {
            byte[] decodedBytes = Base64.getDecoder().decode(updateReportRequest.getReportPicture());
            report.setReportPicture(decodedBytes);
        }

        Laborant laborant = laborantRepository.getById(updateReportRequest.getLaborantId());
        report.setLaborant(laborant);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate reportDate = LocalDate.parse(updateReportRequest.getReportDate(), formatter);
        report.setReportDate(reportDate);

        this.reportRepository.save(report);

    }

    @Override
    @Transactional
    public void delete(int id) {
        Report report = this.reportRepository.getById(id);
        if (report != null)
        {
            report.setIsActive(false);
            this.reportRepository.save(report);
        }
    }

}
