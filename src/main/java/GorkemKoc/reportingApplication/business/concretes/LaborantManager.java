package GorkemKoc.reportingApplication.business.concretes;

import GorkemKoc.reportingApplication.business.abstracts.LaborantService;
import GorkemKoc.reportingApplication.business.reponses.GetAllLaborantResponse;
import GorkemKoc.reportingApplication.business.requests.CreateLaborantRequest;
import GorkemKoc.reportingApplication.business.requests.UpdateLaborantRequest;
import GorkemKoc.reportingApplication.core.utilities.mappers.ModelMapperService;
import GorkemKoc.reportingApplication.dataAccess.abstracts.LaborantRepository;
import GorkemKoc.reportingApplication.entities.concretes.Laborant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LaborantManager implements LaborantService {

    private  LaborantRepository laborantRepository;
    private  ModelMapperService modelMapperService;

    @Override
    public List<GetAllLaborantResponse> getAll() {
        List<Laborant> laborants = laborantRepository.findAll();
        List<GetAllLaborantResponse> laborantResponses = laborants.stream()
                .map(laborant -> this.modelMapperService.forResponse()
                        .map(laborant,GetAllLaborantResponse.class)).collect(Collectors.toList());
        return laborantResponses ;
    }

    @Override
    public void add(CreateLaborantRequest createLaborantRequest) {
        Laborant laborant = new Laborant();

        laborant.setFirstName(createLaborantRequest.getFirstName());
        laborant.setLastName(createLaborantRequest.getLastName());
        laborant.setHospitalIdNumber(createLaborantRequest.getHospitalIdNumber());
        laborant.setUsername(createLaborantRequest.getUsername());
        laborant.setPassword(createLaborantRequest.getPassword());
        laborant.setIsActive(createLaborantRequest.getIsActive());

        this.laborantRepository.save(laborant);
    }

    @Override
    public void update(UpdateLaborantRequest updateLaborantRequest) {
        Laborant laborant = this.modelMapperService.forRequest().map(updateLaborantRequest,Laborant.class);
        this.laborantRepository.save(laborant);
    }

    @Override
    public void delete(int id) {
        Laborant laborant = this.laborantRepository.getById(id);
        if (laborant != null)
        {
            laborant.setIsActive(false);
            this.laborantRepository.save(laborant);
        }
    }
}
