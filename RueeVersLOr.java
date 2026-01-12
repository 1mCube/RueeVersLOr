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
        ecranConnexion();
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
                File marchand = newFile("ressources/marchand.txt");
                dump(marchand);
                sleep(3000);
                marchand();
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
        File menu = newFile("ressources/menu.txt");
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
                    mouvement(coup,pos[0],pos[1],monde);
                    modeCombat = true;
                }else{mouvement(coup,pos[0],pos[1],monde);
                    clear();
                    afficher(monde);
            }
            }else if(equals(coup,SUD)){
                if(pos[0]<length(monde,1)-1 && monde[pos[0]+1][pos[1]] == MONSTRE){
                    mouvement(coup,pos[0],pos[1],monde);
                    modeCombat = true;
                }else{mouvement(coup,pos[0],pos[1],monde);
                    clear();
                    afficher(monde);
            }
            }else if(equals(coup,OUEST)){
                if(pos[1]>0 && monde[pos[0]][pos[1]-1] == MONSTRE){
                    mouvement(coup,pos[0],pos[1],monde);
                    modeCombat = true;
                }else{mouvement(coup,pos[0],pos[1],monde);
                    clear();
                    afficher(monde);
            }
            }else if(equals(coup,EST)){
                if(pos[1]<length(monde,2)-1 && monde[pos[0]][pos[1]+1] == MONSTRE){
                    mouvement(coup,pos[0],pos[1],monde);
                    modeCombat = true;
                }else{mouvement(coup,pos[0],pos[1],monde);
                    clear();
                    afficher(monde);}
            }
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
        println("aller nord : z     aller ouest : q     aller est : d     aller sud : s     changer de niveau : x");
        afficherPVCowboy(COWBOYPV);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////    -/Système de Combat/-   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    int MONSTREPV;
    int COWBOYPV;
    int DAMAGE;

    void combat(){
        clear();
        InitialisationPV();
        while(MONSTREPV > 0){
            afficherCombat();
            afficherPVCowboy(COWBOYPV);
            String arme = choisirArme();
            if(poserQuestion(tirerQuestion(arme))){
                MONSTREPV = MONSTREPV - DAMAGE;
            }else{COWBOYPV = COWBOYPV - 1;}
        }
    }
    void afficherPVCowboy(int pv){
        print("VOS PV : ");
        for(int cpt = 0;cpt<pv;cpt++){
            print("❤︎");
        }
        println();
    }

    void InitialisationPV(){
        MONSTREPV = 10;
    }
    void afficherCombat(){
        File combat = newFile("ressources/combat.txt");
        dump(combat);
    }

    String choisirArme(){
        String difficulte="";
        println("--- Quel arme choisir ?---");
        println("--- 1.Revolver - 1 de dégât - (questions faciles)---");
        println("--- 2.Fusil - 2 de dégâts - (questions moyennes)---");
        println("--- 3.Canon scié - 3 de dégâts - (questions difficiles)---");

        int choix = readInt();
        if (choix == 1){
            difficulte="facile";
            DAMAGE = 1;
        }else if (choix == 2){
            difficulte="moyen";
            DAMAGE = 2;
        }else if (choix == 3){
            difficulte="difficile";
            DAMAGE = 3;
        } else {
            println("Choisir un chiffre entre 1 et 3");
            choisirArme();
        }
        return difficulte;
    }

    String[] themes = new String[]{"maths", "francais", "histoire", "anglais"};

    Question tirerQuestion(String difficulteVoulue) {
        //Choix d'un thème au hasard.
        String themeAleatoire = themes[(int) (random() * length(themes))];
        CSVFile fichier = loadCSV("ressources/questions.csv", ';');    
        //Comptage du nombre de questions pour pouvoir la tirée.
        int nbMatch = 0;
        for (int i = 0; i < rowCount(fichier); i++) {
            if (equals(getCell(fichier, i, 6), themeAleatoire) && equals(getCell(fichier, i, 7), difficulteVoulue)) {
                nbMatch++;
            }
        }
        if (nbMatch == 0) return null;
        //On en tire une au sort.
        int indexChoisi = (int) (random() * nbMatch);
        int compteur = 0;
        for (int i = 0; i < rowCount(fichier); i++) {
            if (equals(getCell(fichier, i, 6), themeAleatoire) && equals(getCell(fichier, i, 7), difficulteVoulue)) {
                if (compteur == indexChoisi) {
                    Question q = new Question();
                    q.id = stringToInt(getCell(fichier, i, 0));
                    q.texte = getCell(fichier, i, 1);
                    q.reponses = new String[]{getCell(fichier, i, 2), getCell(fichier, i, 3), getCell(fichier, i, 4)};
                    q.bonneReponse = getCell(fichier, i, 5);
                    q.theme = getCell(fichier, i, 6);
                    q.difficulte = getCell(fichier, i, 7);
                    return q;
                }
                compteur++;
            }
        }
        return null;
    }

    boolean poserQuestion(Question q) {
        //on pose la question dans le terminal
        println("\n----------------------------------------");
        println(q.texte);
        println("----------------------------------------");
        println("A. " + q.reponses[0]);
        println("B. " + q.reponses[1]);
        println("C. " + q.reponses[2]);
        println("----------------------------------------"); 
        print("Ta réponse : ");
        String choix = readString();
        
        if (equals(choix,q.bonneReponse)) {
            println("JUSTE !");
            return true;
        } else {
            println("FAUX ! La réponse était : " + q.bonneReponse );
            return false;
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////    -/Système d'inscription/connexion/-   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    String PSEUDO_ACTUEL = "";

    void ecranConnexion() {
        println("===Bienvenue dans la Quête de La Ruée Vers L'Or Cowboy !===");
        println("1. Connexion");
        println("2. Inscription");

        int choix = readInt();
        if (choix == 1) {
            connexion();
        } else if (choix == 2) {
            inscription();
        } else {
            println("Veuillez choisir un chiffre entre 1 et 2");
            ecranConnexion();
        }
    }

    boolean verifierIdentifiants(String pseudo, String mdp) {
        CSVFile f = loadCSV("ressources/joueurs.csv", ';');
        if (f == null) return false;
        for (int i = 0; i < rowCount(f); i++) {
            // Colonne 0 = pseudo / Colonne 1 = mdp
            if (equals(getCell(f, i, 0), pseudo) && equals(getCell(f, i, 1), mdp)) {
                return true;
            }
        }
        return false;
    }

    void connexion() {
        boolean reussi = false;
        CSVFile f = loadCSV("ressources/joueurs.csv", ';');
        while (!reussi) {
            print("Pseudo : ");
            String pseudo = readString();
            print("Mot de passe : ");
            String motdepasse = readString();
        
            if (verifierIdentifiants(pseudo, motdepasse)) {
                println("Connexion réussie ! Bonjour " + pseudo);
                PSEUDO_ACTUEL = pseudo;
                for (int i=0; i<rowCount(f) ;i++){
                    if(equals(getCell(f, i, 0),pseudo)){
                        COWBOYPV = stringToInt(getCell(f, i, 3));
                    }
                }
                reussi = true;
            } else {
                println("Pseudo ou mot de passe incorrect. Réessayez.");
            }
        }
    }

    void inscription() {
        //Charger le CSV existant
        CSVFile f = loadCSV("ressources/joueurs.csv", ';');
        print("Choisissez un pseudo : ");
        String pseudo = readString();
        if (f !=null){
            for(int i = 0; i < rowCount(f); i++){
                if (equals(getCell(f, i, 0), pseudo)) {
                    println("ERREUR : Ce pseudo est déjà utilisé par un autre cowboy !");
                    println("Veuillez en choisir un autre.");
                    inscription();
                    return;
                }
            }
        }
        
        print("Choisissez un mot de passe : ");
        String mdp = readString();
        int nbLignesExistantes = 0;
        if (f != null) {
            nbLignesExistantes = rowCount(f);
        }
        //Créer le nouveau tableau (qui remplace l'ancien CSV)
        String[][] nouveauContenu = new String[nbLignesExistantes + 1][5];
        //Recopier les anciens joueurs
        for (int i = 0; i < nbLignesExistantes; i++) {
            nouveauContenu[i][0] = getCell(f, i, 0); // Pseudo
            nouveauContenu[i][1] = getCell(f, i, 1); // MDP
            nouveauContenu[i][2] = getCell(f, i, 2); // Score 
            nouveauContenu[i][3] = getCell(f, i, 3); // PV
            nouveauContenu[i][4] = getCell(f, i, 4); // Or
        }
        //Ajouter le nouveau joueur à la toute dernière ligne
        nouveauContenu[nbLignesExistantes][0] = pseudo;
        nouveauContenu[nbLignesExistantes][1] = mdp;
        nouveauContenu[nbLignesExistantes][2] = "0";
        nouveauContenu[nbLignesExistantes][3] = "5";
        nouveauContenu[nbLignesExistantes][4] = "0";
        //Sauvegarder (écrase et remplace par le CSV)
        saveCSV(nouveauContenu, "ressources/joueurs.csv", ';');
        PSEUDO_ACTUEL = pseudo;
        COWBOYPV = 5;
        println("Inscription réussie ! Bienvenue en ville, " + pseudo);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////    -/MARCHAND/-   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void marchand() {
        clear();
        println("----------------------------------------------------------------------------------------------------------------------------------------");
        println("Le marchand vous propose : +1 PV Max définitif");
        println("Prix : 10 Gold");
        println("1. Acheter");
        println("2. Partir");
        println("Gold : " + afficherGold());

        int choix = readInt();

        if (choix == 1) {
            // Charger le fichier CSV
            CSVFile f = loadCSV("ressources/joueurs.csv", ';');
            if (f == null) return;

            int nbLignes = rowCount(f);
            String[][] nouveauContenu = new String[nbLignes][5];
            boolean achatReussi = false;

            //Parcourir pour trouver le bon compte
            for (int i = 0; i < nbLignes; i++) {
                nouveauContenu[i][0] = getCell(f, i, 0); 
                nouveauContenu[i][1] = getCell(f, i, 1);
                nouveauContenu[i][2] = getCell(f, i, 2);

                if (equals(getCell(f, i, 0), PSEUDO_ACTUEL)) {
                    // On récupère les PV et l'or
                    int pvActuels = stringToInt(getCell(f, i, 3));
                    int goldActuel = stringToInt(getCell(f, i, 4));

                    if (goldActuel >= 10) {
                        nouveauContenu[i][3] = "" + (pvActuels + 1); // PV + 1
                        nouveauContenu[i][4] = "" + (goldActuel - 10); // Gold - 10
                        COWBOYPV = pvActuels + 1;
                        achatReussi = true;
                        clear();
                        println("------------------------");
                        println("Achat effectué ! Tes PV Max passent à " + nouveauContenu[i][3]);
                        sleep(2000);
                    } else {
                        println("Pas assez de Gold ! Il te faut 10 pièces.");
                        sleep(2000);
                        clear();
                        marchand();
                        nouveauContenu[i][3] = getCell(f, i, 3);
                        nouveauContenu[i][4] = getCell(f, i, 4);
                    }
                } else {
                    // Pour les autres on recopie tel quel
                    nouveauContenu[i][3] = getCell(f, i, 3);
                    nouveauContenu[i][4] = getCell(f, i, 4);
                }
            }
            //Sauvegarder si l'or a été dépensé
            if (achatReussi) {
                saveCSV(nouveauContenu, "ressources/joueurs.csv", ';');
                println("Autre chose ?");
                marchand();
            }
        }else if(choix == 2){
            println("A la prochaine !");
            sleep(2000);
            clear();
            menu();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////      -/Afficher score/gold/-    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    int afficherScore(){
        CSVFile f = loadCSV("ressources/joueurs.csv", ';');
        int nbLignes = rowCount(f);
        int score = 0;
        for (int i = 0; i < nbLignes; i++) {
            if (equals(getCell(f, i, 0), PSEUDO_ACTUEL)) {
                score = stringToInt(getCell(f,i,2));
            }
        }
        return score;
    }

        int afficherGold(){
        CSVFile f = loadCSV("ressources/joueurs.csv", ';');
        int nbLignes = rowCount(f);
        int score = 0;
        for (int i = 0; i < nbLignes; i++) {
            if (equals(getCell(f, i, 0), PSEUDO_ACTUEL)) {
                score = score + stringToInt(getCell(f,i,4));
            }
        }
        return score;
    }
}