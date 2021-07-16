package ua.com.alevel.dbdao;

import ua.com.alevel.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbDao {
    private final Connection connection;

    public DbDao(Connection connection) {
        this.connection = connection;
    }

    public List<Location> readAllLocations() {
        List<Location> locationsList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM locations")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                locationsList.add(new Location(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locationsList;
    }

    public List<Route> readAllRoutes(){
        List<Route> routesList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM routes")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                routesList.add(new Route(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3), resultSet.getInt(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return routesList;
    }

    public List<Problem> readAllUnsolvedProblems() {
        List<Problem> problemsList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT p.id, p.from_id, p.to_id " +
                "FROM problems p LEFT JOIN solutions s on p.id = s.problem_id " +
                "WHERE s.problem_id IS NULL")) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                problemsList.add(new Problem(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3)));
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return problemsList;
    }

    public void insertSolution(ArrayList<Solution> solutionsList) {
        try (PreparedStatement insertSolutions = connection.prepareStatement("INSERT INTO solutions (problem_id, cost) VALUES (?, ?)")) {
            for (Solution solution : solutionsList) {
                insertSolutions.setInt(1, solution.getProblemId());
                insertSolutions.setInt(2, solution.getCost());

                insertSolutions.addBatch();
            }
            insertSolutions.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}