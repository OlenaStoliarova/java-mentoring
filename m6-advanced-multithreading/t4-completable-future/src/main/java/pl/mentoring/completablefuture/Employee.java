package pl.mentoring.completablefuture;

import java.math.BigDecimal;

public class Employee {
    private String id;

    private String firstName;

    private String lastName;

    private String position;

    private BigDecimal salary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("%s\t\t%s %-20s\t%-40s\t\t%s", id, firstName, lastName, position,
            (salary == null) ? "?" : salary.setScale(2).toPlainString());
    }
}
