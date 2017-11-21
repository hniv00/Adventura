/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * Interface. Registrace, odstranění a notifikace observeru.
 * 
 * @author hniv00
 * @version ZS 2017/2018
 */
public interface Subject {
    
    void registerObserver(Observer observer);
    
    void removeObserver(Observer observer);
    
    void notifyObservers();
    
}
