/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gsb_frais;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author efleury
 * @author amhoumadi
 */
public class BaseDeDonnees {
    private Connection connexion;
    private String url = "jdbc:mysql://localhost/gsb_frais";
    private String user = "gsb";
    private String passwd = "gsb";
    public BaseDeDonnees(){
        try{
            this.connexion = DriverManager.getConnection(this.url, this.user, this.passwd);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void ajouterUtilisateur(Utilisateur nouvelUtilisateur){
        String sqlRequestValues = "'"+nouvelUtilisateur.getId()+"', '"+nouvelUtilisateur.getNom()+"', '"+nouvelUtilisateur.getPrenom()+"', '"+nouvelUtilisateur.getLogin()+"', '"+nouvelUtilisateur.getMdp()+"', '"+nouvelUtilisateur.getAdresse()+"', '"+nouvelUtilisateur.getCp()+"', '"+nouvelUtilisateur.getVille()+"', '"+nouvelUtilisateur.getDateEmbauche()+"'";
        String sqlRequest = "INSERT INTO visiteur (id, nom, prenom, login, mdp, adresse, cp, ville, dateEmbauche) VALUES ("+sqlRequestValues+")";
        try{
            Statement stmt = connexion.createStatement();
            stmt.executeUpdate(sqlRequest);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimerUtilisateur(Utilisateur utilisateurASupprimer){
        String sqlRequest = "DELETE FROM visiteur WHERE id='"+utilisateurASupprimer.getId()+"'";
        try{
            Statement stmt = connexion.createStatement();
            stmt.executeUpdate(sqlRequest);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean modifierUtilisateur(Utilisateur originel, Utilisateur nouvelUtilisateur){
        String sqlRequestSet = "id='"+nouvelUtilisateur.getId()+"', nom='"+nouvelUtilisateur.getNom()+"', prenom='"+nouvelUtilisateur.getPrenom()+"', login='"+nouvelUtilisateur.getLogin()+"', mdp='"+nouvelUtilisateur.getMdp()+"', adresse='"+nouvelUtilisateur.getAdresse()+"', cp='"+nouvelUtilisateur.getCp()+"', ville='"+nouvelUtilisateur.getVille()+"', dateEmbauche='"+nouvelUtilisateur.getDateEmbauche()+"'";
        String sqlRequest = "UPDATE visiteur SET "+sqlRequestSet+" WHERE id='"+originel.getId()+"'";
        try{
            Statement stmt = connexion.createStatement();
            stmt.executeUpdate(sqlRequest);
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public ArrayList<Utilisateur> getUtilisateurs(String chaineRecherche){
        ArrayList<Utilisateur> users = new ArrayList<>();
        String sqlRequest = "SELECT id, nom, prenom, login, mdp, adresse, cp, ville, dateEmbauche FROM visiteur WHERE id LIKE '"+chaineRecherche+"%' OR nom LIKE '"+chaineRecherche+"%' OR prenom LIKE '"+chaineRecherche+"%'";
        try{
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlRequest);
            while (rs.next()) {
                String userId = rs.getString("id");
                String userNom = rs.getString("nom");
                String userPrenom = rs.getString("prenom");
                String userLogin = rs.getString("login");
                String userMdp = rs.getString("mdp");
                String userAdresse = rs.getString("adresse");
                String userCp = rs.getString("cp");
                String userVille = rs.getString("ville");
                String userDateEmbauche = rs.getString("dateEmbauche");
                users.add(new Utilisateur(userId, userNom, userPrenom, userLogin, userMdp, userAdresse, userCp, userVille, userDateEmbauche));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
    
    public ArrayList<Utilisateur> getUtilisateurs(){
        ArrayList<Utilisateur> users = new ArrayList<>();
        String sqlRequest = "SELECT id, nom, prenom, login, mdp, adresse, cp, ville, dateEmbauche FROM visiteur";
        try{
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlRequest);
            while (rs.next()) {
                String userId = rs.getString("id");
                String userNom = rs.getString("nom");
                String userPrenom = rs.getString("prenom");
                String userLogin = rs.getString("login");
                String userMdp = rs.getString("mdp");
                String userAdresse = rs.getString("adresse");
                String userCp = rs.getString("cp");
                String userVille = rs.getString("ville");
                String userDateEmbauche = rs.getString("dateEmbauche");
                users.add(new Utilisateur(userId, userNom, userPrenom, userLogin, userMdp, userAdresse, userCp, userVille, userDateEmbauche));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
    
    public boolean identifiantValide(String identifiant) {
        ArrayList<String> identifiants = new ArrayList<>();
        String sqlRequest = "SELECT id FROM visiteur";
        try{
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlRequest);
            while (rs.next()) {
                String userId = rs.getString("id");
                identifiants.add(userId);
            }
            return !identifiants.contains(identifiant);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public String genererIdentifiantValide(){
        java.util.Random random = new java.util.Random();
        String[] lettres = {"a", "b", "c", "d", "e", "f"};
        String chaine = lettres[random.nextInt(lettres.length)]+(1+random.nextInt(100));
        while (!identifiantValide(chaine)) {
            chaine = lettres[random.nextInt(lettres.length)]+(1+random.nextInt(100));
        }
        return chaine;
    }
}
