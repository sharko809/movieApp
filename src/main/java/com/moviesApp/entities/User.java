package com.moviesApp.entities;

import java.util.Date;
import java.util.List;

/**
 * Created by dsharko on 7/28/2016.
 */
public class User {

    private Long id;
    private String name;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
