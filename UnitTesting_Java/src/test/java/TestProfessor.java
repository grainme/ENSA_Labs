import org.codefromscratch.App.App;
import org.codefromscratch.App.Beans.Professor;
import org.codefromscratch.App.DAO.IProfessorDAO;
import org.codefromscratch.App.Services.ProfesseurService;
import org.codefromscratch.App.Services.ProfesseurServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestProfessor {

    @Mock
    private IProfessorDAO professorDAOMock;

    private ProfesseurService professeurService;
    private Professor testProfessor;

    @Before
    public void setUp() {
        professeurService = ProfesseurServiceImpl.getInstance(professorDAOMock);
        testProfessor = new Professor(1L, "Test Professor", "Test Specialite");
    }

    @Test
    public void validTest1() {
        assertEquals(6, App.fact(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validTest2() {
        App.fact(-1);
    }

    @Test
    public void testProfessorConstructor() {
        assertEquals(Long.valueOf(1L), testProfessor.getId());
        assertEquals("Test Professor", testProfessor.getName());
        assertEquals("Test Specialite", testProfessor.getSpecialite());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProfessorNullName() {
        testProfessor.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProfessorEmptyName() {
        testProfessor.setName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProfessorNullSpecialite() {
        testProfessor.setSpecialite(null);
    }

    @Test
    public void testProfessorEquals() {
        Professor professor1 = new Professor(1L, "Test", "Test");
        Professor professor2 = new Professor(1L, "Test", "Test");
        Professor professor3 = new Professor(2L, "Test", "Test");

        assertTrue(professor1.equals(professor2));
        assertFalse(professor1.equals(professor3));
    }

    @Test
    public void validTestProf1() {
        Professor expectedProf = new Professor(1L, "Marouane", "CS");
        when(professorDAOMock.getAllProfessors())
                .thenReturn(Collections.singletonList(expectedProf));

        List<Professor> professors = professeurService.getAllProfessors();

        assertEquals(1, professors.size());
        assertEquals(expectedProf, professors.get(0));
        verify(professorDAOMock).getAllProfessors();
    }

    @Test
    public void validtestProf1() {
        when(professorDAOMock.searchProfessor("Ahmed"))
                .thenReturn(Collections.emptyList());

        List<Professor> professors = professeurService.chercherParNom("Ahmed");

        assertEquals(0, professors.size());
        verify(professorDAOMock).searchProfessor("Ahmed");
    }

    @Test
    public void validtestProf2() {
        Professor prof = new Professor(1L, "Marouane", "CS");
        when(professorDAOMock.searchProfessor("Marouane"))
                .thenReturn(Collections.singletonList(prof));

        List<Professor> professors = professeurService.chercherParNom("Marouane");

        assertEquals(1, professors.size());
        assertEquals(prof, professors.get(0));
        verify(professorDAOMock).searchProfessor("Marouane");
    }

    @Test
    public void testMockDAO() {
        Professor mockProfessor = new Professor(1L, "Mock Professor", "Mock Specialite");
        when(professorDAOMock.getAllProfessors())
                .thenReturn(Collections.singletonList(mockProfessor));

        List<Professor> professors = professeurService.getAllProfessors();

        assertEquals(1, professors.size());
        assertEquals(mockProfessor, professors.get(0));
        verify(professorDAOMock).getAllProfessors();
    }

    @Test
    public void validTestProfessorService() {
        Professor prof1 = new Professor(1L, "Marouane", "CS");
        Professor prof2 = new Professor(2L, "Ahmed Marouane", "Maths");
        when(professorDAOMock.searchProfessor("Marouane"))
                .thenReturn(Arrays.asList(prof1, prof2));

        List<Professor> professors = professeurService.chercherParNom("Marouane");

        assertEquals(2, professors.size());
        assertTrue(professors.contains(prof1));
        assertTrue(professors.contains(prof2));
        verify(professorDAOMock).searchProfessor("Marouane");
    }
}