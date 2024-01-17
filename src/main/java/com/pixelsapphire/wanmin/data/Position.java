package com.pixelsapphire.wanmin.data;

import org.jetbrains.annotations.NotNull;

public class Position {

    private final @NotNull String name;
    private final float salary;

    public Position(@NotNull String name, float salary) {
        this.name = name;
        this.salary = salary;
    }

    public float getSalary() {
        return salary;
    }

    public @NotNull String getName() {
        return name;
    }

    public void addToDatabase (){

    }
}
