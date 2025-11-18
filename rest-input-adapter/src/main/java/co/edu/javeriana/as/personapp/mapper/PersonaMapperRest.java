package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.model.request.PersonaRequest;
import co.edu.javeriana.as.personapp.model.response.PersonaResponse;

@Mapper
public class PersonaMapperRest {
	
	public PersonaResponse fromDomainToAdapterRestMaria(Person person) {
		return fromDomainToAdapterRest(person, "MariaDB");
	}
	public PersonaResponse fromDomainToAdapterRestMongo(Person person) {
		return fromDomainToAdapterRest(person, "MongoDB");
	}
	
	public PersonaResponse fromDomainToAdapterRest(Person person, String database) {
		return new PersonaResponse(
				person.getIdentification()+"", 
				person.getFirstName(), 
				person.getLastName(), 
				person.getAge()+"", 
				person.getGender().toString(), 
				database,
				"OK");
	}

	public Person fromAdapterToDomain(PersonaRequest request) {
		Person person = new Person();
		
		// Parse identification (dni)
		if (request.getDni() != null && !request.getDni().isEmpty()) {
			person.setIdentification(Integer.parseInt(request.getDni()));
		}
		
		// Set first name and last name
		person.setFirstName(request.getFirstName());
		person.setLastName(request.getLastName());
		
		// Parse age
		if (request.getAge() != null && !request.getAge().isEmpty()) {
			person.setAge(Integer.parseInt(request.getAge()));
		}
		
		// Map gender (sex: "M" -> MALE, "F" -> FEMALE)
		if (request.getSex() != null && !request.getSex().isEmpty()) {
			String sex = request.getSex().toUpperCase();
			if (sex.equals("M")) {
				person.setGender(Gender.MALE);
			} else if (sex.equals("F")) {
				person.setGender(Gender.FEMALE);
			} else {
				person.setGender(Gender.OTHER);
			}
		}
		
		return person;
	}
		
}
