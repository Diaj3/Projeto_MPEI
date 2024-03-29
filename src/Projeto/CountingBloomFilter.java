package Projeto;
import java.util.*;
import java.io.*;

public class CountingBloomFilter {
    private int numElementos, sizeArr, hashFunctions;
    private double fatorC; //Fator carga
    private String sortudo;
    
    public CountingBloomFilter(double fatorC, int numElementos){
        this.fatorC = fatorC;
        this.numElementos = numElementos;
        this.sizeArr = (int) Math.round( (double)numElementos/fatorC) ;
        this.hashFunctions = (int) Math.round((sizeArr / numElementos) * Math.log(2));
        this.sortudo = sortudo;
    }
    
    //Inicialize BloomFilter
    public int[] inicialize () {
    	int[] bloomfilter = new int[this.sizeArr];
    	return bloomfilter;
    }
    
    //String2Hash
    public  int string2hash(String str) {
		int hashvalue = str.hashCode();
		return hashvalue;
	}
    
    //Inserir elemento no CountingBloomFilter
    public int[] insert(int[] bloomfilter, String elem) {
    	int m = bloomfilter.length;
		for (int i = 0; i < this.hashFunctions; i++) {
			String elem2 = Integer.toString(i);
			String elem3 = elem + elem2;
			int hashvalue = string2hash(elem3);
			int h = Math.abs((hashvalue % m));
			bloomfilter[h] += 1; //Vai incrementar de modo a ficar o count no BloomFilter
		}
		return bloomfilter;
    }
    
    //Eliminar 1 ocorr�ncia do elemento do CountingBloomFilter
    public int[] deleteElem(int[] bloomfilter, String elem) {
    	int m = bloomfilter.length;
    	for (int i = 0; i < this.hashFunctions; i++) {
			String elem2 = Integer.toString(i);
			String elem3 = elem + elem2;
			int hashvalue = string2hash(elem3);
			int h = Math.abs(hashvalue % m);
			if (bloomfilter[h] > 0) { //Caso seja <0 n�o podemos decrementar
				bloomfilter[h] -= 1; //Vai decrementar			
			}
			else {
				System.out.print("O elemento n�o pertence ao BloomFilter");
			}
		}
    	return bloomfilter;
    }
    
  //Eliminar todos os elementos iguais do CountingBloomFilter
    public int[] deleteAll(int[] bloomfilter, String elem) {
    	int m = bloomfilter.length;
    	for (int i = 0; i < this.hashFunctions; i++) {
			String elem2 = Integer.toString(i);
			String elem3 = elem + elem2;
			int hashvalue = string2hash(elem3);
			int h = Math.abs(hashvalue % m);
			if (bloomfilter[h] > 0) { 
				bloomfilter[h] = 0; //O n� de ocorr�ncias do elemento vai passar a ser 0			
			}
			else {
				System.out.println("O elemento n�o pertence ao BloomFilter");
			}
		}
    	return bloomfilter;
    }
    
    //Verificar se o BloomFilter cont�m ou n�o o elemento e quantas vezes este j� foi inserido
    public int contains(int[] bloomfilter, String elem) {
    	int m = bloomfilter.length;
    	int contains = 1;
    	int [] a = new int[this.hashFunctions];
    	
    	for (int i = 0; i < this.hashFunctions; i++) {
			String elem2 = Integer.toString(i);
			String elem3 = elem + elem2;
			int hashvalue = string2hash(elem3);
			int h = Math.abs(hashvalue % m);
			a[i] = bloomfilter[h]; //Vai incrementar de modo a ficar o count no BloomFilter
		}

		int min = a[0];

    	//Determinar o menor valor do array
    	for (int j = 0; j < a.length; j++) {
    		if(a[j] < min) {
    			min = a[j];
    		}
    	}
    	contains = min; //if contains = 0  -> o elem n�o pertence ao BloomFilter 
    	
    	return contains;
	}

	public int[] startBloom(String filename) {
		//Inicializar
		int[] B = inicialize();
	
		//Inserir 
        try {
			File file = new File(filename);
			Scanner sc = new Scanner(file);

			while (sc.hasNext()) {
				String palavra = sc.next();
                                palavra = palavra.replaceAll("[$,.!?_]", "");
				B = insert(B, palavra);
				System.out.println(palavra + " foi adicionada ao bloomfilter.");
			}
                        
                    sc.close();
		} catch (Exception e) {
                    System.out.println("Problemas a abrir ficheiro: " + e);
		}
		return B;
	}
	
    //Muda esta funçao aqui para que mostre todas as palavras num texto
    public void checkAll(String filename,int[] B) {
     
    }    
	     
    public void checkFilter(String chosen,int[] B) {
		Scanner sc1 = new Scanner(System.in);
		int contains = contains(B, chosen);
		if (contains > 0) {
			System.out.println(chosen+ " deve pertencer " + contains + " vezes.");
		}

		else{
			System.out.println(chosen + " n�o pertence.");
		}
    }
    
    public void checkSortudo(String filename, int[] B) {
	Scanner sc1 = new Scanner(System.in);
        int maximo=0;
        Map<Integer,String> sortudoMapa = new HashMap<>();
        
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String palavra = sc.nextLine();
                int contains = contains(B, palavra);
                //System.out.println("Palavra= "+palavra+", contains= "+contains+", maximo="+maximo);
		//Com esta cena tens o numero de vezes que aparece, o palava e contains, repete basicamente
                if (contains > maximo) {
                    maximo = contains;
                    sortudoMapa.put(maximo,palavra);
		}
                
            }
            System.out.println("A nome mais prov�vel de ganhar �: " + sortudoMapa.get(maximo));
            
            sc.close();

        } catch (Exception e) {
            System.out.println("Problemas a abrir ficheiro: " + e);
        }
    }

}