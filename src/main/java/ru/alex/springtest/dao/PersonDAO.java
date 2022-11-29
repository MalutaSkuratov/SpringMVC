package ru.alex.springtest.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

        jdbcTemplate.update("INSERT INTO Person VALUES(2, ?, ?, ?)", person.getName(), person.getAge(), // Добавить инкремент
                person.getEmail());
    }

    public void update(int id, Person updatePerson){ //Внимательно с синтаксисом!! ... = ? WHERE...
         jdbcTemplate.update("UPDATE Person SET name = ?, age =?, email = ? WHERE id = ?", updatePerson.getName(),
                 updatePerson.getAge(),updatePerson.getEmail(), id);
        }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }

    ////////////// Тест производительности пакетной вставки

    public void testMultipleUpdate(){
        List<Person> people = create100People();

        long before = System.currentTimeMillis();

        for (Person person : people){
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?)", person.getId(),
                    person.getName(), person.getAge(),person.getEmail());
        }

        long after = System.currentTimeMillis();

        System.out.println("Время: " + (after - before));
    }

    public void testBatchUpdate(){
        List<Person>people = create100People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setInt(1,people.get(i).getId());
                        preparedStatement.setString(2,people.get(i).getName());
                        preparedStatement.setInt(3,people.get(i).getAge());
                        preparedStatement.setString(4,people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();

        System.out.println("Время: " + (after - before));
    }


    private List<Person> create100People() {
        List<Person>people = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            people.add(new Person(i, "Name" + i, 21, "test" + i + "@mail.com"));

        return people;
    }

}
