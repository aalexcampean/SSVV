package ssvv.example;

import org.junit.Before;
import org.junit.Test;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepo;
import ssvv.example.repository.StudentXMLRepo;
import ssvv.example.repository.TemaXMLRepo;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.ValidationException;

import static junit.framework.Assert.assertEquals;

public class WBTAddAssignment {
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


}