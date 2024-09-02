/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author Tien
 * @param <E>
 * @param <K>
 */
abstract public class QLThuVienDAO <E,K> {
    abstract public void insert(E e);
    abstract public void update(E e);
    abstract public void delete(K key);
    abstract public List<E> selectAll();
    abstract public E selectById(K key);
    abstract public List<E> selectBySql(String sql, Object ...args);
    
}
