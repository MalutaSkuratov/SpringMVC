package ru.alex.springtest.dao;

import org.springframework.stereotype.Component;
import ru.alex.springtest.model.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private List<Person> people;
    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT,"Alex"));
        people.add(new Person(++PEOPLE_COUNT,"Max"));
        people.add(new Person(++PEOPLE_COUNT,"Jim"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatePerson){
        Person toBeUpdate = show(id);
        toBeUpdate.setName(updatePerson.getName());
    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }


}
