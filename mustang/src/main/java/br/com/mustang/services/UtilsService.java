package br.com.mustang.services;

import java.util.Random;

public class UtilsService {
	
	public static String generateRandomToken() {
        String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            token.append(characters.charAt(index));
        }
        
        return token.toString().toUpperCase();
    }
	
	
	 public static String generateRandomName() {
	        String[] nomes = {"Albert", "Marie", "Isaac", "Rosalind", "Galileo", "Ada", "Charles", "Jane", "Stephen", "Rita"};
	        Random random = new Random();
	        int index = random.nextInt(nomes.length);
	        String nome = nomes[index];
	        
	        String[] sobrenomes = {"Einstein", "Curie", "Newton", "Franklin", "Galilei", "Lovelace", "Darwin", "Goodall", "Hawking", "Levi-Montalcini"};
	        int indexS = random.nextInt(sobrenomes.length);
	        String sobrenome = sobrenomes[indexS];
	        
	        return (nome + " " + sobrenome);
	    }

}
