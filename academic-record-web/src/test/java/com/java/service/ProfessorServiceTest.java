package com.java.service;


import com.java.persistence.ProfessorPersistence;
import com.java.service.ProfessorService;
import com.java.service.impl.ProfessorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProfessorServiceTest {

    private ProfessorServiceImpl professorServiceImpl;

    @Mock
    private ProfessorPersistence professorPersistence;



    @BeforeEach
    void setUp(){
        professorServiceImpl = new ProfessorServiceImpl();
    }
}
