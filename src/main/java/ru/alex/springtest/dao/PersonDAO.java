package ru.alex.springtest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alex.springtest.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }


    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null); //   Лямда выражение* Поиск объекта (id) в списке. Если его нет - null
    }

    public void save(Person person){

        jdbcTemplate.update("INSERT INTO Person VALUES(3, ?, ?, ?)", person.getName(), person.getAge(), // Добавить инкремент
                person.getEmail());
    }

    public void update(int id, Person updatePerson){ //Внимательно с синтаксисом!! ... = ? WHERE...
         jdbcTemplate.update("UPDATE Person SET name = ?, age =?, email = ? WHERE id = ?", updatePerson.getName(),
                 updatePerson.getAge(),updatePerson.getEmail(), id);
        }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }


}
