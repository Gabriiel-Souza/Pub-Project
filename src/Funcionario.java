import java.util.Random;

public class Funcionario {
	static int erro;
	private String nome;
	private String nomeUsuario;
	private String senha;
	private String id;

	///////////////////////////////////////////////////////////////////////////////////////
	Funcionario(String nome, String nomeUsuario, String senha, Pub pub) {
		setId(pub);
		setNome(nome);
		setNomeUsuario(nomeUsuario);
		setSenha(senha);
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		if (!nomeUsuario.isEmpty()) {
			if (nomeUsuario.length() >= 6 && nomeUsuario.length() <= 20) {
				this.nomeUsuario = nomeUsuario;
			} else {
				erro = 2;
				throw new IllegalArgumentException("Tamanho do nome de usuário inválido!");
			}
		} else {
			erro = 2;
			throw new IllegalArgumentException("Nome de usuário vazio!");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (!senha.isEmpty()) {
			if (senha.length() >= 6 && senha.length() <= 20) {
				this.senha = senha;
			} else {
				erro = 4;
				throw new IllegalArgumentException("Tamanho da senha inválida");
			}
		} else {
			erro = 4;
			throw new IllegalArgumentException("Senha vazia!");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(Pub pub) {
		boolean existe;
		Random rand = new Random();
		int idNum;
		String id;
		do {
			existe = false;
			idNum = rand.nextInt(9999999);
			id = String.valueOf(idNum);
			for (Funcionario funcionario : pub.funcionarios) {
				if (funcionario.getId().equals(id)) {
					existe = true;
					break;
				}
			}
		} while (existe);
		this.id = id;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (!nome.isEmpty() && !nome.matches("[0-9]+")) {
			this.nome = nome;
		} else {
			erro = 1;
			throw new IllegalArgumentException("Nome vazio ou contém números!");
		}
	}
}
