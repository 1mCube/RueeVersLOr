import extensions.File;
import extensions.CSVFile;
class RueeVersLOr extends Program{

    final int VIDE = 0;
    final int COWBOY = 1;
    final int MONSTRE = 2;
    final int MURS = 3;
    final int COFFRE = 4;
    final int BONUS = 5;

    final char NORD = 'z';
    final char SUD = 's';
    final char OUEST = 'q';
    final char EST = 'd';
    final char SORTIE = 'x';

    int SCORE;

    void algorithm(){

    }
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////  -/MENU/-    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void menu(){
        afficherMenu();
        choixMenu();
    }
    void choixMenu(){
        boolean valide = false;
        char choix = ' ';
        while(!valide){
            choix = readChar();
            if(choix == '1'){
                //redirige vers la run
            }else if(choix == '2'){
                //redirige vers le marchand
            }else if(choix == '3'){
                //affiche le leaderboard
            }else if(choix == '4'){
                //vers quitter
            }

        }
    }

    void dump(File file){
        int nblignes = 0;
        while(ready(file)){
            println(readLine(file));
        }
    }

    void afficherMenu(){
        File menu = newFile("resources/menu.txt");
        dump(menu);
    }

    char saisie(int monde[][],int[] position){
        boolean valide = false;
        char coup = ' ';
        while(!valide){
            coup = readChar();
            if(coup == NORD){
                if(position[0] != 0){
                    valide = true;
                }
            }if(coup == SUD){
                if(position[0] != length(monde,1)){
                    valide = true;
                }
            }if(coup == OUEST){
                if(position[1] != 0){
                    valide = true;
                }
            }if(coup == EST){
                if(position[1] != length(monde,2)){
                    valide = true;
                }
            }
        }
        return coup;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////   -/Initialisation du monde/-   /////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int[][] generationMonde(){
        int[][] monde = new int[7][10];
        double monstre = 0.1;
        double mur = 0.35;
        double coffre = 0.02;

        for(int c = 0;c<7;c++){
            for(int l = 0;l<10;l++){
                double aleatoire = random();
                if(coffre>aleatoire){
                    monde[c][l] = COFFRE;
                }else if(monstre>aleatoire){
                    monde[c][l] = MONSTRE;
                }else if(mur>aleatoire){
                    monde[c][l] = MURS;
                }else{monde[c][l] = VIDE;}
            }
        }
        monde[3][0] = COWBOY;
        return monde;
        
    }
   

    int[][] mouvement(char c, int positionX, int positionY, int[][] monde){
        if(c == NORD){
            monde[positionX][positionY] = VIDE;
            monde[positionX-1][positionY] = COWBOY;
        }
        if(c == SUD){
            monde[positionX][positionY] = VIDE;
            monde[positionX+1][positionY] = COWBOY;
        }
        if(c == OUEST){
            monde[positionX][positionY] = VIDE;
            monde[positionX][positionY-1] = COWBOY;
        }
        if(c == EST){
            monde[positionX][positionY] = VIDE;
            monde[positionX][positionY+1] = COWBOY;
        }
        return monde;
    }

    int[] positionJoueur(int monde[][]){
        int[] pos = new int[2];
        for(int x = 0;x<length(monde,1);x++){
            for(int y = 0;y<length(monde,2);y++){
                if(monde[x][y] == COWBOY){
                    pos[0] = x;
                    pos[1] = y;
                }
            }
        }
        return pos;
    }
// partie affichage du Jeux : //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //affiche les ligne du tableau : ├───┼───┼───┼───┼───┼───┼───┼───┤
    void afficherligne(int[][] tab){
        print("├");
        for(int x = 0;x<length(tab,2)-1;x++){
            print("───┼");
        }
        print("───┤");
        println();
    }

    //affiche le haut du tableau : ┌───┬───┬───┬───┬───┬───┬───┬───┐
    void afficherHaut(int[][] tab){
        print("┌");
        for(int x = 0;x<length(tab,2)-1;x++){
            print("───┬");
        }
        print("───┐");
        println();
    }

    // affiche le bas du tableau : └───┴───┴───┴───┴───┴───┴───┴───┘
    void afficherBas(int[][] tab){
        print("└");
        for(int x = 0;x<length(tab,2)-1;x++){
            print("───┴");
        }
        print("───┘");
        println();
    }

    //affiche le contenue du tableau : │ C │   │   │ M │   │   │   │
    void afficherContenue(int[][] tab,int c){
        for(int l = 0;l<length(tab,2);l++){
                print("│ ");
                afficherEntite(tab[c][l]);
                print(" ");
            }
            print("│");
            println();
    }

    void afficherEntite(int nb){
        if(nb == VIDE){
            print(" ");
        }else if(nb == COWBOY){
            print("C");
        }else if(nb == MONSTRE){
            print("M");
        }else if(nb == MURS){
            print("■");
        }else if(nb == COFFRE){
            print("□");
        }else if(nb == BONUS){
            print("?");
        }
    }

    void afficher(int[][] tab){
        println("C = COWBOY     M = MONSTRE     ■ = MURS     □ = COFFRE A BUTIN     ? = BONUS");
        afficherHaut(tab);
        for(int c = 0;c<length(tab,1);c++){
            afficherContenue(tab,c);
            if(c == length(tab,1)-1){
                afficherBas(tab);
            }else{afficherligne(tab);}
        }
        println("aller nord : z");
        println("aller ouest : q");
        println("aller est : d");
        println("aller sud : s");
        println("changer de niveau : x");

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////    -/Système de Combat/-   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    int MONSTREPV;
    int COWBOYPV;

    void InitialisationPV(){
        MONSTREPV = 10;
    }
    void afficherCombat(){
        File combat = newFile("resources/combat.txt");
        dump(combat);
    }
}