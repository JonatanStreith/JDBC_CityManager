package org.example;

import java.sql.SQLException;
import java.util.List;

public interface CityDao {

    public City findById(int id);
    List<City> findByCode(String code);

    List<City> findByName(String name);
    List<City> findAll();
    int add(City city);
    int update(City city);
    int delete(City city);
}

    //Minor change: add and update returns int instead of City. Made no sense to get a city back (same?),
    //and you can now use the return to check how many rows were edited.
