package GorkemKoc.reportingApplication.business.reponses;

import GorkemKoc.reportingApplication.entities.concretes.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetByDescendingReportResponse {
    private int id;
    private String fileNumber;
    private String patientFirstName;
    private String patientLastName;
    private String patientIdNumber;
    private String diagnosisTitle;
    private String diagnosisDetails;
    private LocalDate reportDate;
    private int laborantId;
    private String laborantFirstName;
    private String laborantLastName;
    private Boolean isActive;

    private String reportPictureBase64;

    public void setReportPicture(byte[] reportPicture) {
        if (reportPicture != null) {
            this.reportPictureBase64 = Base64.getEncoder().encodeToString(reportPicture);
        } else {
            this.reportPictureBase64 = null;
        }
    }
}
