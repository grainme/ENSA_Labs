package org.codefromscratch.App.Services;

import org.codefromscratch.App.Beans.Professor;
import java.util.List;

public interface ProfesseurService {
    List<Professor> chercherParNom ( String nom );
    List<Professor> getAllProfessors();
}
