package lifegame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;  



/** 
 * ������Ϸ 
 *  
 */  
  
public class LifeGame extends JFrame{  
  
      
    private static final long serialVersionUID = -1660060555234651445L;  
    
    LifeGame(){  
    	
        this.setSize(700,580);  
        this.setTitle("������Ϸ-������̽��ʵ��");  
        this.add(new Show());      
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setLocationRelativeTo(null);  
        this.setResizable(false);  
        
    }  
      
    public static void main(String[] args){  
        LifeGame game = new LifeGame();  
        game.setVisible(true);  
        
    }  
  
}
