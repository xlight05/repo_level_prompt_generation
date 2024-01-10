/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uniceub.fsw.templates;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class TabelaIterator implements Iterator<List<String>> {  
      
     private Tabela tabela;  
     private int currentPosition;  
     
       
     public TabelaIterator(Tabela tabela) {  
      this.tabela = tabela;
      currentPosition = 1;  
     }  
       
     @Override  
     public boolean hasNext() {  
      return currentPosition < tabela.getQuantidadeLinhas();  
     }  
      
     @Override  
     public List<String> next() {  
      return tabela.getLinha(currentPosition++);
     }  
      
     @Override  
     public void remove() {
         throw new UnsupportedOperationException();
     } 
    
}
