import axios from "axios";  

export default class LaborantService {
    
    addLaborant(laborant) {
        return axios.post("http://localhost:8080/laborant/add", laborant)
    }

    updateLaborant(laborant) {
        return axios.put("http://localhost:8080/laborant/update", laborant)
    }
    
    getLaborants() {
        return axios.get("http://localhost:8080/laborant/getAll")
    }

    deleteLaborant(id) {
        return axios.delete(`http://localhost:8080/laborant/delete?id=${id}`)
    }
}

