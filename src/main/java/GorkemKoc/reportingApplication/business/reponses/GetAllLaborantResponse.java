package GorkemKoc.reportingApplication.business.reponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllLaborantResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String hospitalIdNumber;
    private String username;
    private String password;
    private Boolean isActive;
}
