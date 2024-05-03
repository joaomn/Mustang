package br.com.mustang.services;

import java.util.Random;

public class UtilsService {
	
	public static String generateRandomToken() {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            token.append(characters.charAt(index));
        }
        
        return token.toString().toUpperCase();
    }
	
	
	 public static String generateRandomName() {
	        String[] nomes = {"João", "Maria", "José", "Ana", "Pedro", "Luiza", "Carlos", "Mariana", "Paulo", "Fernanda"};
	        Random random = new Random();
	        int index = random.nextInt(nomes.length);
	        String nome = nomes[index];
	        
	        String[] sobrenomes = {"Silva", "Santos", "Oliveira"
	        		, "Pereira", "Almeida", "Ribeiro", "Rodrigues", "Costa", "Carvalho", "Martins"};
	        int indexS = random.nextInt(sobrenomes.length);
	        String sobrenome = sobrenomes[indexS];
	        
	        return (nome + " " + sobrenome);
	    }

}
