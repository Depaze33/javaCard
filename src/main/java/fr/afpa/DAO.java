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
    protected abstract T find(String id);
    
    /**
     * Permet de créer une entrée dans la base de données
     * par rapport à un objet
     * @param obj
     */
    protected abstract T create(T obj);
    
    /**
     * Permet de mettre à jour les données d'une entrée dans la base 
     * @param obj
     */
    protected abstract T update(T obj);
    
    /**
     * Permet la suppression d'une entrée de la base
     * @param obj
     */
    protected abstract boolean delete(String id);

    protected abstract ArrayList<Contact> findAll();
}