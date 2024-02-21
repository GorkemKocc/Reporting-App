package GorkemKoc.reportingApplication.webApi.controllers;

import GorkemKoc.reportingApplication.business.abstracts.LaborantService;
import GorkemKoc.reportingApplication.business.reponses.GetAllLaborantResponse;
import GorkemKoc.reportingApplication.business.requests.CreateLaborantRequest;
import GorkemKoc.reportingApplication.business.requests.UpdateLaborantRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/laborant")
@RestController
@CrossOrigin
public class LaborantsController {
    private LaborantService laborantService;

    @GetMapping("/getAll")
    public List<GetAllLaborantResponse> getAll() {
        return laborantService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add(@RequestBody() @Valid CreateLaborantRequest createLaborantRequest) {
        this.laborantService.add(createLaborantRequest);
    }
    @PutMapping("/update")
    public void update(@RequestBody() @Valid UpdateLaborantRequest updateLaborantRequest) {
        this.laborantService.update(updateLaborantRequest);
    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam int id) {
        this.laborantService.delete(id);
    }
}
