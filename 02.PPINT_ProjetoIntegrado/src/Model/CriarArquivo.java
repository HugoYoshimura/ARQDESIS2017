package Model;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;


public class CriarArquivo {
	private Formatter output;
	private File file;

	public CriarArquivo(){
		
	}

	
	/**
	 * M�todo que abre arquivo e caso o arquivo n�o exista cria um.
	 */
	public void abrirArquivo() {
		try {
			file = new File("src/login.simetrica");
			if(file.exists()) {
				return;
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException securityException) {
			System.err.println("Voc� n�o tem permiss�o para acessar este arquivo.");
			System.exit(1);
		}
	}
	
	
	/**
	 * M�todo que adiciona usu�rios no arquivo txt.
	 * @param cpf
	 * @param login
	 * @param senha
	 * @param tipoConta
	 * @throws IOException
	 */
	public void adicionarRegistro(Usuario user) throws IOException {
		
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		    //output.format("%s %s %s %d ", cpf, login, tipoConta, senha);
			bufferedWriter.write(user.getIdUsuario() + "			" + user.getLogin() + "			" + user.getSenha() + "			" + user.getTipoConta() + "			");
		    bufferedWriter.close();
		}
		catch (FormatterClosedException formatterClosedException) {
			System.err.println("Erro de inser��o.");
			return;
		}
		catch (NoSuchElementException elementException) {
			System.err.println("Valor(es) inv�lido(s). Tente novamente.");
		}
	}
	
	/**
	 * 
	 * @param idUsuario
	 * @param login
	 * @param senha
	 * @param tipoConta
	 * @throws IOException
	 */
	public void adicionarRegistros(ArrayList<Usuario> users) throws IOException {
		
		try {
			FileWriter fileWriter = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		    //output.format("%s %s %s %d ", cpf, login, tipoConta, senha);
			for(int i = 0; i < users.size(); i++) {
				bufferedWriter.write(users.get(i).getIdUsuario() + "			" + users.get(i).getLogin() + "			" + users.get(i).getSenha() +  "			" + users.get(i).getTipoConta() + "			");
			}
			bufferedWriter.close();
		}
		catch (FormatterClosedException formatterClosedException) {
			System.err.println("Erro de inser��o.");
			return;
		}
		catch (NoSuchElementException elementException) {
			System.err.println("Valor(es) inv�lido(s). Tente novamente.");
		}
	}
	
	/**
	 * M�todo que fecha o arquivo.
	 */
	public void fecharArquivo() {
		if (output != null)
			output.close();
	}
}