package ssvv.example;

import ssvv.example.domain.Student;
import org.junit.Before;
import org.junit.Test;
import ssvv.example.repository.*;

import ssvv.example.service.Service;
import ssvv.example.validation.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTest {

    private Service service;

    @Before
    public void setUp() {
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentRepo = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaRepo = new TemaXMLRepo(filenameTema);
        NotaXMLRepo notaRepo = new NotaXMLRepo(filenameNota);

        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        NotaValidator notaValidator = new NotaValidator(studentRepo, temaRepo);

        service = new Service(studentRepo, studentValidator, temaRepo, temaValidator, notaRepo, notaValidator);
    }

    @Test
    public void addStudent_givenValidStudent() {
        Student student = new Student("1", "Andrei Cupes", 932, "andrei.cupes@gmail.com");

        service.addStudent(student);
        Student addedStudent = service.findStudent("1");

        assertEquals(addedStudent.getID(), "1");

        service.deleteStudent("1");
    }

    @Test
    public void addStudent_givenInvalidId() {
        Student student = new Student("", "Andrei Cupes", 932, "andrei.cupes@gmail.com");
        Student student1 = new Student(null, "Andrei Cupes", 932, "andrei.cupes@gmail.com");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
        assertThrows(ValidationException.class, () -> service.addStudent(student1));
    }


    @Test
    public void addStudent_givenInvalidName() {
        Student studentEmpty = new Student("1", "", 932, "andrei.cupes@gmail.com");
        Student studentNull = new Student("1", null, 932, "andrei.cupes@gmail.com");

        assertThrows(ValidationException.class, () -> service.addStudent(studentEmpty));
        assertThrows(ValidationException.class, () -> service.addStudent(studentNull));
    }


    @Test
    public void addStudent_givenInvalidGroup() {
        Student student = new Student("1", "Andrei", -5, "andrei.cupes@gmail.com");

        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }


    @Test
    public void addStudent_givenInvalidEmail() {
        Student studentEmpty = new Student("1", "Andrei", 932, "");
        Student studentNull = new Student("1", "Andrei", 932, null);

        assertThrows(ValidationException.class, () -> service.addStudent(studentEmpty));
        assertThrows(ValidationException.class, () -> service.addStudent(studentNull));
    }

    @Test
    public void addStudent_givenExistingId() {
        Student student1 = new Student("1", "Andrei Cupes", 932, "andrei.cupes@gmail.com");
        Student student2 = new Student("1", "Andrei Cupes", 932, "andrei.cupes@gmail.com");

        Student result1 = service.addStudent(student1);
        Student result2 = service.addStudent(student2);

        assertEquals(result1, null);
        assertEquals(result2, student2);

        service.deleteStudent("1");
    }

    @Test
    public void addStudent_givenNonExistingId() {
        Student student = new Student("3", "Andrei Cupes", 932, "andrei.cupes@gmail.com");
        Student result = service.addStudent(student);

        assertEquals(null, result);
        service.deleteStudent("3");
    }
}