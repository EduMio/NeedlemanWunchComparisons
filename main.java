import java.util.HashMap;

class main {
	public static void main(String[] args) {
		if (args.length != 2) {
			throw new RuntimeException("Insira as duas sequencias para serem alinhadas");
		}

		String v = args[0];
		String w = args[1];

		int[][] matrizBlosum62 = gerarMatrizBlosum62();

		HashMap<Character, Integer> dicionarioIndiceAlfabetoAllAmino = gerarDicionario();

		main(matrizBlosum62, dicionarioIndiceAlfabetoAllAmino, 4, v, w);
	}

	private static int[][] gerarMatrizBlosum62() {
		int[][] blosum62 = {
				{ 4, -1, -2, -2, 0, -1, -1, 0, -2, -1, -1, -1, -1, -2, -1, 1, 0, -3, -2, 0 },
				{ -1, 5, 0, -2, -3, 1, 0, -2, 0, -3, -2, 2, -1, -3, -2, -1, -1, -3, -2, -3 },
				{ -2, 0, 6, 1, -3, 0, 0, 0, 1, -3, -4, 0, -2, -3, -2, 1, 0, -4, -2, -3 },
				{ -2, -2, 1, 6, -3, 0, 2, -1, -1, -3, -4, -1, -3, -3, -1, 0, -1, -4, -3, -3 },
				{ 0, -3, -3, -3, 9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1, -2, -2, -1 },
				{ -1, 1, 0, 0, -3, 5, 2, -2, 0, -3, -2, 1, 0, -3, -1, 0, -1, -2, -1, -2 },
				{ -1, 0, 0, 2, -4, 2, 5, -2, 0, -3, -3, 1, -2, -3, -1, 0, -1, -3, -2, -2 },
				{ 0, -2, 0, -1, -3, -2, -2, 6, -2, -4, -4, -2, -3, -3, -2, 0, -2, -2, -3, -3 },
				{ -2, 0, 1, -1, -3, 0, 0, -2, 8, -3, -3, -1, -2, -1, -2, -1, -2, -2, 2, -3 },
				{ -1, -3, -3, -3, -1, -3, -3, -4, -3, 4, 2, -3, 1, 0, -3, -2, -1, -3, -1, 3 },
				{ -1, -2, -4, -4, -1, -2, -3, -4, -3, 2, 4, -2, 2, 0, -3, -2, -1, -2, -1, 1 },
				{ -1, 2, 0, -1, -3, 1, 1, -2, -1, -3, -2, 5, -1, -3, -1, 0, -1, -3, -2, -2 },
				{ -1, -1, -2, -3, -1, 0, -2, -3, -2, 1, 2, -1, 5, 0, -2, -1, -1, -1, -1, 1 },
				{ -2, -3, -3, -3, -2, -3, -3, -3, -1, 0, 0, -3, 0, 6, -4, -2, -2, 1, 3, -1 },
				{ -1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4, 7, -1, -1, -4, -3, -2 },
				{ 1, -1, 1, 0, -1, 0, 0, 0, -1, -2, -2, 0, -1, -2, -1, 4, 1, -3, -2, -2 },
				{ 0, -1, 0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1, 1, 5, -2, -2, 0 },
				{ -3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1, 1, -4, -3, -2, 11, 2, -3 },
				{ -2, -2, -2, -3, -2, -1, -2, -3, 2, -1, -1, -2, -1, 3, -3, -2, -2, 2, 7, -1 },
				{ 0, -3, -3, -3, -1, -2, -2, -3, -3, 3, 1, -2, 1, -1, -2, -2, 0, -3, -1, 4 }
		};

		return blosum62;
	}

	private static HashMap<Character, Integer> gerarDicionario() {
		HashMap<Character, Integer> dicionario = new HashMap<>();
		dicionario.put('A', 0);
		dicionario.put('R', 1);
		dicionario.put('N', 2);
		dicionario.put('D', 3);
		dicionario.put('C', 4);
		dicionario.put('Q', 5);
		dicionario.put('E', 6);
		dicionario.put('G', 7);
		dicionario.put('H', 8);
		dicionario.put('I', 9);
		dicionario.put('L', 10);
		dicionario.put('K', 11);
		dicionario.put('M', 12);
		dicionario.put('F', 13);
		dicionario.put('P', 14);
		dicionario.put('S', 15);
		dicionario.put('T', 16);
		dicionario.put('W', 17);
		dicionario.put('Y', 18);
		dicionario.put('V', 19);

		return dicionario;
	}

	private static void main(int[][] matrizPontuacao, HashMap<Character, Integer> dicionarioIndiceAlfabeto,
			int penalidadeIndel, String v, String w) {
		int lenV = v.length();
		int lenW = w.length();

		int[][] matrizPosicionamento = new int[lenV + 1][lenW + 1];
		for (int j = 0; j < lenW + 1; j++) {
			matrizPosicionamento[0][j] = 1;
		}

		int[][] matrizSimilaridade = new int[lenV + 1][lenW + 1];

		for (int i = 1; i < lenV + 1; i++) {
			for (int j = 1; j < lenW + 1; j++) {

				int valorInsercaoV = matrizSimilaridade[i - 1][j] - penalidadeIndel;

				int valorInsercaoW = matrizSimilaridade[i][j - 1] - penalidadeIndel;

				int valorMatch = matrizSimilaridade[i - 1][j - 1]
						+ matrizPontuacao[dicionarioIndiceAlfabeto.get(v.charAt(i - 1))][dicionarioIndiceAlfabeto
								.get(w.charAt(j - 1))];

				matrizSimilaridade[i][j] = Math.max(Math.max(valorInsercaoV, valorInsercaoW), valorMatch);

				if (matrizSimilaridade[i][j] == valorMatch) {
					matrizPosicionamento[i][j] = 2;
				} else if (matrizSimilaridade[i][j] == valorInsercaoW) {
					matrizPosicionamento[i][j] = 1;
				} else if (matrizSimilaridade[i][j] == valorInsercaoV) {
					matrizPosicionamento[i][j] = 0;
				}
			}
		}

		//System.out.println("Maior Valor: " + matrizSimilaridade[lenV][lenW]);
	}
}