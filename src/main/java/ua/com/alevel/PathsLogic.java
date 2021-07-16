package ua.com.alevel;

import ua.com.alevel.dbdao.DbDao;
import ua.com.alevel.dijkstra.DijkstraAlgorithm;
import ua.com.alevel.entity.Location;
import ua.com.alevel.entity.Problem;
import ua.com.alevel.entity.Route;
import ua.com.alevel.entity.Solution;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PathsLogic {
    private final int DEF = -1;

    public void start(){
        Properties props = loadProperties();
        String url = props.getProperty("url");
        try(Connection connection = DriverManager.getConnection(url, props)) {
            DbDao dbDao = new DbDao(connection);
            List<Location> locationList = dbDao.readAllLocations();
            List<Route> routeList = dbDao.readAllRoutes();
            List<Problem> problemList = dbDao.readAllUnsolvedProblems();
            List<Solution> solutionList = solve(locationList, routeList, problemList);
            dbDao.insertSolution((ArrayList<Solution>) solutionList);
    }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try(InputStream input = PathsLogic.class.getResourceAsStream("/jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return props;
    }

    private List<Solution> solve(List<Location> locations, List<Route> routes, List<Problem> problems) {
        List<Solution> solutions = new ArrayList<>();
        int[][] matrix = new int[locations.size()][locations.size()];
        for (Route route : routes) {
            int fromIndex = DEF;
            int toIndex = DEF;
            for (int i = 0; i < locations.size(); i++) {
                if (locations.get(i).getId() == route.getFromId()) {
                    fromIndex = i;
                } else if (locations.get(i).getId() == route.getToId()) {
                    toIndex = i;
                }
            }
            matrix[fromIndex][toIndex] = route.getCost();
        }
        for (Problem problem : problems) {
            int fromIndex = DEF;
            int toIndex = DEF;
            for (int i = 0; i < locations.size(); i++) {
                if (locations.get(i).getId() == problem.getFromId()) {
                    fromIndex = i;
                } else if (locations.get(i).getId() == problem.getToId()) {
                    toIndex = i;
                }
            }
            int[] paths = DijkstraAlgorithm.dijkstra(matrix, fromIndex);
            solutions.add(new Solution(problem.getId(), paths[toIndex]));
        }
        return solutions;
    }
}