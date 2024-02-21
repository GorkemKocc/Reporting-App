package GorkemKoc.reportingApplication.dataAccess.abstracts;

import GorkemKoc.reportingApplication.entities.concretes.Laborant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaborantRepository extends JpaRepository<Laborant,Integer> {

}
