package GorkemKoc.reportingApplication.business.concretes;

import GorkemKoc.reportingApplication.business.reponses.*;
import GorkemKoc.reportingApplication.core.utilities.mappers.ModelMapperService;
import GorkemKoc.reportingApplication.dataAccess.abstracts.LaborantRepository;
import GorkemKoc.reportingApplication.dataAccess.abstracts.ReportRepository;
import GorkemKoc.reportingApplication.entities.concretes.Laborant;
import GorkemKoc.reportingApplication.entities.concretes.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportManagerTest {
    private ReportManager reportManager;
    private ReportRepository reportRepository;
    private LaborantRepository laborantRepository;
    private ModelMapperService modelMapperService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        reportRepository = Mockito.mock(ReportRepository.class);
        laborantRepository = Mockito.mock(LaborantRepository.class);
        modelMapperService = Mockito.mock(ModelMapperService.class);
        modelMapper = Mockito.mock(ModelMapper.class);

        reportManager = new ReportManager(reportRepository, laborantRepository, modelMapperService);
    }
    @Test
    void doesReportExist() {

        String fileNumber = "123456";
        Mockito.when(reportRepository.existsByFileNumber(fileNumber)).thenReturn(true);
        boolean result = reportManager.doesReportExist(fileNumber);

        assertTrue(result);
    }
    @Test
    void getTotalPages(){

        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborant(laborant)
                .build();

        Report report2 = Report.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborant(laborant)
                .build();

        int pageSize = 10;

        Pageable pageable = PageRequest.of(0, pageSize);

        Mockito.when(reportRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(report1, report2), pageable, 2));

        int totalPages = reportManager.getTotalPages(pageSize);

        int expectedTotalPages = 1;

        Assertions.assertEquals(expectedTotalPages, totalPages);

    }

    @Test
    void getAll() {
        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborant(laborant)
                .build();

        Report report2 = Report.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborant(laborant)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(reportRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(report1, report2), pageable, 2));

        GetAllReportResponse response1 = GetAllReportResponse.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        GetAllReportResponse response2 = GetAllReportResponse.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(report1, GetAllReportResponse.class)).thenReturn(response1);
        Mockito.when(modelMapperService.forResponse().map(report2, GetAllReportResponse.class)).thenReturn(response2);

        Page<GetAllReportResponse> expected = new PageImpl<>(Arrays.asList(response1, response2), pageable, 2);

        Page<GetAllReportResponse> result = reportManager.getAll(0,10);

        Assertions.assertEquals(result, expected);

    }
    @Test
    void getAllActiveReports() {
        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborant(laborant)
                .build();

        Report report2 = Report.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborant(laborant)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(reportRepository.findAllByIsActiveTrue(pageable)).thenReturn(new PageImpl<>(Arrays.asList(report1, report2), pageable, 2));

        GetAllActiveReportResponse response1 = GetAllActiveReportResponse.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        GetAllActiveReportResponse response2 = GetAllActiveReportResponse.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(report1, GetAllActiveReportResponse.class)).thenReturn(response1);
        Mockito.when(modelMapperService.forResponse().map(report2, GetAllActiveReportResponse.class)).thenReturn(response2);

        Page<GetAllActiveReportResponse> expected = new PageImpl<>(Arrays.asList(response1, response2), pageable, 2);

        Page<GetAllActiveReportResponse> result = reportManager.getAllActiveReports(0,10);

        Assertions.assertEquals(result, expected);

    }

    @Test
    void getByAscendingReport() {
        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborant(laborant)
                .build();

        Report report2 = Report.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023, 2, 2))
                .isActive(true)
                .laborant(laborant)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(reportRepository.findAllByIsActiveTrueOrderByReportDateAsc(pageable)).thenReturn(new PageImpl<>(Arrays.asList(report1, report2), pageable, 2));

        GetByAscendingReportResponse response1 = GetByAscendingReportResponse.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        GetByAscendingReportResponse response2 = GetByAscendingReportResponse.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023, 2, 2))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(report1, GetByAscendingReportResponse.class)).thenReturn(response1);
        Mockito.when(modelMapperService.forResponse().map(report2, GetByAscendingReportResponse.class)).thenReturn(response2);

        Page<GetByAscendingReportResponse> expected = new PageImpl<>(Arrays.asList(response1, response2), pageable, 2);

        Page<GetByAscendingReportResponse> result = reportManager.getByAscendingReport(0,10);

        Assertions.assertEquals(result, expected);

    }

    @Test
    void getByDescendingReport() {
        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborant(laborant)
                .build();

        Report report2 = Report.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023, 2, 2))
                .isActive(true)
                .laborant(laborant)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(reportRepository.findAllByIsActiveTrueOrderByReportDateDesc(pageable)).thenReturn(new PageImpl<>(Arrays.asList(report2, report1), pageable, 2));

        GetByDescendingReportResponse response1 = GetByDescendingReportResponse.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        GetByDescendingReportResponse response2 = GetByDescendingReportResponse.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023, 2, 2))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(report1, GetByDescendingReportResponse.class)).thenReturn(response1);
        Mockito.when(modelMapperService.forResponse().map(report2, GetByDescendingReportResponse.class)).thenReturn(response2);

        Page<GetByDescendingReportResponse> expected = new PageImpl<>(Arrays.asList(response2, response1), pageable, 2);

        Page<GetByDescendingReportResponse> result = reportManager.getByDescendingReport(0,10);

        Assertions.assertEquals(result, expected);

    }
    @Test
    void getByReportId() {
        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborant(laborant)
                .build();



        Mockito.when(reportRepository.findById(1)).thenReturn(Optional.ofNullable(report1));

        GetByReportIdResponse response1 = GetByReportIdResponse.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.now())
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(report1, GetByReportIdResponse.class)).thenReturn(response1);

        GetByReportIdResponse result = reportManager.getByReportId(1);

        Assertions.assertEquals(result, response1);
    }

   @Test
    void getByPatientIdNumber() {

        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborant(laborant)
                .build();

        Report report2 = Report.builder()
                .id(1)
                .fileNumber("67891")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023,2, 2))
                .isActive(true)
                .laborant(laborant)
                .build();

       List<Report> reports = Arrays.asList(report1, report2);

       int page = 0;
       int size = 10;
       boolean asc = true;
       String patientIdNumber = "ID123";

       Pageable pageable = PageRequest.of(page, size);

       Page<Report> reportPage = new PageImpl<>(reports, pageable, reports.size());

       if (asc) {
           Mockito.when(reportRepository.findByPatientIdNumberAndIsActiveTrueOrderByReportDateAsc(patientIdNumber, pageable)).thenReturn(reportPage);
       } else {
           Mockito.when(reportRepository.findByPatientIdNumberAndIsActiveTrueOrderByReportDateDesc(patientIdNumber, pageable)).thenReturn(reportPage);
       }

       GetByPatientIdNumberResponse response1 = GetByPatientIdNumberResponse.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
               .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

       GetByPatientIdNumberResponse response2 = GetByPatientIdNumberResponse.builder()
                .id(1)
                .fileNumber("67891")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
               .reportDate(LocalDate.of(2023,2, 2))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();


        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(report1, GetByPatientIdNumberResponse.class)).thenReturn(response1);
        Mockito.when(modelMapperService.forResponse().map(report2, GetByPatientIdNumberResponse.class)).thenReturn(response2);

       Page<GetByPatientIdNumberResponse> expected = new PageImpl<>(Arrays.asList(response1, response2), pageable, 2);

        Page<GetByPatientIdNumberResponse> result = reportManager.getByPatientIdNumber(patientIdNumber,page,size,asc);

        Assertions.assertEquals(result, expected);
    }

    @Test
    void getByPatientName() {
        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborant(laborant)
                .build();

        Report report2 = Report.builder()
                .id(1)
                .fileNumber("67891")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023,2, 2))
                .isActive(true)
                .laborant(laborant)
                .build();

        List<Report> reports = Arrays.asList(report1, report2);

        String patientFirstName = "John";
        String patientLastName = "Doe";
        int page = 0;
        int size = 10;
        boolean asc = true;
        Pageable pageable = PageRequest.of(page, size);

        Page<Report> reportPage = new PageImpl<>(reports, pageable, reports.size());

        if (asc) {
            Mockito.when(reportRepository.findByPatientFullNameOrderByReportDateAscContainingIgnoreCase(patientFirstName + " " + patientLastName, pageable)).thenReturn(reportPage);
        } else {
            Mockito.when(reportRepository.findByPatientFullNameOrderByReportDateAscContainingIgnoreCase(patientFirstName + " " + patientLastName, pageable)).thenReturn(reportPage);
        }

        GetByPatientNameResponse response1 = GetByPatientNameResponse.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        GetByPatientNameResponse response2 = GetByPatientNameResponse.builder()
                .id(1)
                .fileNumber("67891")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023,2, 2))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(report1, GetByPatientNameResponse.class)).thenReturn(response1);
        Mockito.when(modelMapperService.forResponse().map(report2, GetByPatientNameResponse.class)).thenReturn(response2);

        Page<GetByPatientNameResponse> expected = new PageImpl<>(Arrays.asList(response1, response2), pageable, 2);

        Page<GetByPatientNameResponse> result = reportManager.getByPatientName(patientFirstName,patientLastName,page,size,asc);

        Assertions.assertEquals(result, expected);
    }

    @Test
    void getByLaborantName() {
        Laborant laborant = Laborant.builder()
                .id(1)
                .firstName("Alice")
                .lastName("Johnson")
                .hospitalIdNumber("1234567")
                .username("alicejohnson")
                .password("password123")
                .isActive(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborant(laborant)
                .build();

        Report report2 = Report.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023,2, 2))
                .isActive(true)
                .laborant(laborant)
                .build();

        List<Report> reports = Arrays.asList(report1, report2);

        String laborantFirstName = "Alice";
        String laborantLastName = "Johnson";
        int page = 0;
        int size = 10;
        boolean asc = true;
        Pageable pageable = PageRequest.of(page, size);

        Page<Report> reportPage = new PageImpl<>(reports, pageable, reports.size());

        if (asc) {
            Mockito.when(reportRepository.findByLaborantFullNameOrderByReportDateAscContainingIgnoreCase(laborantFirstName + " " + laborantLastName, pageable)).thenReturn(reportPage);
        } else {
            Mockito.when(reportRepository.findByLaborantFullNameOrderByReportDateAscContainingIgnoreCase(laborantFirstName + " " + laborantLastName, pageable)).thenReturn(reportPage);
        }

        GetByLaborantNameResponse response1 = GetByLaborantNameResponse.builder()
                .id(1)
                .fileNumber("12345")
                .patientFirstName("John")
                .patientLastName("Doe")
                .patientIdNumber("ID123")
                .diagnosisTitle("Title1")
                .diagnosisDetails("Details1")
                .reportDate(LocalDate.of(2020, 1, 1))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        GetByLaborantNameResponse response2 = GetByLaborantNameResponse.builder()
                .id(2)
                .fileNumber("67890")
                .patientFirstName("Jane")
                .patientLastName("Smith")
                .patientIdNumber("ID456")
                .diagnosisTitle("Title2")
                .diagnosisDetails("Details2")
                .reportDate(LocalDate.of(2023,2, 2))
                .isActive(true)
                .laborantId(laborant.getId())
                .laborantFirstName(laborant.getFirstName())
                .laborantLastName(laborant.getLastName())
                .build();

        Mockito.when(modelMapperService.forResponse()).thenReturn(modelMapper);

        Mockito.when(modelMapperService.forResponse().map(report1, GetByLaborantNameResponse.class)).thenReturn(response1);
        Mockito.when(modelMapperService.forResponse().map(report2, GetByLaborantNameResponse.class)).thenReturn(response2);

        Page<GetByLaborantNameResponse> expected = new PageImpl<>(Arrays.asList(response1, response2), pageable, 2);

        Page<GetByLaborantNameResponse> result = reportManager.getByLaborantName(laborantFirstName,laborantLastName,page,size,asc);

        Assertions.assertEquals(result, expected);

    }

}