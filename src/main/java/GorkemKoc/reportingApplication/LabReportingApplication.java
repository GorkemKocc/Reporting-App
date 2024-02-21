package GorkemKoc.reportingApplication;

import GorkemKoc.reportingApplication.core.exceptions.BusinessExepction;
import GorkemKoc.reportingApplication.core.exceptions.ProblemDetails;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SpringBootApplication
@RestControllerAdvice
public class LabReportingApplication {

	public static void main(String[] args) {

		SpringApplication.run(LabReportingApplication.class, args);
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ProblemDetails handleBusinessExeption(BusinessExepction businessExepction){
		ProblemDetails problemDetails = new ProblemDetails();
		problemDetails.setMessage(businessExepction.getMessage());
		return problemDetails;
	}

	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

}
