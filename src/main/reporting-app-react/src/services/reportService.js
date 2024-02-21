import axios from "axios";

export default class ReportService {

    getReportTotalPages(size) {
        return axios.get(`http://localhost:8080/report/totalPages?pageSize=${size}`, size)
    }

    doesReportExist(fileNumber) {
        return axios.get(`http://localhost:8080/report/exists?fileNumber=${fileNumber}`, fileNumber)
    }

    getReports(page, size) {
        return axios.get(`http://localhost:8080/report/getAllActiveReport?page=${page}&size=${size}`, page, size)
    }

    getByAscendingReport(page, size) {
        return axios.get(`http://localhost:8080/report/getByAscendingReport?page=${page}&size=${size}`, page, size)
    }

    getByDescendingReport(page, size) {
        return axios.get(`http://localhost:8080/report/getByDescendingReport?page=${page}&size=${size}`, page, size)
    }

    getByReportId(id) {
        return axios.get(`http://localhost:8080/report/getByReportId?reportId=${id}`);
    }

    addReport(report) {
        return axios.post("http://localhost:8080/report/add", report)
    }

    updateReport(report) {
        return axios.put("http://localhost:8080/report/update", report)
    }

    deleteReport(id) {
        return axios.delete(`http://localhost:8080/report/delete?id=${id}`)
    }

    getByPatientIdNumber(patientIdNumber, page, size, asc) {
        return axios.get(`http://localhost:8080/report/getByPatientIdNumber?patientIdNumber=${patientIdNumber}&page=${page}&size=${size}&asc=${asc}`);
    }

    getByPatientName(patientFirstName, patientLastName, page, size, asc) {
        return axios.get(`http://localhost:8080/report/getByPatientName?patientFirstName=${patientFirstName}&patientLastName=${patientLastName}&page=${page}&size=${size}&asc=${asc}`);
    }

    getByLaborantName(laborantFirstName, laborantLastName, page, size, asc) {
        return axios.get(`http://localhost:8080/report/getByLaborantName?laborantFirstName=${laborantFirstName}&laborantLastName=${laborantLastName}&page=${page}&size=${size}&asc=${asc}`);
    }
}