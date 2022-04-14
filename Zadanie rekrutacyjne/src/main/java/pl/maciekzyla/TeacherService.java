package pl.maciekzyla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Obsługa nauczycieli
@RestController
public class TeacherService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TeacherRepository teacherRepository;

    //Wyświetlanie wszystkich nauczycieli
    @GetMapping("/teachers/p/{page}")
    public ResponseEntity getTeachers(@PathVariable("page") int page) {
        int p = 10*(page-1);
        List <Teacher> allTeachers = jdbcTemplate.query("SELECT DISTINCT name, surname, email, subject, age, teacher_indeks FROM teachers ORDER BY surname LIMIT 10 OFFSET ?",
                BeanPropertyRowMapper.newInstance(Teacher.class), p);
        return ResponseEntity.ok(allTeachers);
    }

    //Dodawanie nauczyciela oraz przypisywanie studentów
    @PostMapping("/teachers")
    public ResponseEntity addTeacher(@RequestBody Teacher teacher) {
        if (teacher.getAge()<=18 || teacher.getName().length()<=2 || !teacher.getEmail().contains("@")) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        Teacher savedTeacher = teacherRepository.save(teacher);
        if (teacher.getStudent_indeks() == 0) {
            return ResponseEntity.ok(savedTeacher);
        }
        else {
            jdbcTemplate.update("INSERT INTO students(name, surname, email, age, course, indeks, teacher_indeks) SELECT name, surname, email, age, course, indeks, ? FROM students WHERE indeks = ?", teacher.getTeacher_indeks(), teacher.getStudent_indeks());
            return ResponseEntity.ok(savedTeacher);
        }
    }

    //Usuwanie nauczyciela
    @DeleteMapping("/teachers/delete/{teacher_indeks}")
    public ResponseEntity deleteTeacher(@PathVariable("teacher_indeks") int teacher_indeks) {
        jdbcTemplate.update("DELETE FROM teachers WHERE teacher_indeks = ?", teacher_indeks);
        jdbcTemplate.update("DELETE FROM students WHERE teacher_indeks = ?", teacher_indeks);
        return ResponseEntity.ok(teacher_indeks);
    }

    //Wyświetlanie nauczyciela po imieniu i nazwisku
    @GetMapping("/teachers/{name}-{surname}")
    public ResponseEntity getBySurname(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        Teacher getTeacher = jdbcTemplate.queryForObject("SELECT DISTINCT name, surname, email, age, subject, teacher_indeks FROM teachers WHERE name = ? AND surname = ?", BeanPropertyRowMapper.newInstance(Teacher.class), name, surname);
        return ResponseEntity.ok(getTeacher);
    }

    //Wyświetlanie studentów wybranego nauczyciela
    @GetMapping("/teachers/mystudents/{teacher_indeks}")
    public ResponseEntity getMyTeachers(@PathVariable("teacher_indeks") int teacher_indeks) {
        List<Student> myStudents = jdbcTemplate.query("SELECT * FROM students where teacher_indeks = ?",
                BeanPropertyRowMapper.newInstance(Student.class), teacher_indeks);
        return ResponseEntity.ok(myStudents);
    }

    //Usuwanie wybranego studenta dla wybranego nauczyciela
    @DeleteMapping("/teachers/deletemystudent/{teacher_indeks}/{student_indeks}")
            public ResponseEntity deleteMyStudent(@PathVariable("teacher_indeks") int teacher_indeks, @PathVariable("student_indeks") int student_indeks) {
        jdbcTemplate.update("DELETE FROM teachers WHERE student_indeks = ? AND teacher_indeks = ?", student_indeks, teacher_indeks);
        jdbcTemplate.update("DELETE FROM students WHERE indeks = ? AND teacher_indeks = ?", student_indeks, teacher_indeks);
        return ResponseEntity.ok("Pomyślnie usunięto studenta");
    }
}
