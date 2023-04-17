/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Zarrouk
 */
public interface questioninterface <T>{
     public abstract void add (T t) throws SQLException;
    List<T> read() throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;

    
}
