package com.kyle.budgetAppBackend.transaction;

public class ParentEntity {

    public long id;
    public String name;

    public ParentEntity() {

    }

    public ParentEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
