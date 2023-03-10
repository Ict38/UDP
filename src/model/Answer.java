package model;

import java.io.Serializable;

public class Answer implements Serializable {
    static final long serialVersionUID = 22L;
    private Student student;

    public Answer(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "student=" + student +
                '}';
    }
}
