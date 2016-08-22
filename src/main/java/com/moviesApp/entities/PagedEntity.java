package com.moviesApp.entities;

import java.util.List;

/**
 * Created by dsharko on 8/22/2016.
 */
public class PagedEntity {

    private List<?> entity;
    private Integer numberOfRecords;

    public List<?> getEntity() {
        return entity;
    }

    public void setEntity(List<?> entity) {
        this.entity = entity;
    }

    public Integer getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(Integer numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }


}
