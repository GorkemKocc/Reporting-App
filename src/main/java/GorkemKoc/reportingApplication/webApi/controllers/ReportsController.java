package GorkemKoc.reportingApplication.webApi.controllers;

import GorkemKoc.reportingApplication.business.abstracts.ReportService;
import GorkemKoc.reportingApplication.business.reponses.*;
import GorkemKoc.reportingApplication.business.requests.CreateReportRequest;
import GorkemKoc.reportingApplication.business.requests.UpdateReportRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/report")
@RestController
@CrossOrigin
public class ReportsController {
    private ReportService reportService;


    @GetMapping("/totalPages")
    public ResponseEntity<Integer> getTotalPages(@RequestParam(defaultValue = "10") int pageSize) {
        int totalPages = reportService.getTotalPages(pageSize);
        return ResponseEntity.ok(totalPages);
    }
    @GetMapping("/exists")
    public ResponseEntity<?> checkReportExistence(@RequestParam String fileNumber) {
        boolean exists = reportService.doesReportExist(fileNumber);
        return ResponseEntity.ok(exists);
    }
    @GetMapping("/getAll")
    public Page<GetAllReportResponse> getAll(int page, int size) {
        return reportService.getAll(page, size);
    }

    @GetMapping("/getByAscendingReport")
    public Page<GetByAscendingReportResponse> getByAscendingReport(int page, int size) {
        return reportService.getByAscendingReport(page, size);
    }
    @GetMapping("/getByDescendingReport")
    public Page<GetByDescendingReportResponse> getByDescendingReport(int page, int size) {
        return reportService.getByDescendingReport(page, size);
    }

    @GetMapping("/getAllActiveReport")
    public Page<GetAllActiveReportResponse> getAllActiveReportResponses(int page, int size) {
        return reportService.getAllActiveReports(page, size);
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add(@RequestBody() @Valid CreateReportRequest createReportRequest) {
        this.reportService.add(createReportRequest);
    }

    @GetMapping("/getByReportId")
    public GetByReportIdResponse getByReportId(@RequestParam int reportId) {
        return reportService.getByReportId(reportId);
    }

    @GetMapping("/getByPatientIdNumber")
    public Page<GetByPatientIdNumberResponse> getByPatientIdNumber(@RequestParam String patientIdNumber, int page, int size, Boolean asc) {
        return reportService.getByPatientIdNumber(patientIdNumber, page, size, asc);
    }

    @GetMapping("/getByPatientName")
    public Page<GetByPatientNameResponse> getByPatientName(@RequestParam String patientFirstName, String patientLastName, int page, int size, Boolean asc) {
        return reportService.getByPatientName(patientFirstName, patientLastName, page, size, asc);
    }

    @GetMapping("/getByLaborantName")
    public Page<GetByLaborantNameResponse> getByLaborantName(@RequestParam String laborantFirstName, String laborantLastName, int page, int size, Boolean asc) {
        return reportService.getByLaborantName(laborantFirstName, laborantLastName, page, size, asc);
    }

    @PutMapping("/update")
    public void update(@RequestBody() @Valid UpdateReportRequest updateReportRequest) {
        this.reportService.update(updateReportRequest);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam int id) {
        this.reportService.delete(id);
    }

}
