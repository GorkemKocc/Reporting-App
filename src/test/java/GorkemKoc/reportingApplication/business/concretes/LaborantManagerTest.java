package GorkemKoc.reportingApplication.business.concretes;

import GorkemKoc.reportingApplication.business.reponses.GetAllLaborantResponse;
import GorkemKoc.reportingApplication.core.utilities.mappers.ModelMapperService;
import GorkemKoc.reportingApplication.dataAccess.abstracts.LaborantRepository;
import GorkemKoc.reportingApplication.entities.concretes.Laborant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import java.util.Arrays;
import java.util.List;

class LaborantManagerTest {

    private LaborantManager laborantManager;
    private LaborantRepository laborantRepository;
    private ModelMapperService modelMapperService;
    private  ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        laborantRepository = Mockito.mock(LaborantRepository.class);
        modelMapperService = Mockito.mock(ModelMapperService.class);
        modelMapper = Mockito.mock(ModelMapper.class);

        laborantManager = new LaborantManager(laborantRepository, modelMapperService);
    }

    @Test
    public void getAll() {
        Laborant laborant1 = Laborant.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .hospitalIdNumber("1234567")
                .username("johndoe")
                .password("password123")
                .isActive(true)
                .build();

        Laborant laborant2 = Laborant.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Smith")
                .hospitalIdNumber("7654321")
                .username("janesmith")
                .password("password456")
                .isActive(true)
                .build();

        Mockito.when(laborantRepository.findAll()).thenReturn(Arrays.asList(laborant1, laborant2));

        GetAllLaborantResponse response1 = GetAllLaborantResponse.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .hospitalIdNumber("1234567")
                .username("johndoe")
                .password("password123")
                .isActive(true)
                .build();

        GetAllLaborantResponse response2 = GetAllLaborantResponse.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Smith")
                .hospitalIdNumber("7654321")
                .username("janesmith")
                .password("password456")
                .isActive(true)
                .build();

        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(laborant1, GetAllLaborantResponse.class)).thenReturn(response1);
        Mockito.when(modelMapperService.forResponse().map(laborant2, GetAllLaborantResponse.class)).thenReturn(response2);

        List<GetAllLaborantResponse> expected = Arrays.asList(response1, response2);

        List<GetAllLaborantResponse> result = laborantManager.getAll();

        Assertions.assertEquals(result, expected);

    }
}