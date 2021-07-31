import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Utils {

	static Pub criarPub() {
		try {
			String nome = View.solicitarString("Nome do Pub");
			Pub pub = new Pub(nome);
			return pub;
		} catch (IllegalArgumentException e) {
			View.exibirErro("Nome inserido é inválido, tente novamente...", "Erro");
			return null;
		}
	}

	static Lanche procuraLanche(ArrayList<Lanche> lanches, int id) {
		for (Lanche lanche : lanches) {
			if (lanche.getId() == id) {
				return lanche;
			}
		}
		return null;
	}

	static Funcionario criarFuncionario(Pub pub) {
		Funcionario funcionario = null;
		boolean terminou, acheiFuncionario;
		do {
			terminou = false;
			acheiFuncionario = false;
			try {
				String[] infos = View.cadastroFuncionario(); // Info[0] Nome | Info[1] Nome de Usuário | Info[2] Senha
				for (Funcionario infoFuncionario : pub.funcionarios) {
					if (infoFuncionario.getNomeUsuario().equals(infos[1])) {
						View.exibirErro("Nome de usuário já existe!", "");
						acheiFuncionario = true;
					}
				}
				if (!acheiFuncionario) {
					funcionario = new Funcionario(infos[0], infos[1], infos[2], pub);
					terminou = true;
				}
			} catch (IllegalArgumentException e) {
				switch (Funcionario.erro) {
				case 1:
					View.exibirErro("Nome inserido é inválido...", "Erro");
					break;

				case 2:
					View.exibirErro("Nome de Usuário deve conter de 6 à 20 caracteres", "Erro");
					break;

				case 4:
					View.exibirErro("Senha deve conter de 6 à 20 caracteres", "Erro");
				}
			} catch (NullPointerException e) {
				View.exibirErro("Cadastro Cancelado!", "Cancelado");
				terminou = true;
			}
		} while (!terminou);
		return funcionario;
	}

	static Cliente criarCliente(String nomeCliente) {
		Cliente cliente = null;
		boolean terminou;
		do {
			terminou = false;
			try {
				String[] infos = View.cadastroCliente(nomeCliente); // Info[0] Nome | Info[1] CPF | Info[2] Fone
				cliente = new Cliente(infos[0]);
				cliente.setCpf(infos[1]);
				cliente.setTelefone(infos[2]);
				terminou = true;
			} catch (IllegalArgumentException e) {
				switch (Cliente.erro) {
				case 1:
					View.exibirErro("Nome inserido é inválido...", "Erro");
					break;

				case 2:
					View.exibirErro("CPF inserido é inválido...", "Erro");
					break;

				case 4:
					View.exibirErro("Telefone inserido é inválido...", "Erro");
				}
			} catch (NullPointerException e) {
				View.exibirErro("Cadastro Cancelado!", "Cancelado");
				cliente = null;
				terminou = true;
			}
		} while (!terminou);
		return cliente;
	}

	static Funcionario procuraFuncionario(ArrayList<Funcionario> funcionarios, String nomeUsuario) {
		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getNomeUsuario().equals(nomeUsuario)) {
				return funcionario;
			}
		}
		return null;
	}

	static Cliente procuraCliente(ArrayList<Cliente> clientes, String nome) {
		for (Cliente cliente : clientes) {
			if (cliente.getNome().equals(nome)) {
				return cliente;
			}
		}
		return null;
	}

	static Pedido criarPedido(Pub pub) throws IllegalAccessException {
		Pedido pedido = null;
		String nome = null;
		Cliente cliente = null;
		ArrayList<Lanche> lanches = new ArrayList<Lanche>();
		boolean cancelar = false;
		try {
			nome = View.solicitarString("Nome do Cliente");
			cliente = procuraCliente(pub.clientes, nome);
		} catch (NullPointerException e) {
			cancelar = true;
		}
		if (cliente == null && !cancelar) {
			try {
				cliente = new Cliente(nome);
			} catch (IllegalArgumentException e) {
			} catch (NullPointerException e) {
				View.exibirErro("Pedido Cancelado!", "Cancelado");
				cancelar = true;
			}
			if (!cancelar) {
				cliente = criarCliente(nome);
				if (cliente == null) {
					cancelar = true;
				}
			}
		} else {
			View.exibirMensagem("Já é Cliente do Pub!", "");
		}
		if (cliente != null && !cancelar) {
			try {
				Funcionario funcionario = View.fazerlogin(pub);
				if (funcionario != null) {
					Lanche lanche = null;
					Jogo jogo = null;
					boolean parar;
					cancelar = false;
					do {
						parar = false;
						try {
							lanche = View.selecionaLanche(pub.lanches);
							if (lanche != null) {
								lanches.add(lanche);
							}
							parar = true;
							if (parar && !cancelar) {
								int option = View.solicitarConfirmacao("Add mais itens?");
								if (option == 0) {
									parar = true;
								} else {
									parar = false;
								}
							}
						} catch (IndexOutOfBoundsException e) {
							View.exibirErro("Nenhum lanche cadastrado", "");
							cancelar = true;
							parar = true;
						} catch (NullPointerException e) {

						}

					} while (!parar);

					if (lanches.size() < 1 && !cancelar) {
						View.exibirErro("Pedido cancelado, é preciso pelo menos 1 lanche!", "Operação Cancelada");
						cancelar = true;
					} else if (!cancelar) {
						int option = View.solicitarConfirmacao("Add jogo?");
						if (option == 1) {
							try {
								jogo = View.selecionaJogo(pub.jogos);
							} catch (IndexOutOfBoundsException e) {
								View.exibirErro("Nenhum jogo cadastrado", "");
							} catch (ConcurrentModificationException e) {
								View.exibirErro("Nenhum jogo disponível", "");
							} catch (NullPointerException e) {

							}

						}
					}
					// Cria o pedido
					if (!cancelar) {
						// Add cliente ao Pub
						pub.clientes.add(cliente);
						// Add Funcionario e cliente
						pedido = new Pedido(cliente, funcionario);
						// Add os lanches
						for (Lanche lanche2 : lanches) {
							pedido.setLanches(lanche2);
						}
						// Add o jogo
						if (jogo != null) {
							pedido.setJogos(jogo);
							pedido.setTemJogo(true);
							jogo.setDisponivel(false);
							jogo.setCliente(cliente);
						}
					}
				}
			} catch (NullPointerException e) {
				cancelar = true;
			}

		}
		if (!cancelar) {
			return pedido;
		} else {
			return null;
		}
	}
}
