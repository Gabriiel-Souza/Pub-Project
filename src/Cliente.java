
public class Cliente {
	static int erro;
	private String nome;
	private String cpf;
	private String telefone;

	///////////////////////////////////////////////////////////////////////////////////////
	Cliente(String nome) {
		setNome(nome);
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
			throw new IllegalArgumentException("Nome vazio ou cont�m n�meros!");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if (cpf.isEmpty()) {
			return;
		} else if (cpf.matches("[0-9]+") && cpf.length() == 11) {
			this.cpf = cpf;
		} else {
			erro = 2;
			throw new IllegalArgumentException("CPF Cont�m letras ou possui quantidade de n�meros errado!");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		if (telefone.isEmpty()) {
			return;
		} else if (telefone.matches("[0-9]+") && cpf.length() >= 10) {
			this.telefone = telefone;
		} else {
			erro = 4;
			throw new IllegalArgumentException("Telefone Cont�m letras ou possui menos de 10 caracteres!");
		}
	}
}
