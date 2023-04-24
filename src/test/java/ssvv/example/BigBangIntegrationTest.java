package ssvv.example;

import org.junit.Before;
import org.junit.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepo;
import ssvv.example.repository.StudentXMLRepo;
import ssvv.example.repository.TemaXMLRepo;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.ValidationException;

import java.time.LocalDate;

public class BigBangIntegrationTest {

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
    public void addStudent_givenInvalidId() {
        Student student = new Student("", "Andrei Cupes", 932, "andrei.cupes@gmail.com");
        Student student1 = new Student(null, "Andrei Cupes", 932, "andrei.cupes@gmail.com");
        try {
            service.addStudent(student);
            service.addStudent(student1);
        } catch (ValidationException e) {

        }
    }

    @Test
    public void addAssignment_givenInvalidDeadline() {
        Tema tema1 = new Tema("1", "tema1", 0, 1);
        Tema tema2 = new Tema("1", "tema1", 17, 1);
        try {
            service.addTema(tema1);
            service.addTema(tema2);
        } catch (ValidationException e) {

        }
    }

    @Test
    public void addGrade_givenInvalidGrade(){
        Nota nota1=new Nota("1", "1", "1", 11, LocalDate.of(2023, 4, 2));
        Nota nota2=new Nota("1", "1", "1", -1, LocalDate.of(2023, 4, 2));
        try {
            service.addNota(nota1, "feedback");
            service.addNota(nota2, "feedback");
        }
        catch (ValidationException e){

        }
    }

    @Test
    public void integrationTest(){
        addStudent_givenInvalidId();
        addAssignment_givenInvalidDeadline();
        addGrade_givenInvalidGrade();
    }

}
