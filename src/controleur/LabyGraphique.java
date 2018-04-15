/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import modele.*;


/**
 *
 * @author Tao Tuong Vi
 */
public class LabyGraphique extends JFrame implements ActionListener
{
    private Icon img;
    private Icon img2;
    
    private TestLaby test;
    private JPanel pan_menu ; // panneau menu
    private JPanel pan_laby ; // panneau labyrinthe
    private JButton boutons[][]; // matrice de boutons
    private Box bot_box;
    private JButton bouton_DFS;
    private JButton bouton_auto;
    private JButton bouton_exit;
    private JButton bouton_clavier;
    
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
        bouton_clavier=new JButton("Clavier");

        
         boutons = new JButton [test.getLaby().getTailleY()][test.getLaby().getTailleX()] ; // instancier les lignes de la matrice de boutons

         for (int j=0 ; j< test.getLaby().getTailleY(); j++)
        {
            for (int i=0 ; i< test.getLaby().getTailleX(); i++)
            {
                boutons[j][i]=new JButton();
                pan_laby.add(boutons[j][i]);
            }
        }
         
         
         //LECTURE FICHIER DE L'IMAGE
        try
        {                
            Image image = ImageIO.read(new File("D:\\Documents\\ECE 2018\\JAVA\\PROJET\\PROJET_GITHUB\\Labyrinthe2018-master\\laby\\sprite.png"));
            image=make_col_transparent(image);
            img = new ImageIcon(image);
            
        } catch (IOException ex) {
            System.out.println("lien sprite.png n'est pas le bon ");
        }
        
        //LECTURE FICHIER DE L'IMAGE MUR
        try
        {                
            Image image2 = ImageIO.read(new File("D:\\Documents\\ECE 2018\\JAVA\\PROJET\\PROJET_GITHUB\\Labyrinthe2018-master\\laby\\mur.png"));
            image2=make_col_transparent(image2);
            img2 = new ImageIcon(image2);
            
        } catch (IOException ex) {
            System.out.println("lien mur.png n'est pas le bon ");
        }
         
        
       // JLabel picLabel = new JLabel(new ImageIcon(image));
        
        
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
        bouton_clavier.addActionListener(this);
        
        pan_menu.add(bouton_DFS);
        pan_menu.add(bouton_auto);
        pan_menu.add(bouton_clavier);
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
                    boutons[j][i].setIcon(img2);
                } else {
                    if (c.getVisited()) {
                        boutons[j][i].setText("V");
                        boutons[j][i].setIcon(null);
                    } else {
                        boutons[j][i].setText(" ");
                    }
                }
            }
        }   
        
        
        boutons[laby.getCurrentPositionY()][laby.getCurrentPositionX()].setIcon(img);
        boutons[laby.getCurrentPositionY()][laby.getCurrentPositionX()].setText("");

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
       else if(source==bouton_clavier)
       {
           System.out.println("Se deplacer avec les touches directionelles!");
           pan_laby.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    boolean arrivee=false;
                    try{    
                    if(key == KeyEvent.VK_LEFT){
                        arrivee=test.getLaby().deplacerClaviergauche(test);
                        e.consume();
                    }
                    if(key == KeyEvent.VK_RIGHT){
                        arrivee=test.getLaby().deplacerClavierdroite(test);
                        e.consume();
                    }
                    if(key == KeyEvent.VK_UP){
                        arrivee=test.getLaby().deplacerClavierhaut(test);
                        e.consume();
                    }
                    if(key == KeyEvent.VK_DOWN){
                        arrivee=test.getLaby().deplacerClavierbas(test);
                        e.consume();
                    }
                        
                    if(arrivee==true){
                        pan_laby.setFocusable(false);
                    }
                    }
                    catch (ImpossibleMoveException ex){
                        System.out.println("pbm");
                    }
                }
            });
            pan_laby.setFocusable(true);
            pan_laby.requestFocusInWindow();
        }
    }


    public static Image make_col_transparent( Image img)
    {
        ImageFilter filter = new RGBImageFilter() {
            int transparentColor = Color.white.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == transparentColor) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(img.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }






}





