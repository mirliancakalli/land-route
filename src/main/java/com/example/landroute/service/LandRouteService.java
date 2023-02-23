package com.example.landroute.service;

import com.example.landroute.data.Region;
import com.example.landroute.exception.NoPathException;
import com.example.landroute.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LandRouteService {

    @Autowired
    private CountriesService countriesService;

    public List<String> getRoute(String destination, String origin)  {

        List<Country> countries = countriesService.getAllCountries();

        Graph graph = new Graph();
        for (Country country : countries) {
            for (String neighbor : country.getBorders()) {
                graph.addEdge(country.getCca3(), neighbor);
            }
        }

        boolean hasLandCrossing = hasLandCrossing(origin, destination, countries);
        if (!hasLandCrossing) {
            throw new NoPathException("No land crossing between " + origin + " and " + destination);
        }

        return graph.shortestPath(origin, destination);
    }

    private boolean hasLandCrossing(String country1, String country2, List<Country> countries) {
        Country country_1 = countries.stream()
                .filter(c -> c.getCca3().equals(country1))
                .findFirst()
                .orElse(null);

        Country country_2 = countries.stream()
                .filter(c -> c.getCca3().equals(country2))
                .findFirst()
                .orElse(null);

        if (country1 != null && country2 != null) {
            if (country_1.getBorders().size() == 0 || country_2.getBorders().size() == 0) {
                return false;
            } else if (!Region.getRegion(country_1.getRegion()).connectedWith(Region.getRegion(country_2.getRegion()))) {
                return false;
            } else return true;
        }
        return true;
    }

    static class Graph {
        private final Map<String, List<String>> graph = new HashMap<>();

        public void addEdge(String origin, String destination) {
            // Add edge from origin to destination
            List<String> neighbors = graph.getOrDefault(origin, new ArrayList<>());
            neighbors.add(destination);
            graph.put(origin, neighbors);

            // Add edge from destination to origin (assuming undirected graph)
            neighbors = graph.getOrDefault(destination, new ArrayList<>());
            neighbors.add(origin);
            graph.put(destination, neighbors);
        }

        public List<String> shortestPath(String start, String end) {
            Map<String, String> parent = new HashMap<>();
            Map<String, Integer> distance = new HashMap<>();
            Queue<String> queue = new LinkedList<>();

            // Initialize distance map with infinity for all nodes except start
            for (String node : graph.keySet()) {
                if (node == null) continue;
                if (node.equals(start)) {
                    distance.put(node, 0);
                } else {
                    distance.put(node, Integer.MAX_VALUE);
                }
            }

            // Start BFS from the start node
            queue.add(start);
            while (!queue.isEmpty()) {
                String current = queue.poll();
                if (current.equals(end)) {
                    break;
                }
                for (String neighbor : graph.get(current)) {
                    int newDistance = distance.get(current) + 1;
                    if (newDistance < distance.get(neighbor)) {
                        distance.put(neighbor, newDistance);
                        parent.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }

            List<String> path = new ArrayList<>();
            String current = end;
            while (parent.containsKey(current)) {
                path.add(current);
                current = parent.get(current);
            }
            path.add(start);
            Collections.reverse(path);
            return path;
        }
    }
}
