package GorkemKoc.reportingApplication.dataAccess.abstracts;

import GorkemKoc.reportingApplication.entities.concretes.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepository extends JpaRepository<Report,Integer>{

    @Transactional
    Page<Report> findAllByIsActiveTrue(Pageable pageable);
    @Transactional
    boolean existsByFileNumber(String fileNumber);
    @Transactional
    Page<Report> findAllByIsActiveTrueOrderByReportDateAsc(Pageable pageable);
    @Transactional
    Page<Report> findAllByIsActiveTrueOrderByReportDateDesc(Pageable pageable);
    @Transactional
    Page<Report> findByPatientIdNumberAndIsActiveTrueOrderByReportDateAsc(String patientIdNumber, Pageable pageable);
    @Transactional
    Page<Report> findByPatientIdNumberAndIsActiveTrueOrderByReportDateDesc(String patientIdNumber, Pageable pageable);
    @Transactional
    @Query("SELECT r FROM Report r WHERE r.isActive = true AND LOWER(CONCAT(r.laborant.firstName, ' ', r.laborant.lastName)) LIKE LOWER(concat('%', :fullName, '%')) ORDER BY r.reportDate ASC")
    Page<Report> findByLaborantFullNameOrderByReportDateAscContainingIgnoreCase(@Param("fullName") String fullName, Pageable pageable);

    @Transactional
    @Query("SELECT r FROM Report r WHERE r.isActive = true AND LOWER(CONCAT(r.laborant.firstName, ' ', r.laborant.lastName)) LIKE LOWER(concat('%', :fullName, '%')) ORDER BY r.reportDate DESC")
    Page<Report> findByLaborantFullNameOrderByReportDateDescContainingIgnoreCase(@Param("fullName") String fullName, Pageable pageable);

    @Transactional
    @Query("SELECT r FROM Report r WHERE r.isActive = true AND LOWER(CONCAT(r.patientFirstName, ' ', r.patientLastName)) LIKE LOWER(concat('%', :fullName, '%')) ORDER BY r.reportDate ASC")
    Page<Report> findByPatientFullNameOrderByReportDateAscContainingIgnoreCase(@Param("fullName") String fullName, Pageable pageable);

    @Transactional
    @Query("SELECT r FROM Report r WHERE r.isActive = true AND LOWER(CONCAT(r.patientFirstName, ' ', r.patientLastName)) LIKE LOWER(concat('%', :fullName, '%')) ORDER BY r.reportDate DESC")
    Page<Report> findByPatientFullNameContainingIgnoreCaseOrderByReportDateDesc(@Param("fullName") String fullName, Pageable pageable);

}
