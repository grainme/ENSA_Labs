package org.codefromscratch.App.Services;

import org.codefromscratch.App.Beans.Professor;
import org.codefromscratch.App.DAO.IProfessorDAO;

import java.util.List;

public class ProfesseurServiceImpl implements ProfesseurService {
    private static ProfesseurService instance;
    private final IProfessorDAO professorDAO;

    private ProfesseurServiceImpl(IProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    public static ProfesseurService getInstance(IProfessorDAO professorDAO) {
        instance = new ProfesseurServiceImpl(professorDAO);
        return instance;
    }

    @Override
    public List<Professor> chercherParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return professorDAO.searchProfessor(nom);
    }

    @Override
    public List<Professor> getAllProfessors() {
        return professorDAO.getAllProfessors();
    }
}