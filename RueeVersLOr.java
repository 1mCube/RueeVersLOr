import extensions.File;
import extensions.CSVFile;
class RueeVersLOr extends Program{

    final int VIDE = 0;
    final int COWBOY = 1;
    final int MONSTRE = 2;
    final int MURS = 3;
    final int COFFRE = 4;
    final int BONUS = 5;

    final String NORD = "z";
    final String SUD = "s";
    final String OUEST = "q";
    final String EST = "d";
    final String SORTIE = "x";

    int SCORE;
    int monde[][];

    void algorithm(){
        //connection
        menu();
    }

    void clear(){
        print("\033[H\033[2J");
    }
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////      -/MENU/-    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void menu(){
        clear();
        afficherMenu();
        choixMenu();
    }
    void choixMenu(){
        boolean valide = false;
        int choix;
        while(!valide){
            choix = readInt();
            if(choix == 1){
                monde = generationMonde();
                monde();
                valide = true;
            }else if(choix == 2){
                //redirige vers le marchand
                valide = true;
            }else if(choix == 3){
                //affiche le leaderboard
                valide = true;
            }else if(choix == 4){
                //vers quitter
                valide = true;
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////   -/Initialisation du monde/-   /////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    void monde(){
    	boolean modeCombat = false;
        clear();
        afficher(monde);
        while(!modeCombat){
            int[] pos = positionJoueur(monde);
            String coup = saisie(monde,pos);
            if(equals(coup,NORD)){
                if(pos[0]>0 && monde[pos[0]-1][pos[1]] == MONSTRE){
                    modeCombat = true;
                }
            }if(equals(coup,SUD)){
                if(pos[0]<length(monde,1)-1 && monde[pos[0]+1][pos[1]] == MONSTRE){
                    modeCombat = true;
                }
            }if(equals(coup,OUEST)){
                if(pos[1]>0 && monde[pos[0]][pos[1]-1] == MONSTRE){
                    modeCombat = true;
                }
            }if(equals(coup,EST)){
                if(pos[1]<length(monde,2)-1 && monde[pos[0]][pos[1]+1] == MONSTRE){
                    modeCombat = true;
                }
            }else{mouvement(coup,pos[0],pos[1],monde);
            clear();
            afficher(monde);}
        }
        if(modeCombat){
            combat();
        }
    }

    String saisie(int monde[][],int[] position){
        int positionX = position[0];
        int positionY = position[1];
        boolean valide = false;
        String coup = "";
        while(!valide){
            coup = readString();
            if(equals(coup,NORD)){
                if(position[0] != 0 && monde[positionX-1][positionY] != MURS){
                    valide = true;
                }
            }if(equals(coup,SUD)){
                if(position[0] != length(monde,1) && monde[positionX+1][positionY] != MURS){
                    valide = true;
                }
            }if(equals(coup,OUEST)){
                if(position[1] != 0 && monde[positionX][positionY-1] != MURS){
                    valide = true;
                }
            }if(equals(coup,EST)){
                if(position[1] != length(monde,2) && monde[positionX][positionY+1] != MURS){
                    valide = true;
                }
            }if(equals(coup,SORTIE)){
                menu();
                valide = true;
            }
        }
        return coup;
    }
    
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
        monde[4][1] = COWBOY;
        return monde;
        
    }
   

    int[][] mouvement(String c, int positionX, int positionY, int[][] monde){
        if(equals(c,NORD)){
            monde[positionX][positionY] = VIDE;
            monde[positionX-1][positionY] = COWBOY;
        }
        if(equals(c,SUD)){
            monde[positionX][positionY] = VIDE;
            monde[positionX+1][positionY] = COWBOY;
        }
        if(equals(c,OUEST)){
            monde[positionX][positionY] = VIDE;
            monde[positionX][positionY-1] = COWBOY;
        }
        if(equals(c,EST)){
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
        afficherHaut(tab);
        for(int c = 0;c<length(tab,1);c++){
            afficherContenue(tab,c);
            if(c == length(tab,1)-1){
                afficherBas(tab);
            }else{afficherligne(tab);}
        }
        println("C = COWBOY     M = MONSTRE     ■ = MURS     □ = COFFRE A BUTIN     ? = BONUS");
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

    void combat(){
        clear();
        afficherCombat();
    }

    void InitialisationPV(){
        MONSTREPV = 10;
    }
    void afficherCombat(){
        File combat = newFile("resources/combat.txt");
        dump(combat);
    }
}