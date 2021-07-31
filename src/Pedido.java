import java.util.ArrayList;

public class Pedido {
	static int num = 1;
	private int numPedido;
	private Cliente cliente;
	private Funcionario funcionario;
	private boolean temJogo = false;
	private boolean pago = false;
	private ArrayList<Lanche> lanches = new ArrayList<Lanche>();
	private ArrayList<Jogo> jogos = new ArrayList<Jogo>();

	///////////////////////////////////////////////////////////////////////////////////////
	Pedido(Cliente cliente, Funcionario funcionario) throws IllegalAccessException {
		setNumPedido();
		setCliente(cliente);
		setFuncionario(funcionario);
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public int getNumPedido() {
		return numPedido;
	}

	public void setNumPedido() throws IllegalAccessException {
		if (num == 9999999) {
			num = 1;
		}
		if (this.numPedido != 0) {
			throw new IllegalAccessException();
		}
		this.numPedido = num;
		num++;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		if (funcionario != null) {
			this.funcionario = funcionario;
		} else {
			throw new IllegalArgumentException("Funcionário inválido!");
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Lanche> getLanches() {
		return lanches;
	}

	public void setLanches(Lanche lanche) {
		if (lanche != null) {
			this.lanches.add(lanche);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(Jogo jogo) {
		if (jogo != null) {
			this.jogos.add(jogo);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public boolean getTemJogo() {
		return temJogo;
	}

	public void setTemJogo(boolean temJogo) {
		this.temJogo = temJogo;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	static void fazAcao(Pub pub, int option) {
		int numPedido = 0;
		boolean achei = false;
		// Verifica a opção selecionada
		switch (option) {
		case 0: // Add Pedido
			Pedido pedido = null;
			try {
				pedido = Utils.criarPedido(pub);
			} catch (IllegalAccessException e) {
				View.exibirErro("Não é possível alterar o número do pedido!", "Erro");
			}
			if (pedido != null) {
				pub.pedidos.add(pedido);
			}
			break;

		case 1: // Devolver Jogo
			try {
				numPedido = View.solicitarInt("Número do pedido");
				for (Pedido pedido2 : pub.pedidos) {
					if (pedido2.getNumPedido() == numPedido) {
						achei = true;
						if (pedido2.getTemJogo()) {
							int numJogo = pedido2.jogos.size();
							Jogo jogo = pedido2.jogos.get(numJogo - 1);
							int optionDevolucao = View
									.solicitarConfirmacao("Deseja devolver o Jogo " + jogo.getNome() + "?");
							if (optionDevolucao == 1) {
								pedido2.setTemJogo(false);
								View.exibirMensagem("Devolução feita com sucesso!", "");
							}
						}
					}
				}
				if (!achei) {
					View.exibirErro("Pedido não encontrado", "");
				}
			} catch (NullPointerException e) {
				View.exibirErro("Operação Cancelada", "");
			} catch (NumberFormatException e) {
				View.exibirErro("Número do pedido inválido", "Erro");
			}
			break;

		case 2: // Add Jogo no pedido
			try {
				numPedido = View.solicitarInt("Número do pedido");
				for (Pedido pedido2 : pub.pedidos) {
					if (pedido2.getNumPedido() == numPedido) {
						achei = true;
						if (!pedido2.getTemJogo()) {
							Jogo jogo = View.selecionaJogo(pub.jogos);
							if (jogo != null) {
								pedido2.setTemJogo(true);
								pedido2.setJogos(jogo);
								View.exibirMensagem("Jogo adicionado ao pedido!", "");
							}
						} else {
							View.exibirErro("Esse cliente já possui um jogo emprestado!", "");
						}
					}
				}
				if (!achei) {
					View.exibirErro("Pedido não encontrado", "");
				}
			} catch (NullPointerException e) {
				View.exibirErro("Operação Cancelada", "");
			} catch (NumberFormatException e) {
				View.exibirErro("Número do pedido inválido", "Erro");
			}

			break;

		case 3: // Add Lanche
			try {
				numPedido = View.solicitarInt("Número do pedido");
				for (Pedido pedido2 : pub.pedidos) {
					if (pedido2.getNumPedido() == numPedido) {
						achei = true;
						Lanche lanche = View.selecionaLanche(pub.lanches);
						if (lanche != null) {
							pedido2.setLanches(lanche);
						}
					}
				}
				if (!achei) {
					View.exibirErro("Pedido não encontrado", "");
				}
			} catch (NullPointerException e) {
				View.exibirErro("Operação Cancelada", "");
			} catch (NumberFormatException e) {
				View.exibirErro("Número do pedido inválido", "Erro");
			}
			break;

		case 4: // Ver pedido
			try {
				numPedido = View.solicitarInt("Número do pedido");
				for (Pedido pedido2 : pub.pedidos) {
					if (pedido2.getNumPedido() == numPedido) {
						System.out.println(pedido2);
						achei = true;
					}
				}
				if (!achei) {
					View.exibirErro("Pedido não encontrado", "");
				}
			} catch (NullPointerException e) {
			} catch (NumberFormatException e) {
				View.exibirErro("Número do pedido inválido", "Erro");
			}

			break;
		case 5: // Pagar
			try {
				numPedido = View.solicitarInt("Número do pedido");
				Pedido pedidoProcurado = null;
				for (Pedido pedido2 : pub.pedidos) {
					if (pedido2.getNumPedido() == numPedido) {
						achei = true;
						pedidoProcurado = pedido2;
						break;
					}
				}
				if (achei) {
					if (!pedidoProcurado.isPago()) {
						System.out.println(pedidoProcurado);
						String[] metodoPagamento = { "Dinheiro", "C.Débito", "C.Crédito" };
						View.exibirMenu("Selecione uma Opção", metodoPagamento);
						pedidoProcurado.setPago(true);
					} else {
						View.exibirMensagem("Esse pedido já foi pago!", "");
					}
				} else {
					View.exibirErro("Pedido não encontrado", "");
				}
			} catch (NullPointerException e) {
				View.exibirErro("Pagamento Cancelado", "");
			} catch (NumberFormatException e) {
				View.exibirErro("Número do pedido inválido", "Erro");
			}

		}
	}

	public String toString() {
		double valorTotal = 0;
		String resumoPedido = ("Número do pedido: " + this.getNumPedido() + "\n" + "Cliente: "
				+ this.getCliente().getNome() + "\n" + "Funcionário: " + this.getFuncionario().getNome() + "\n"
				+ "Situação: ");
		if (this.getTemJogo()) {
			int size = jogos.size();
			Jogo jogo = jogos.get(size - 1);
			resumoPedido += "Jogo Pendente - " + jogo.getNome() + "\n";
		} else {
			resumoPedido += "Tudo Certo\n";
		}
		resumoPedido += "Pedidos\n";
		for (Lanche lanche : lanches) {
			resumoPedido += (lanche.getNome() + " - R$" + lanche.getValor() + "\n");
			valorTotal += lanche.getValor();
		}
		resumoPedido += ("Valor total: R$" + valorTotal);
		View.exibirMensagem(resumoPedido, "");
		return "";
	}
}
