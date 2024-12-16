package org.codefromscratch.App.DAO;

import org.codefromscratch.App.Beans.Professor;
import java.util.List;

public interface IProfessorDAO {
    List<Professor> searchProfessor(String key);
    List<Professor> getAllProfessors();
}
