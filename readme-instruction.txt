INSTRUKCJA 

1. Połączenie z bazą danych:

- w pliku application.properties(src/main/java/resources/application.properties) należy edytować miejsca z wystąpieniem "xxx" wpisując odpowiednie dane (schema, username, password) dla połączenia z bazą danych


2. Tabele bazy danych (na przykładzie MySQLWorkbanch)

- należy importować tabele: students, teachers znajdujące się w katalogu: "Zadanie_rekrutacyjne/MySQL_tables"

- w tabelach należy dodać auto inkrementacje dla kolumny "id", a także kolumny "id", "age", "teacher_indeks", "student_indeks" oznaczyć jako "Not Null"

3. Klasy StudentService i TeacherService
- klasy odpowiadają za obsługę studentów oraz nauczycieli (dodawanie, usuwanie, wyświetlanie)
