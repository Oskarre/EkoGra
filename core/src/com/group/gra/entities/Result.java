package com.group.gra.entities;

public class Result {
    String name;
    MatchStatus status;

    public Result(String name, MatchStatus status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MatchStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return name.equals(result.name) &&
                status == result.status;
    }
}
