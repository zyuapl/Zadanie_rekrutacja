package pl.maciekzyla;


import lombok.*;

import javax.persistence.*;

    @Entity
    @Table(name = "students")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @RequiredArgsConstructor
    @ToString
    public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NonNull
        private String name;

        @NonNull
        private String surname;

        private String email;

        private int age;

        private String course;

        private int indeks;

        private int teacher_indeks;
    }
