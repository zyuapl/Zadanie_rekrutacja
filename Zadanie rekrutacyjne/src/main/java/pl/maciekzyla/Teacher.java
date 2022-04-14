package pl.maciekzyla;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    private String email;

    private String subject;

    private int age;

    private int teacher_indeks;

    private int student_indeks;
}
