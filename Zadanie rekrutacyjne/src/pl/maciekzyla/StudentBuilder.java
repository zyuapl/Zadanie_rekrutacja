package pl.maciekzyla;

public class StudentBuilder {
    private String name;
    private String kierunek;

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setKierunek(String kierunek) {
        this.kierunek = kierunek;
        return this;
    }

    public Student createStudent() {
        return new Student(name, kierunek);
    }
}