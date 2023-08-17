package com.foxminded.bohdansharubin;

import java.util.Scanner;

public class TaskEntryPoint {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		scanner.close();
		Anagram anagram = new Anagram(input);
		anagram.makeAnagram();
		System.out.println(anagram);
	}
}
