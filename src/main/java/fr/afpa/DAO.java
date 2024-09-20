package fr.afpa;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DAO<T> {

    @SuppressWarnings("exports")
    public Connection con = ConnectionPostgreSQL.getInstance();
    
    /**
     * Permet de récupérer un objet via son ID
     * @param id
     * @return
     */
    public abstract T find(long id);
    
    /**
     * Permet de créer une entrée dans la base de données
     * par rapport à un objet
     * @param obj
     */
    public abstract T create(T obj);
    
    /**
     * Permet de mettre à jour les données d'une entrée dans la base 
     * @param obj
     */
    public abstract T update(T obj);
    
    /**
     * Permet la suppression d'une entrée de la base
     * @param obj
     */
    public abstract boolean delete(T obj);

    protected abstract ArrayList<Contact> findAll();
}