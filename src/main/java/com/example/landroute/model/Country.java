package com.example.landroute.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    private String ccn3;
    private String cca3;
    private Name name;
    private String region;
    private boolean landlocked;
    private ArrayList<String> borders;
}


