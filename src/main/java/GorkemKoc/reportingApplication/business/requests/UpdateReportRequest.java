package GorkemKoc.reportingApplication.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReportRequest {

    private int id;
    private String fileNumber;
    private String patientFirstName;
    private String patientLastName;
    @Size(min = 11, max = 11, message = "11 Haneli olmalı.")
    private String patientIdNumber;
    private String diagnosisTitle;
    private String diagnosisDetails;
    @NotBlank
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private String reportDate;
    private String reportPicture;
    private int laborantId;
    private Boolean isActive = true;

}
