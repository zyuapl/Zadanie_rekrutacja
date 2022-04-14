package pl.maciekzyla;

public class Main {

    public static void main(String[] args) {
	    Nauczyciel nauczyciel1 = new Nauczyciel("Gobo", "Fizyka");
	    Nauczyciel nauczyciel2 = new Nauczyciel("Bibi", "Chemia");
	    Nauczyciel nauczyciel3 = new Nauczyciel("Dudu", "Astronomia");

        Student student1 = new Student("Kaka", "Dziadoróbstwo");
        Student student2 = new Student("Bobo", "Brzęczenie");

        student1.dodajNauczyciela(nauczyciel1);
        student1.dodajNauczyciela(nauczyciel2);
        student1.dodajNauczyciela(nauczyciel2);
        student1.dodajNauczyciela(nauczyciel3);

        nauczyciel2.dodajUcznia(student2);
        nauczyciel2.dodajUcznia(student2);

        System.out.println("Wyswietlenie nauczycieli st1");
        student1.wyswietlNauczycieli();

        System.out.println("Wyswietlanie uczniow n2");
        nauczyciel2.wyswietlUczniow();
    }
}
