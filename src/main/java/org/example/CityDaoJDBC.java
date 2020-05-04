package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CityDaoJDBC implements CityDao {

    //Let's have singletons.
    private static final Properties settings = getSettings();

    private static final String connectionUrl = settings.getProperty("db.url");
    private static final String userName = settings.getProperty("db.user");
    private static final String password = settings.getProperty("db.password");

    //Why not keep our prepared queries somewhere easy to find?
    private String findByIdPrep = "SELECT * FROM city WHERE id = ?";
    private String findByCodePrep = "SELECT * FROM city WHERE CountryCode LIKE ?";
    private String findByNamePrep = "SELECT * FROM city WHERE Name LIKE ?";
    private String findAllPrep = "SELECT * FROM city";

    private String addCityPrep = "INSERT INTO city (Name, CountryCode, District, Population) VALUES(?,?,?,?)";
    private String updateCityPrep = "UPDATE city SET CountryCode = ?, District = ?, Population = ? WHERE name = ?";
    private String deleteCityPrep = "DELETE FROM city WHERE name = ?";


    @Override
    public City findById(int id) {

        City city = null;

            //Try-With-Resources FTW!
        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {


            //Default type/conc modes, to simplify
            PreparedStatement findById = conn.prepareStatement(findByIdPrep);

            findById.setInt(1, id);

            ResultSet rs = findById.executeQuery();

            if (rs.next()) {
                city =
                        new City(rs.getString("Name"), rs.getString("CountryCode"),
                                rs.getString("District"), rs.getLong("Population"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return city;
    }

    @Override
    public List<City> findByCode(String code) {

        List<City> cities = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {

            PreparedStatement findByCode = conn.prepareStatement(findByCodePrep);

            findByCode.setString(1, code);

            ResultSet rs = findByCode.executeQuery();

            while (rs.next()) {


                cities.add(
                        new City(rs.getString("Name"), rs.getString("CountryCode"),
                                rs.getString("District"), rs.getLong("Population"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }

    @Override
    public List<City> findByName(String name) {

        List<City> cities = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {

            PreparedStatement findByName = conn.prepareStatement(findByNamePrep);

            findByName.setString(1, name);

            ResultSet rs = findByName.executeQuery();

            while (rs.next()) {


                cities.add(
                        new City(rs.getString("Name"), rs.getString("CountryCode"),
                                rs.getString("District"), rs.getLong("Population"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }

    @Override
    public List<City> findAll() {

        List<City> cities = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {


            PreparedStatement findAll = conn.prepareStatement(findAllPrep);


            ResultSet rs = findAll.executeQuery();

            while (rs.next()) {


                cities.add(
                        new City(rs.getString("Name"), rs.getString("CountryCode"),
                                rs.getString("District"), rs.getLong("Population"))
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }

    @Override
    public int add(City city) {

        int result = 0;

        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {

            PreparedStatement add = conn.prepareStatement(addCityPrep);

            add.setString(1, city.getName());
            add.setString(2, city.getCountryCode());
            add.setString(3, city.getDistrict());
            add.setLong(4, city.getPopulation());

            result = add.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(result == 0)
            System.out.println("No records were changed.");
        else
            System.out.println(result + " records were changed.");

        return result;
    }

    @Override
    public int update(City city) {

        int result = 0;

        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {

            PreparedStatement add = conn.prepareStatement(updateCityPrep);

            add.setString(1, city.getCountryCode());
            add.setString(2, city.getDistrict());
            add.setLong(3, city.getPopulation());
            add.setString(4, city.getName());

            result = add.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(result == 0)
            System.out.println("No records were changed.");
        else
            System.out.println(result + " records were changed.");


        return result;
    }

    @Override
    public int delete(City city) {

        int result = 0;

        try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {

            PreparedStatement add = conn.prepareStatement(deleteCityPrep);

            add.setString(1, city.getName());

            result = add.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(result == 0)
            System.out.println("No records were changed.");
        else
            System.out.println(result + " records were changed.");

        return result;
    }


    /*  ------------------------- Non-Dao stuff -------------------------  */

    //Why yes, I'm using properties, could you tell? Make sure the data is correct for your environment.
    public static Properties getSettings() {

        Properties settings = null;

        try (InputStream input = new FileInputStream("src/main/Resources/config.properties")) {

            settings = new Properties();

            if (input == null) {
                System.out.println("Properties file not found.");
            } else {
                settings.load(input);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return settings;
    }

}
