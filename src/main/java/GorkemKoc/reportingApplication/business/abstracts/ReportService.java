package GorkemKoc.reportingApplication.business.abstracts;

import GorkemKoc.reportingApplication.business.reponses.*;
import GorkemKoc.reportingApplication.business.requests.CreateReportRequest;
import GorkemKoc.reportingApplication.business.requests.UpdateReportRequest;
import org.springframework.data.domain.Page;
public interface ReportService {
    int getTotalPages(int pageSize);
    boolean doesReportExist(String fileNumber);
    Page<GetAllReportResponse> getAll(int page, int size);
    Page<GetAllActiveReportResponse> getAllActiveReports(int page, int size);
    GetByReportIdResponse getByReportId(int reportId);
    Page<GetByPatientIdNumberResponse>  getByPatientIdNumber(String patientIdNumber, int page, int size, Boolean asc);
    Page<GetByAscendingReportResponse>  getByAscendingReport(int page, int size);
    Page<GetByDescendingReportResponse>  getByDescendingReport(int page, int size);
    Page<GetByPatientNameResponse> getByPatientName(String patientFirstName, String patientLastName, int page, int size, Boolean asc);
    Page<GetByLaborantNameResponse> getByLaborantName(String laborantFirstName, String laborantLastName, int page, int size, Boolean asc);
    void add(CreateReportRequest createReportRequest);
    void update(UpdateReportRequest updateReportRequest);
    void delete(int id);

}
