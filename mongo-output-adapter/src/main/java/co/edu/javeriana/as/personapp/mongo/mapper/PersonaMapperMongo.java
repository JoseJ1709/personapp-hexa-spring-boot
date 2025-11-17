package co.edu.javeriana.as.personapp.mongo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument;

@Mapper
public class PersonaMapperMongo {

    @Autowired
    @Lazy
    private TelefonoMapperMongo telefonoMapperMongo;

    @Autowired
    @Lazy
    private EstudiosMapperMongo estudiosMapperMongo;

    public PersonaDocument fromDomainToAdapter(Person person) {
        PersonaDocument personaDocument = new PersonaDocument();
        personaDocument.setId(person.getIdentification());
        personaDocument.setNombre(person.getFirstName());
        personaDocument.setApellido(person.getLastName());
        personaDocument.setGenero(validateGenero(person.getGender()));
        personaDocument.setEdad(validateEdad(person.getAge()));
        // No mapear teléfonos ni estudios para evitar recursión
        personaDocument.setTelefonos(new ArrayList<>());
        personaDocument.setEstudios(new ArrayList<>());
        return personaDocument;
    }

    private String validateGenero(Gender gender) {
        return gender != null ? gender.name() : null;
    }

    private Integer validateEdad(Integer age) {
        return age != null && age >= 0 ? age : null;
    }

    public Person fromAdapterToDomain(PersonaDocument personaDocument) {
        if (personaDocument == null || personaDocument.getId() == null) {
            return null;
        }

        Person person = new Person();
        person.setIdentification(personaDocument.getId());
        person.setFirstName(personaDocument.getNombre());
        person.setLastName(personaDocument.getApellido());
        person.setGender(validateGender(personaDocument.getGenero()));
        person.setAge(validateAge(personaDocument.getEdad()));
        person.setPhoneNumbers(new ArrayList<>());
        person.setStudies(new ArrayList<>());
        return person;
    }

    private Gender validateGender(String genero) {
        if (genero == null || genero.isEmpty()) {
            return null;
        }
        try {
            return Gender.valueOf(genero);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Integer validateAge(Integer edad) {
        return edad != null && edad >= 0 ? edad : null;
    }
}