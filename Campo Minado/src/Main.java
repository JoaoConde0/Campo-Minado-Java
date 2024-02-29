import java.util.Scanner;
import java.util.Random;
public class Main {
    public static Scanner teclado = new Scanner(System.in);
    public static final String Reset = "\u001B[0m";//resetar 
    public static final String Red = "\u001B[31m";// vermelho
    public static final String Negrit = "\u001B[1m";// negrito
    public static final String Green = "\u001B[32m";//verde
    public static void main(String args[]) {
        long Inicial = System.currentTimeMillis();//calcula o tempo inicial
        System.out.println("Escolha o nível:\n1-Iniciante"+"\n2-Intermediario\n"+"3-Avançado");
        int x, alt=0, larg=0;
        
        x = teclado.nextInt();
        switch(x){
            case 1:
                larg = 9;
                alt = 9;
                x = 10;//x agora signifca a quantidade de bombas
            break;
            case 2:
                larg = 16;
                alt = 16;
                x = 40;//x agora signifca a quantidade de bombas
            break;
            case 3:
                larg = 30;
                alt = 16;
                x = 99;//x agora signifca a quantidade de bombas
            break;
        }
        int[][] Back = new int[larg][alt];
        Back = CriarBack(larg,alt,x);

        CampVisual(Back,larg,alt, x);


        long Final = System.currentTimeMillis();//obtem o tempo quando o jogo acabou
        System.out.println("O jogo durou: "+(((Final-Inicial)/1000))+" Segundos");//converte de milisegundos para segundos


    }
    public static int[][] CriarBack(int larg, int alt, int bombs){
        Random random = new Random();
        int[][] Back = new int[larg][alt];
        for(int i =0; i<larg; i++){
            for(int j = 0; j<alt; j++){
                Back[i][j] = 0;
            }
        }
        for(int i=0; i<bombs; i++){
            int x1 = random.nextInt(larg);
            int x2 = random.nextInt(alt);
            if(Back[x1][x2] != -1){
                Back[x1][x2] = -1;
            }else{
                i--;
            }
        }
        //colocando a quantidade de bomba em cada quadrado
        /*
        "if(Camp[casa1][casa2] != -1)" serve para ver se a casa que vai ser somada a quantidade de numero
        de bomba ao redor n seja outra bomba
         */
        for(int i =0; i<larg; i++){
            for(int j = 0; j<alt; j++){
                if(Back[i][j]!= -1){
                    continue;
                }else{
                    if(j!= 0)//Caso n seja a primeira coluna
                        if(Back[i][j-1] !=-1)
                            Back[i][j-1]++;
                    if(j!= alt-1)//Caso n seja a ultima coluna
                        if(Back[i][j+1] !=-1)
                            Back[i][j+1]++;
                    if(i!= 0){//caso n seja a primeira linha
                        if(Back[i-1][j] !=-1)
                            Back[i-1][j]++;
                        if(j!= 0)
                            if(Back[i-1][j-1] !=-1)
                                Back[i-1][j-1]++;
                        if(j!= alt-1)
                            if(Back[i-1][j+1] !=-1)
                                Back[i-1][j+1]++;
                    }
                    if(i!= larg-1){//caso n seja a ultima linha
                        if(Back[i+1][j] !=-1)
                            Back[i+1][j]++;
                        if(j!= 0)
                            if(Back[i+1][j-1] !=-1)
                                Back[i+1][j-1]++;
                        if(j!= alt-1)
                            if(Back[i+1][j+1] !=-1)
                                Back[i+1][j+1]++;
                    }

                }
            }
            
    }
        return Back;
    }

    public static void CampVisual(int[][] Back, int larg, int alt, int bombs){
        String[][] Visual = new String[larg][alt];
        int cont =0, totalCasas=(larg*alt)-bombs, ver = 0;
        int[] rsp = new int[2];
        for(int i=0; i<larg; i++){//colocar tudo vazio
            for(int j=0; j<alt; j++){
                Visual[i][j] = "-";
            }
        }
        do{//Fazer o loop do jogo
        rsp=Escreva(Visual, ver);
        cont++;
        if(Back[rsp[0]][rsp[1]]!= -1 && cont != totalCasas){//caso o usuario n tenha escolhido uma bomba
            Visual[rsp[0]][rsp[1]] = String.valueOf(Back[rsp[0]][rsp[1]]);
        }else if(Back[rsp[0]][rsp[1]]== -1){//caso tenha escolhido uma bomba
            
            ver = -1;
            for(int i=0; i<larg; i++){//terminar de preencher o campo minado
                for(int j=0; j<alt; j++){
                    if(Visual[i][j] == "-"){
                        if(Back[i][j] != -1){
                        Visual[i][j] =String.valueOf(Back[i][j]);
                        }else{
                            Visual[i][j]= Red+"*"+Reset;// todas as bombas vao ser identificadas por b
                        }
                    }else{
                        continue;
                    }
                }
            }
        }else if(cont == totalCasas ){//caso tenha completado sem escolher uma bomba
            ver = 1;
            for(int i=0; i<larg; i++){//terminar de preencher o campo minado
                for(int j=0; j<alt; j++){
                    if(Visual[i][j] != "-"){
                        continue;
                    }else{
                        Visual[i][j] =Red+"*"+Reset;
                    }
                }
            }
            Visual[rsp[0]][rsp[1]] = String.valueOf(Back[rsp[0]][rsp[1]]);

        }
        }while(ver==0);

        Escreva(Visual, ver);
    }


    public static int[] Escreva(String[][] Camp, int ver){
        Scanner teclado = new Scanner(System.in);
        int[] rsp = new int[2];
        switch(ver){
            case 0:
            System.out.println("O campo está assim:");
            for(int i=0; i<Camp.length; i++){//imprimir campo
                System.out.println();
                for(int j=0; j<Camp[0].length; j++){
                    System.out.print(Camp[i][j]+"\t");
                }
                System.out.println();
            }

            System.out.println("Qual a casa que você quer escolher?");
            rsp[0] = (teclado.nextInt())-1;
            rsp[1] = (teclado.nextInt())-1;
            break;
            case 1:
            System.out.println(Negrit+Green+"PARABENS!!! VOCÊ DESCOBRIU TODAS AS CASAS SEM SER EXPLODIDO"+Reset);
            System.out.println("Confira agora o Campo Completo: ");
            for(int i=0; i<Camp.length; i++){//imprimir campo
                System.out.println();
                for(int j=0; j<Camp[0].length; j++){
                    System.out.print(Camp[i][j]+"\t");
                }
                System.out.println();
            }
            break;
            case -1:
            System.out.println(Negrit+Red+"BOOOM!! VOCÊ ACABA DE SER EXPLODIDO"+Reset);
            System.out.println("Confira agora o Campo Completo: ");
            for(int i=0; i<Camp.length; i++){//imprimir campo
                System.out.println();
                for(int j=0; j<Camp[0].length; j++){
                    System.out.print(Camp[i][j]+"\t");
                }
                System.out.println();
            }
            break;
            
        }
        return rsp;
        
    }
}