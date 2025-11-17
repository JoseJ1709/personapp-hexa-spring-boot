package co.edu.javeriana.as.personapp.mapper;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.model.request.EstudiosRequest;
import co.edu.javeriana.as.personapp.model.response.EstudiosResponse;

@Mapper
public class EstudiosMapperRest {

    public EstudiosResponse fromDomainToAdapterRestMaria(Study study) {
        return fromDomainToAdapterRest(study, "MariaDB");
    }

    public EstudiosResponse fromDomainToAdapterRestMongo(Study study) {
        return fromDomainToAdapterRest(study, "MongoDB");
    }

    public EstudiosResponse fromDomainToAdapterRest(Study study, String database) {
        return new EstudiosResponse(
                study.getPerson() != null ? study.getPerson().getIdentification() + "" : "",
                study.getProfession() != null ? study.getProfession().getIdentification() + "" : "",
                study.getGraduationDate() != null ? study.getGraduationDate().toString() : "",
                study.getUniversityName(),
                database,
                "OK");
    }

    public Study fromAdapterToDomain(EstudiosRequest request) {
        Study study = new Study();

        // Asociar persona
        if (request.getCc_per() != null && !request.getCc_per().isEmpty()) {
            Person person = new Person();
            person.setIdentification(Integer.parseInt(request.getCc_per()));
            study.setPerson(person);
        }

        // Asociar profesión
        if (request.getId_prof() != null && !request.getId_prof().isEmpty()) {
            Profession profession = new Profession();
            profession.setIdentification(Integer.parseInt(request.getId_prof()));
            study.setProfession(profession);
        }

        // Fecha de graduación
        if (request.getFecha() != null && !request.getFecha().isEmpty()) {
            study.setGraduationDate(LocalDate.parse(request.getFecha()));
        }

        // Universidad
        study.setUniversityName(request.getUniver());

        return study;
    }
}