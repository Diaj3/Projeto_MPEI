package Projeto;
import java.util.*;

public class SimilaresTest {
    public static void main(String[] args) {
        int nhf = 200, p = 10001, max = 5;
        double thresh = 0.7;
        double[][] semMinHash;

        Similares sm1 = new Similares(thresh, nhf, p, max);

        //Ler para o mapa
        sm1.readToMapa("C://Users//João Dias//eclipse-workspace//Projeto_mpei//src//Projeto//u.data"); //Caso erro tem a ver com a localização do file
        semMinHash = sm1.calcDistance();
        sm1.similarUsers(semMinHash);
    }
}
