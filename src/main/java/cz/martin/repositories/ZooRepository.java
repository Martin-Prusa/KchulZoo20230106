package cz.martin.repositories;

import cz.martin.models.VetAnimalsCount;
import cz.martin.models.VetWithAnimals;
import jakarta.enterprise.context.ApplicationScoped;
import org.checkerframework.checker.units.qual.A;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ZooRepository {

    private final String databaseURL = "jdbc:mariadb://localhost/zoo?user=root&password=password";

    public List<String> getVet() {
        List<String> vet = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(databaseURL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                        SELECT Ote.jmeno FROM Osetrovatele AS Ote
                        """)
        ) {
            while (resultSet.next()) {
                vet.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vet;
    }

    public List<VetAnimalsCount> getAnimalsCount() {
        ArrayList<VetAnimalsCount> vetAnimalsCounts = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(databaseURL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                        SELECT Ote.jmeno, COUNT(Oje.id)
                        FROM Osetrovatele AS Ote JOIN Osetruje AS Oje ON Oje.osetrovatel = Ote.id
                        GROUP BY Ote.id
                        """)
        ) {
            while (resultSet.next()) {
                vetAnimalsCounts.add(new VetAnimalsCount(resultSet.getString(1), resultSet.getInt(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vetAnimalsCounts;
    }

    public List<VetWithAnimals> getVetWithAnimals() {
        ArrayList<VetWithAnimals> vetWithAnimals = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(databaseURL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                                        SELECT Ote.jmeno, GROUP_CONCAT(Z.jmeno ORDER BY Z.jmeno SEPARATOR ";")
                                        FROM Osetrovatele AS Ote JOIN Osetruje AS Oje ON Oje.osetrovatel = Ote.id
                                        JOIN Zvirata AS Z ON Oje.zvire = Z.id
                                        GROUP BY Ote.id
                        """)
        ) {
            while (resultSet.next()) {
                vetWithAnimals.add(new VetWithAnimals(resultSet.getString(1), resultSet.getString(2).split(";")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vetWithAnimals;
    }

    public List<VetWithAnimals> getFiltered(boolean maRad, boolean osetruje) {
        ArrayList<VetWithAnimals> vetWithAnimals = new ArrayList<>();
        System.out.println(maRad+" "+osetruje);

        String sqlQuery = "SELECT Ote.jmeno , GROUP_CONCAT(Z.jmeno ORDER BY Z.jmeno SEPARATOR ';') " +
                        "FROM Osetrovatele AS Ote " +
                        (osetruje ? "JOIN Osetruje AS Oje ON Oje.osetrovatel = Ote.id " : "" ) +
                        (maRad ? "JOIN Ma_Rad AS M ON M.osetrovatel = Ote.id " : "") +
                        (osetruje && !maRad ? "JOIN Zvirata AS Z ON Oje.zvire = Z.id ": "")+
                        (!osetruje && maRad ?"JOIN Zvirata AS Z ON M.druh = Z.druh " : "") +
                        (osetruje && maRad ? "JOIN Zvirata AS Z ON M.druh = Z.druh AND Oje.zvire = Z.id " : "")+
                        "GROUP BY Ote.id ";

        try (
                Connection connection = DriverManager.getConnection(databaseURL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
            while (resultSet.next()) {
                vetWithAnimals.add(new VetWithAnimals(resultSet.getString(1), resultSet.getString(2).split(";")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vetWithAnimals;
    }

    /*
    public List<VetWithAnimals> getVetWithAnimalsLike() {
        ArrayList<VetWithAnimals> vetWithAnimals = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(databaseURL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                                        SELECT Ote.jmeno, GROUP_CONCAT(Z.jmeno ORDER BY Z.jmeno SEPARATOR ",")
                                        FROM Osetrovatele AS Ote JOIN Osetruje AS Oje ON Oje.osetrovatel = Ote.id
                                        JOIN Zvirata AS Z ON Oje.zvire = Z.id
                                        JOIN Ma_Rad AS M ON M.druh = Z.druh AND M.osetrovatel = Ote.id
                                        GROUP BY Ote.id
                        """)
        ) {
            while (resultSet.next()) {
                vetWithAnimals.add(new VetWithAnimals(resultSet.getString(1), resultSet.getString(2).split(",")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vetWithAnimals;
    }

    public List<VetWithAnimals> getVetWithAnimalsLikeOnly() {
        ArrayList<VetWithAnimals> vetWithAnimals = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(databaseURL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("""
                                        SELECT Ote.jmeno, GROUP_CONCAT(Z.jmeno ORDER BY Z.jmeno SEPARATOR ",")
                                        FROM Osetrovatele AS Ote JOIN Ma_Rad AS M ON M.osetrovatel = Ote.id
                                        JOIN Zvirata AS Z ON M.druh = Z.druh
                                        GROUP BY Ote.id
                        """)
        ) {
            while (resultSet.next()) {
                vetWithAnimals.add(new VetWithAnimals(resultSet.getString(1), resultSet.getString(2).split(",")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vetWithAnimals;
    }

    */

}
