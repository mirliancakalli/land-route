package com.example.landroute.controller;

import com.example.landroute.service.LandRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routing")
public class LandRouteController {

    @Autowired
    private LandRouteService landRouteService;

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<?> getRoute(@PathVariable String destination, @PathVariable String origin) {
        return new ResponseEntity<>(landRouteService.getRoute(destination, origin), HttpStatus.OK);
    }
}
