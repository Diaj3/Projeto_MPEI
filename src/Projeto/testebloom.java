package Projeto;
import java.util.*;
import java.io.*;

public class testebloom {
	public static void main(String[] args) {
		
		CountingBloomFilter myFilter = new CountingBloomFilter(0.2, 3000); //Não tenho a certeza do valores a inserir como argumento
		
		//Inicializar
		System.out.println("INICIALIZANDO O BLOOMFILTER");
		int[] B = myFilter.inicialize();
		Scanner sc = new Scanner(System.in);
		
		try {
			//FileReader file = new FileReader("TextFiles/teste.txt");
			FileReader file = new FileReader("C://Users//João Dias//eclipse-workspace//Projeto_mpei//src//Projeto//pg21209.txt");
			BufferedReader reader = new BufferedReader(file);

			String text = "";
			String line = reader.readLine();
			while(line != null) {
				text += line;
				line = reader.readLine();
			}
			
			String text2 = text.replaceAll("[$,.!?_]", "");
			String[] words = text2.trim().split("\\s+"); //Este words ainda contém palavras iguais
			
			//Inserir no BloomFilter
			for(int i = 0; i < words.length; i++) {
				B = myFilter.insert(B, words[i]);
				System.out.println(words[i] + " foi adicionada ao bloomfilter.");
			}
			reader.close();
			
			//Número de ocorrências
			System.out.println("Deseja ver o número de ocorrências de uma palavra? \n(1)Sim \n(2)Não ");
			int answer = sc.nextInt();
			if (answer == 1) {
				for(int i = 0; i < words.length; i++) {
					int count = myFilter.contains(B, words[i]);
					System.out.println("A palavra " + words[i] + " aparece " + count + " vezes.");
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Erro.");
		}
		
		//Testar delete functions
		int input = 0;
		while(input!=1 && input!=2) {
			System.out.println("Deseja remover alguma palavra do BloomFilter? \n(1)Sim \n(2)Não ");
			input = sc.nextInt();
			sc.nextLine();
		}
		if (input == 1) {
			int op = 0;
			while(op!=1 && op!=2) {
				System.out.println("Deseja remover a palavra totalmente ou apenas uma ocorrências? \n(1)Totalmente \n(2)Ocorrência ");
				op = sc.nextInt();
				sc.nextLine();
			}
			if (op == 1) {
				System.out.print("Insira a palavra que deseja remover: ");
				String deleted_word = sc.nextLine();
				B = myFilter.deleteAll(B, deleted_word);
				System.out.println(deleted_word + " foi removido do BloomFilter com sucesso!");
			}
			else {
				System.out.print("Insira a palavra que deseja remover: ");
				String ocorrencia = sc.nextLine();
				B = myFilter.deleteElem(B, ocorrencia);
				System.out.println("Foi removida uma ocorrência de " + ocorrencia + " com sucesso!");
			}
		}
		
		//Verificar se pertence e, se sim, quantas vezes
		System.out.println("Verifique a presença de uma palavra.");
		System.out.print("Insira uma palavra: ");
		String word = sc.nextLine();
		
		int contains = myFilter.contains(B, word);
		if (contains == 0) {
			System.out.println(word + " não pertence.");
		} 
		else {
			System.out.println(word + " deve pertencer " + contains + " vezes.");
		}
		
		sc.close();
	}
}
