package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CityDaoJDBCTest {


    @Test
    public void findCityByIdTest() {

        CityDaoJDBC dao = new CityDaoJDBC();

        City test = dao.findById(35);       //Should be Alger

        System.out.println(test.getName());

        assertEquals(test.getName(), "Alger");

    }

    @Test
    public void findCityByCodeTest() {

        CityDaoJDBC dao = new CityDaoJDBC();

        List<City> test = dao.findByCode("AUS");

        for (City city: test)
             {
                 System.out.println(city.getName());
        }


        //Check if Sydney is in the list
        assertTrue(
                test.stream()
                        .filter(o -> o.getName().equals("Sydney")).findFirst().isPresent()
        );
    }

    @Test
    public void findCityByNameTest() {

        CityDaoJDBC dao = new CityDaoJDBC();

        List<City> test = dao.findByName("Sydney");

        for (City city: test)
        {
            System.out.println(city.getName());
        }


        //Check if Sydney is in the list
        assertTrue(
                test.stream()
                        .filter(o -> o.getName().equals("Sydney")).findFirst().isPresent()
        );
    }

    @Test
    public void findAllTest() {

        CityDaoJDBC dao = new CityDaoJDBC();

        List<City> test = dao.findAll();

/*        for (City city: test)
        {
            System.out.println(city.getName());
        }*/


        //Check if Sydney is in the list
        assertTrue(
                test.stream()
                        .filter(o -> o.getName().equals("Sydney")).findFirst().isPresent()
        );
    }

    @Test
    public void addTest() {

        CityDaoJDBC dao = new CityDaoJDBC();

        City test = new City("Konoha", "JPN", "Hidden Village", 20000L);

        int result = dao.add(test);

    }

    @Test
    public void updateTest() {

        CityDaoJDBC dao = new CityDaoJDBC();

        City test = new City("Konoha", "JPN", "Smoking crater", 0L);

        int result = dao.update(test);

    }

    @Test
    public void deleteTest() {

        CityDaoJDBC dao = new CityDaoJDBC();

        City test = new City("Konoha", "JPN", "Smoking crater", 0L);

        int result = dao.delete(test);

    }

}
