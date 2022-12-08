package ru.alex.springtest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alex.springtest.dao.PersonDAO;
import ru.alex.springtest.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) { // На объектах какого класса можно использовать валидатор
        return Person.class.equals(aClass); // значение boolean вернёт true, если класс который передаётся в качестве аргумента = классу Person
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if(personDAO.show(person.getEmail()).isPresent())
            errors.rejectValue("email", "", "This email is already taken");

    }
}
