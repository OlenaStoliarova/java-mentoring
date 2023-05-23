package pl.mentoring.m16nosql.entity;

public class Sport {

    private String id;
    private String sportName;
    private String sportProficiency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportProficiency() {
        return sportProficiency;
    }

    public void setSportProficiency(String sportProficiency) {
        this.sportProficiency = sportProficiency;
    }
}
