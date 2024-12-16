package org.codefromscratch.App.DAO;

import org.codefromscratch.App.Beans.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesseurDAOImpl implements IProfessorDAO {
    public static Connection con;

    public ProfesseurDAOImpl() {
        if (con == null) {
            try {
                con = DriverManager.getConnection("jdbc:sqlite:C:/Users/boufa/sample.db");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Professor save(Professor professor) {
        String sql = professor.getId() == null ?
                "INSERT INTO professors (name, specialite) VALUES (?, ?)" :
                "UPDATE professors SET name = ?, specialite = ? WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, professor.getName());
            ps.setString(2, professor.getSpecialite());
            if (professor.getId() != null) {
                ps.setLong(3, professor.getId());
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating/updating professor failed, no rows affected.");
            }

            if (professor.getId() == null) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    professor.setId(generatedKeys.getLong(1));
                }
            }
            return professor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM professors WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Professor> searchProfessor(String key) {
        ArrayList<Professor> result = new ArrayList<Professor>();
        String sql = "SELECT * FROM professors WHERE name LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("specialite"));
                result.add(professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Professor> searchBySpecialite(String specialite) {
        ArrayList<Professor> result = new ArrayList<Professor>();
        String sql = "SELECT * FROM professors WHERE specialite LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + specialite + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("specialite"));
                result.add(professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Professor> getAllProfessors() {
        ArrayList<Professor> result = new ArrayList<Professor>();
        String sql = "SELECT * FROM professors";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("specialite"));
                result.add(professor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}