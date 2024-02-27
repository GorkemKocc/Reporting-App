package GorkemKoc.reportingApplication.business.abstracts;

import GorkemKoc.reportingApplication.business.reponses.GetAllLaborantResponse;
import GorkemKoc.reportingApplication.business.requests.CreateLaborantRequest;
import GorkemKoc.reportingApplication.business.requests.UpdateLaborantRequest;
import java.util.List;
public interface LaborantService {
    List<GetAllLaborantResponse> getAll();
    void add(CreateLaborantRequest createLaborantRequest);
    void update(UpdateLaborantRequest updateLaborantRequest);
    void delete(int id);
}
