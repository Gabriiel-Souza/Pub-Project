
public class Lanche {
	static int num = 1;
	private int id;
	private String nome;
	private double valor;

	///////////////////////////////////////////////////////////////////////////////////////
	Lanche(String nome, double valor) {
		boolean erro = false;
		try {
			setNome(nome);
		} catch (IllegalArgumentException e) {
			View.exibirErro("Nome de usuário inválido!", "Erro");
			erro = true;
		}

		if (!erro) {
			try {
				setValor(valor);
			} catch (IllegalArgumentException e) {
				View.exibirErro("Valor inserido é inválido!", "Erro");
			}
		}
		if (!erro) {
			setId();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public int getId() {
		return id;
	}

	public void setId() {
		this.id = num;
		num++;
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
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		if (valor > 0) {
			this.valor = valor;
		} else {
			throw new IllegalArgumentException();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	static void fazAcao(Pub pub, int option) {

		switch (option) {

		case 0: // Ver cardápio
			String cardapio = "";
			if (pub.lanches.size() > 0) {
				for (Lanche lanche : pub.lanches) {
					cardapio += lanche.getId() + " - " + lanche.getNome() + " - R$" + lanche.getValor() + "\n";
				}
				View.exibirMensagem(cardapio, "Cardápio");
			} else {
				View.exibirErro("Nenhum lanche cadastrado", "");
			}
			break;

		case 1: // Add Lanche
			Funcionario editFuncionario = null;
			try {
				editFuncionario = View.fazerlogin(pub);
				if (editFuncionario != null) {
					double valor = 0;
					String nome = View.solicitarString("Nome do lanche");
					if (nome != null) {
						valor = View.solicitarDouble("Valor do lanche");
						Lanche lanche = new Lanche(nome, valor);
						pub.lanches.add(lanche);
						View.exibirMensagem("Id do Lanche: " + lanche.getId() + "\n" + "Nome: " + lanche.getNome()
								+ "\n" + "Valor: R$" + lanche.getValor(), "");
					} else {
						View.exibirErro("Operação Cancelada", "");
					}

				}

			} catch (IllegalArgumentException e) {
				View.exibirErro("Dados inseridos inválidos...", "Erro");
			} catch (NullPointerException e) {
			}

			break;

		case 2: // Alterar lanche
			boolean terminarEdit = false;
			Lanche lanche = null;
			Funcionario funcionario = null;
			try {
				funcionario = View.fazerlogin(pub);
				if (funcionario != null) {
					lanche = View.selecionaLanche(pub.lanches);
					if (lanche != null) {
						String[] campos = { "Nome", "Valor", "Voltar" };
						int optionEdit = View.exibirMenu("O que deseja alterar?", campos);
						switch (optionEdit) {
						case 0: // Alterar o nome do lanche
							do {
								try {
									String nome = View.solicitarString("Novo nome do lanche");
									lanche.setNome(nome);
									terminarEdit = true;
								} catch (IllegalArgumentException e) {
									View.exibirErro("Nome inválido", "");
								} catch (NullPointerException e) {
									View.exibirErro("Alteração cancelada", "Cancelado");
									terminarEdit = true;
								}
							} while (!terminarEdit);
							break;
						case 1: // Alterar o valor do lanche
							do {
								try {
									double valor = View.solicitarDouble("Novo valor do lanche");
									lanche.setValor(valor);
									terminarEdit = true;
								} catch (IllegalArgumentException e) {
									View.exibirErro("Valor inválido", "");
								} catch (NullPointerException e) {
									View.exibirErro("Alteração cancelada", "Cancelado");
									terminarEdit = true;
								}
							} while (!terminarEdit);
						}
					}
				}

			} catch (IndexOutOfBoundsException e) {
				View.exibirErro("Nenhum lanche cadastrado", "");
			} catch (NullPointerException e) {

			}
			break;
		case 3: // Remover lanche
			try {
				Lanche procuraLanche = View.selecionaLanche(pub.lanches);
				int optionExclusao = View
						.solicitarConfirmacao("Deseja realmente excluir " + procuraLanche.getNome() + "?");
				if (optionExclusao == 1) {
					pub.lanches.remove(procuraLanche);
				} else {
					View.exibirErro("Exclusão cancelada", "");
				}
			} catch (NullPointerException e) {
				View.exibirErro("Exclusão cancelada", "");
			} catch (IndexOutOfBoundsException e) {
				View.exibirErro("Nenhum lanche cadastrado", "");
			}
		}
	}
}
