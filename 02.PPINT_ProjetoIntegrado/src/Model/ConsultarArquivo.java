package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsultarArquivo {
	private Scanner input;
	private Usuario user;
	private ArrayList<Usuario> users;

	/**
	 * Método construtor
	 */
	public void abrirArquivo() {
		try {
			input = new Scanner(new File("src/login.simetrica"));
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Erro em abrir arquivo.");
			System.exit(1);
		}
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	// = = = = = = = = = = = = =
	/**
	 * Puxa dados armazenados no arquivo externo e armazena em uma lista
	 * 
	 * @throws IOException
	 */
	public void consultarRegistros() throws IOException {
		users = new ArrayList<Usuario>();
		BufferedReader br = new BufferedReader(new FileReader("src/login.simetrica"));
		String registros;
		String[] atributos;
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			registros = sb.toString();
			atributos = registros.split("			");
			user = new Usuario();
			for (int i = 0; i < atributos.length - 1; i++) {
				if ((i % 4) == 0) {
					user.setIdUsuario(Integer.parseInt(atributos[i]));
				} else if ((i % 4) == 1) {
					user.setLogin(atributos[i]);
				} else if ((i % 4) == 2) {
					user.setSenha(atributos[i]);
				} else if ((i % 4) == 3) {
					user.setTipoConta(Integer.parseInt(atributos[i]));
					users.add(user);
					user = new Usuario();
				}
			}
		} finally {
			br.close();
		}
	}

	public void fecharArquivo() {
		if (input != null)
			input.close();
	}

	public Usuario getUsuario() {
		return user;
	}

	public ArrayList<Usuario> getUsuarios() {
		return users;
	}

	/*
	 * public Usuario procurado(ArrayList<Usuario> users, int i) { return
	 * users.get(i); }
	 * 
	 * // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	 * = = // = = = = = = = = = = = = = /** Ordenar lista de usuários
	 * armazenadas no arquivo externo com a arquitetura de ordenagem "QuickSort"
	 * 
	 * @param users
	 */
	public void quickSort(ArrayList<Usuario> users) {
		quickSort(users, 0, users.size() - 1);
	}

	public void quickSort(ArrayList<Usuario> users, int esquerda, int direita) {
		int indice = particao(users, esquerda, direita);
		if (esquerda < indice - 1) {
			quickSort(users, esquerda, indice - 1);
		}
		if (direita > indice) {
			quickSort(users, indice, direita);
		}
	}

	public int particao(ArrayList<Usuario> users, int esquerda, int direita) {
		int i = esquerda;
		int j = direita;
		Usuario aux = new Usuario();
		Usuario pivo = new Usuario();
		pivo = users.get((direita + esquerda) / 2);
		String pivoLogin = pivo.getLogin();
		while (i <= j) {
			while (users.get(i).getLogin().compareTo(pivoLogin) < 0) {
				i++;
			}
			while (users.get(j).getLogin().compareTo(pivoLogin) > 0) {
				j--;
			}
			if (i <= j) {
				aux = users.get(i);
				users.set(i, users.get(j));
				users.set(j, aux);
				i++;
				j--;
			}
		}
		return i;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	// = = = = = = = = = = = = =
	/**
	 * Procurar usuário pela arquitetura de busca binária
	 * 
	 * @param users
	 * @param login
	 * @return
	 */
	public int binary(ArrayList<Usuario> users, String login) {
		int inicio = 0;
		int fim = users.size() - 1;
		while (inicio <= fim) {
			int meio = ((inicio + fim) / 2);
			if (users.get(meio).getLogin().compareTo(login) == 0) {
				return meio;
			} else if (users.get(meio).getLogin().compareTo(login) > 0) {
				fim = meio - 1;
			} else {
				inicio = meio + 1;
			}
		}
		return -1;
	}
}