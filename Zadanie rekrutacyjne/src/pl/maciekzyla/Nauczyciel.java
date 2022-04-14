package pl.maciekzyla;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Nauczyciel {

        protected String name;
        protected String przedmiot;
        protected List<Student> spisStudentow = new ArrayList<>();

        public Nauczyciel(String name, String przedmiot) {
            this.name = name;
            this.przedmiot = przedmiot;
        }
    public void dodajUcznia(Student student) {
        spisStudentow.add(student);
        HashSet studenci = new HashSet<>(spisStudentow);
        spisStudentow.clear();
        spisStudentow.addAll(studenci);

        student.spisNauczycieli.add(this);
        HashSet lista = new HashSet<>(student.spisNauczycieli);
        student.spisNauczycieli.clear();
        student.spisNauczycieli.addAll(lista);
    }

    public void wyswietlUczniow() {
            for(Student s : spisStudentow) {
                System.out.println(s.name + ", " + s.kierunek);
            }
    }
}
