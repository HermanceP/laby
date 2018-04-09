/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modele.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Tao Tuong Vi
 */
public class LabyGraphique extends JFrame implements ActionListener
{
    private TestLaby test;
    private JPanel pan_menu ; // panneau menu
    private JPanel pan_laby ; // panneau labyrinthe
    private JButton boutons[][]; // matrice de boutons
    private Box bot_box;
    private JButton bouton_DFS;
    private JButton bouton_auto;
    private JButton bouton_exit;
    
 //   private Labyrinthe laby;
    
    /** ctor */
    public LabyGraphique(TestLaby test)
    {
        this.test=test;
        setTitle("Test fenetre");
        setSize(800, 600);
        pan_menu= new JPanel(); //on instancie un pannel pour le menu
        pan_laby = new JPanel(); // pareil pour le labyrinthe
      
         
        bouton_DFS=new JButton("DFS"); //DES
        bouton_auto=new JButton("Auto");
        bouton_exit=new JButton("Exit");

        
         boutons = new JButton [test.getLaby().getTailleY()][test.getLaby().getTailleX()] ; // instancier les lignes de la matrice de boutons

         for (int j=0 ; j< test.getLaby().getTailleY(); j++)
        {
            for (int i=0 ; i< test.getLaby().getTailleX(); i++)
            {
                boutons[j][i]=new JButton();
                pan_laby.add(boutons[j][i]);
            }
        }
         
        
        getContentPane().add(pan_menu, BorderLayout.NORTH); // ajouter le panneau dans la fenêtre
        getContentPane().add(pan_laby, BorderLayout.CENTER); // ajouter le panneau dans la fenêtre
    }
    
    public void affiche(Case c) {
        
        
        System.out.println("ligne = " + c.getPositionY() + " colonne=" + c.getPositionX());
    }
    
    /** display the maze grid */
    public void affiche(Labyrinthe laby)
    {
       
        //this.laby=laby;
        //listener
        
        bouton_DFS.addActionListener(this);
        bouton_auto.addActionListener(this);
        bouton_exit.addActionListener(this);
        
        
        pan_menu.add(bouton_DFS);
        pan_menu.add(bouton_auto);
        pan_menu.add(bouton_exit);         
                    
        pan_laby.setLayout(new GridLayout(laby.getTailleY(), laby.getTailleX())); // mise en forme avec une grille
        //boutons = new JButton [laby.getTailleY()][laby.getTailleX()] ; // instancier les lignes de la matrice de boutons

        
        for (int j=0 ; j< laby.getTailleY(); j++)
        {
            for (int i=0 ; i< laby.getTailleX(); i++)
            {
                //boutons[j][i]=new JButton();
                //pan_laby.add(boutons[j][i]);
                Case c=laby.getCase(j,i);
                
                if (c instanceof CaseMur) {
                    boutons[j][i].setText("mur");
                } else {
                    if (c.getVisited()) {
                        boutons[j][i].setText("V");
                    } else {
                        boutons[j][i].setText(" ");
                    }
                }
            }
        }   

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       Object source=ae.getSource(); //qd on clique sur un bouton, on envoie ActionEvent et apres ça prend getSource
       if (source==bouton_exit)
       {
           System.exit(0);
       }
       else if (source==bouton_DFS)
       {
           test.deplacerDFS(test.getLaby().getDepartY(), test.getLaby().getDepartX());
       }
       else if (source==bouton_auto)
       {
           test.deplacerAuto();
       }
    }
}