import java.util.ArrayList;

public class Pub {
	private String nome;
	ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
	ArrayList<Lanche> lanches = new ArrayList<Lanche>();
	ArrayList<Jogo> jogos = new ArrayList<Jogo>();
	ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

	///////////////////////////////////////////////////////////////////////////////////////
	Pub(String nome) {
		setNome(nome);
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (!nome.isEmpty()) {
			this.nome = nome;
		} else {
			throw new IllegalArgumentException("Nome vazio!");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	static void fazAcao(Pub pub, int option) {

		switch (option) {
		case 0: // Add Funcionario
			Funcionario novoFuncionario = Utils.criarFuncionario(pub);
			if (novoFuncionario != null) {
				pub.funcionarios.add(novoFuncionario);
				String senha = "";
				for (int i = 0; i < novoFuncionario.getSenha().length(); i++) {
					senha += "*";
				}
				View.exibirMensagem(
						"Id do Funcion�rio: " + novoFuncionario.getId() + "\n" + "Nome: " + novoFuncionario.getNome()
								+ "\n" + "Usu�rio: " + novoFuncionario.getNomeUsuario() + "\n" + "Senha: " + senha,
						"");
			}
			break;

		case 1: // Editar Funcionario
			boolean terminarEdit, cancelar;
			Funcionario editFuncionario = null;

			do {
				cancelar = false;
				try {
					editFuncionario = View.fazerlogin(pub);
					cancelar = true;
				} catch (NullPointerException e) {
					cancelar = true;
				}
				if (!cancelar) {
					if (editFuncionario == null) {
						View.exibirErro("Usu�rio ou senha incorretos...", "");
					}
				}

			} while (!cancelar);

			if (editFuncionario != null) {
				String[] campos = { "Nome", "Usuario", "Senha", "Cancelar" };
				int optionEdit = View.exibirMenu("O que deseja alterar?", campos);
				switch (optionEdit) {
				case 0: // Alterar nome do Funcion�rio
					do {
						terminarEdit = false;
						try {
							String nome = View.solicitarString("Novo nome para " + editFuncionario.getNome());
							if (nome != null) {
								editFuncionario.setNome(nome);
								terminarEdit = true;
							} else {
								View.exibirErro("Altera��o cancelada", "");
								terminarEdit = true;
							}
						} catch (IllegalArgumentException e) {
							View.exibirErro("Nome inserido � inv�lido...", "Erro");
						}
					} while (!terminarEdit);
					break;

				case 1: // Alterar nome de Usu�rio do Funcion�rio
					do {
						terminarEdit = false;
						try {
							String nomeUsuario = View
									.solicitarString("Novo nome de usu�rio para " + editFuncionario.getNomeUsuario());
							if (nomeUsuario != null) {
								editFuncionario.setNomeUsuario(nomeUsuario);
								terminarEdit = true;
							} else {
								View.exibirErro("Altera��o cancelada", "");
								terminarEdit = true;
							}
						} catch (IllegalArgumentException e) {
							View.exibirErro("Nome de usu�rio inv�lido...", "Erro");
						} catch (NullPointerException e) {
							View.exibirErro("Altera��o cancelada", "");
							terminarEdit = true;
						}
					} while (!terminarEdit);
					break;

				case 2: // Alterar senha do funcion�rio
					do {
						terminarEdit = false;
						try {

							String senhaAtual = View.solicitarString("Senha atual");
							if (senhaAtual != null) {
								if (senhaAtual.equals(editFuncionario.getSenha())) {
									String novaSenha = View.solicitarString("Nova senha");
									editFuncionario.setSenha(novaSenha);
									terminarEdit = true;
								} else {
									View.exibirErro("Senha inv�lida", "");
								}
							} else {
								View.exibirErro("Altera��o cancelada", "");
								terminarEdit = true;
							}
						} catch (IllegalArgumentException e) {
							View.exibirErro("A senha deve conter de 6 � 20 caracteres...", "Erro");
						}
					} while (!terminarEdit);
					break;
				case 3: // Remover Funcion�rio
					try {
						View.exibirMensagem(
								"A SEGUIR SER�O SOLICITADOS OS DADOS DO\n FUNCION�RIO A SER DEMITIDO, PRESTE ATEN��O!",
								"!ATEN��O!");
						Funcionario funcionario = View.fazerlogin(pub);
						if (funcionario != null) {
							int optionRemove = View
									.solicitarConfirmacao("Deseja realmente EXCLUIR " + funcionario.getNome() + "?");
							if (optionRemove == 1) {
								pub.funcionarios.remove(funcionario);
							} else {
								View.exibirErro("Opera��o Cancelada", "");
							}
						}
					} catch (NullPointerException e) {
					}
				}
			}
			break;

		case 2: // Alterar nome do pub
			boolean terminarEditPub;
			Funcionario funcionario = null;
			do {
				terminarEditPub = false;
				try {
					funcionario = View.fazerlogin(pub);
					String nome = View.solicitarString("Novo nome para o Pub");
					if (nome != null) {
						if (funcionario != null) {
							pub.setNome(nome);
							terminarEditPub = true;
						} else {
							View.exibirErro("Nome de usu�rio ou senha inv�lido...", "");
						}

					} else {
						View.exibirErro("Altera��o cancelada", "");
						terminarEditPub = true;
					}
				} catch (IllegalArgumentException e) {
					View.exibirErro("Nome inv�lido", "Erro");
				} catch (NullPointerException e) {
					terminarEditPub = true;
				}
			} while (!terminarEditPub);
		}
	}

}
