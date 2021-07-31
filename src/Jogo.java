
public class Jogo {
	static int num = 1;
	private int id;
	private String nome;
	private boolean disponivel = true;
	private Cliente cliente;

	///////////////////////////////////////////////////////////////////////////////////////
	Jogo(String nome) {
		setId();
		setNome(nome);
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
	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		if (cliente != null) {
			this.cliente = cliente;
		} else {
			throw new IllegalArgumentException("Cliente inválido");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////

	static void fazAcao(Pub pub, int option) {

		switch (option) {
		case 0: // Ver jogos
			if (pub.jogos.size() > 0) {
				String listaJogos = "";
				for (Jogo jogo : pub.jogos) {
					listaJogos += jogo.getId() + " - " + jogo.getNome() + " - ";
					if (jogo.isDisponivel()) {
						listaJogos += "Disponível\n";
					} else {
						listaJogos += "Indisponível (" + jogo.getCliente().getNome() + ")\n";
					}
				}
				View.exibirMensagem(listaJogos, "Jogos");
			} else {
				View.exibirErro("Nenhum jogo cadastrado", "");
			}

			break;

		case 1: // Add Jogo
			boolean existe = false;
			try {
				Funcionario funcionario = View.fazerlogin(pub);
				if (funcionario != null) {
					String nome = View.solicitarString("Nome do jogo");
					if (nome != null) {
						for (Jogo jogoInfo : pub.jogos) {
							if (jogoInfo.getNome().equals(nome)) {
								existe = true;
								break;
							}
						}
						if (!existe) {
							Jogo jogo = new Jogo(nome);
							pub.jogos.add(jogo);
						} else {
							View.exibirErro("Esse jogo já existe!", "");
						}
					} else {
						View.exibirErro("Operação cancelada", "");
					}
				}
			} catch (IllegalArgumentException e) {
				View.exibirErro("Dados inseridos inválidos...", "Erro");
			} catch (NullPointerException e) {
			}
			break;

		case 2: // Alterar Jogo
			boolean terminarEdit = false;
			Jogo jogo = null;

			do {
				try {
					Funcionario funcionario = View.fazerlogin(pub);
					if (funcionario != null) {
						jogo = View.selecionaJogo(pub.jogos);
						String nome = View.solicitarString("Novo nome do jogo");
						jogo.setNome(nome);
						terminarEdit = true;
					}
				} catch (IllegalArgumentException e) {
					View.exibirErro("Nome inválido", "");
				} catch (NullPointerException e) {
					View.exibirErro("Alteração cancelada", "Cancelado");
					terminarEdit = true;
				} catch (IndexOutOfBoundsException e) {
					View.exibirErro("Nenhum jogo cadastrado", "");
					terminarEdit = true;
				}
			} while (!terminarEdit);

		case 3: // Remover jogo
			try {
				Funcionario funcionario = View.fazerlogin(pub);
				if (funcionario != null) {
					Jogo procuraJogo = View.selecionaJogo(pub.jogos);
					int optionExclusao = View
							.solicitarConfirmacao("Deseja realmente excluir " + procuraJogo.getNome() + "?");
					if (optionExclusao == 1) {
						pub.jogos.remove(procuraJogo);
					} else {
						View.exibirErro("Exclusão cancelada", "");
					}
				}
			} catch (NullPointerException e) {
				View.exibirErro("Exclusão cancelada", "");
			} catch (IndexOutOfBoundsException e) {
				View.exibirErro("Nenhum jogo cadastrado", "");
			}
		}
	}
}
