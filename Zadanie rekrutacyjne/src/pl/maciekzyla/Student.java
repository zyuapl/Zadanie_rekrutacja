package pl.maciekzyla;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Student {
    String name;
    protected String kierunek;
    List<Nauczyciel> spisNauczycieli = new ArrayList<>();

    public Student(String name, String kierunek) {
        this.name = name;
        this.kierunek = kierunek;
    }

    public void dodajNauczyciela(Nauczyciel nauczyciel) {
        spisNauczycieli.add(nauczyciel);
        HashSet lista = new HashSet<>(spisNauczycieli);
        spisNauczycieli.clear();
        spisNauczycieli.addAll(lista);

        nauczyciel.spisStudentow.add(this);
        HashSet studenci = new HashSet<>(nauczyciel.spisStudentow);
        nauczyciel.spisStudentow.clear();
        nauczyciel.spisStudentow.addAll(studenci);
    }
    public void wyswietlNauczycieli() {
        for (Nauczyciel nauczyciel : spisNauczycieli) {
            System.out.println(nauczyciel.name + ", " + nauczyciel.przedmiot);
        }
    }

}
