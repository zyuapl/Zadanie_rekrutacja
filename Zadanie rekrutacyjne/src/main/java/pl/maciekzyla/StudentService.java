package pl.maciekzyla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Obsługa studentów
@RestController
public class StudentService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    StudentRepository studentRepository;

    //Wyświetlanie wszystkich studentów
    @GetMapping("/students/p/{page}")
    public List<Student> getStudents(@PathVariable("page") int page) {
        int p = 10*(page-1);
        return jdbcTemplate.query("SELECT DISTINCT name, surname, email, age, course, indeks FROM students LIMIT 10 OFFSET ?", BeanPropertyRowMapper.newInstance(Student.class), p);
    }

    //Dodawanie studenta i przypisywanie nauczycieli
    @PostMapping("/students")
    public ResponseEntity addStudent(@RequestBody Student student) {
        if (student.getName().length() <= 2 || student.getAge() < 18 || !student.getEmail().contains("@")) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        Student savedStudent = studentRepository.save(student);
        if (student.getTeacher_indeks() == 0) {
            return ResponseEntity.ok(savedStudent);
        } else {
            jdbcTemplate.update("INSERT INTO teachers(name, surname, email, subject, age, teacher_indeks, student_indeks) SELECT name, surname, email, subject, age, teacher_indeks, ? FROM teachers WHERE teacher_indeks = ?", student.getIndeks(), student.getTeacher_indeks());
            return ResponseEntity.ok(savedStudent);
        }
    }
    //Usuwanie studenta
    @DeleteMapping("/students/delete/{indeks}")
    public ResponseEntity deleteStudent(@PathVariable("indeks") int indeks) {
        jdbcTemplate.update("DELETE FROM students WHERE indeks = ?", indeks);
        jdbcTemplate.update("DELETE FROM teachers WHERE student_indeks = ?", indeks);
        return ResponseEntity.ok(indeks);
    }

    //Wyświetlanie studenta po imieniu i nazwisku
    @GetMapping("/students/{name}-{surname}")
    public ResponseEntity getBySurname(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        Student getStudent = jdbcTemplate.queryForObject("SELECT DISTINCT name, surname, email, age, course, indeks FROM students WHERE name = ? AND surname = ?", BeanPropertyRowMapper.newInstance(Student.class), name, surname);
        return ResponseEntity.ok(getStudent);
    }

    //Wyświetlanie nauczycieli wybranego studenta
    @GetMapping("/students/myteachers/{indeks}")
    public ResponseEntity getMyTeachers(@PathVariable("indeks") int indeks) {
        List<Teacher> myTeachers = jdbcTemplate.query("SELECT * FROM teachers where  student_indeks = ?", BeanPropertyRowMapper.newInstance(Teacher.class), indeks);
        return ResponseEntity.ok(myTeachers);
    }

    //Usuwanie nauczyciela wybranego studenta
    @DeleteMapping("/students/deletemyteacher/{indeks}/{teacher_indeks}")
    public ResponseEntity deleteMyTeacher(@PathVariable("indeks") int indeks, @PathVariable("teacher_indeks") int teacher_indeks) {
        jdbcTemplate.update("DELETE FROM students WHERE indeks = ? AND teacher_indeks = ?", indeks, teacher_indeks);
        jdbcTemplate.update("DELETE FROM teachers WHERE teacher_indeks = ? AND student_indeks = ?", teacher_indeks, indeks);
        return ResponseEntity.ok("Pomyślnie usunięto nauczyciela");
    }
}