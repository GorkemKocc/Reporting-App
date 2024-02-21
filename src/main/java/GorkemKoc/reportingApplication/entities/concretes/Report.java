package GorkemKoc.reportingApplication.entities.concretes;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Table(name="reports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="file_number")
    private String fileNumber;
    @Column(name="patient_first_name")
    private String patientFirstName;
    @Column(name="patient_last_name")
    private String patientLastName;
    @Column(name="patient_id_number")
    private String patientIdNumber;
    @Column(name = "diagnosis_title")
    private String diagnosisTitle;
    @Column(name = "diagnosis_details" , length = 500)
    private String diagnosisDetails;
    @Column(name = "report_date")
    private LocalDate reportDate;

    @Lob
    @Column(name = "report_picture", length = 1000)
    private byte[] reportPicture;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "laborant_id")
    private Laborant laborant;

}
