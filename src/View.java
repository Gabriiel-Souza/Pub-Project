import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class View {

	static void exibirMensagem(String msg, String titulo) {
		JOptionPane.showMessageDialog(null, msg, titulo, JOptionPane.INFORMATION_MESSAGE);
	}

	static int exibirMenu(String msg, String[] options) {
		int escolha = JOptionPane.showOptionDialog(null, msg, "Menu", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		return escolha;
	}

	static void exibirErro(String msg, String titulo) {
		JOptionPane.showMessageDialog(null, msg, titulo, JOptionPane.ERROR_MESSAGE);
	}

	static String solicitarString(String msg) {
		String nome = JOptionPane.showInputDialog(msg);
		return nome;
	}

	static int solicitarInt(String msg) {

		String nome = JOptionPane.showInputDialog(msg);
		if (nome != null) {
			int numero = Integer.parseInt(nome);
			return numero;
		}
		throw new NullPointerException();

	}

	static double solicitarDouble(String msg) {
		String nome = JOptionPane.showInputDialog(msg);
		if (nome != null) {
			double numero = Double.parseDouble(nome);
			return numero;
		}
		throw new NullPointerException();

	}

	static int solicitarConfirmacao(String msg) {
		int option = JOptionPane.showConfirmDialog(null, msg, null, JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			return 1;
		} else {
			return 0;
		}
	}

	static Funcionario fazerlogin(Pub pub) {
		boolean achou, terminou;

		Funcionario funcionario = null;

		// Cria o campo de usuário
		JTextField campoUsuario = new JTextField(10);

		// Cria o campo de senha e faz com que os caracteres mostrados sejam
		// substituídos por '•'
		JPasswordField campoSenha = new JPasswordField(10);
		campoSenha.setEchoChar('•');

		// Cria a estrutura da janela
		Object[] campos = { "Usuário: (6 à 20 caracteres)", campoUsuario, "Senha: (6 à 20 caracteres)", campoSenha };

		do {
			terminou = false;
			achou = false;

			int result = JOptionPane.showConfirmDialog(null, campos, "Login", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				String senha = String.valueOf(campoSenha.getPassword());
				funcionario = Utils.procuraFuncionario(pub.funcionarios, campoUsuario.getText());
				if (funcionario != null) {
					if (funcionario.getSenha().equals(senha)) {
						terminou = true;
						achou = true;
					} else {
						achou = false;
						funcionario = null;
					}
				} else {
					achou = false;
				}
				if (!achou) {
					View.exibirErro("Usuário e/ou senha incorretos!", "Erro");
				}
			} else {
				View.exibirErro("Operação cancelada", "");
				throw new NullPointerException();
			}

		} while (!terminou);
		return funcionario;
	}

	static String[] cadastroFuncionario() {

		// Cria o campo de Nome
		JTextField campoNome = new JTextField(10);

		// Cria o campo de usuário
		JTextField campoUsuario = new JTextField(10);

		// Cria o campo de senha e faz com que os caracteres mostrados sejam
		// substituídos por '•'
		JPasswordField campoSenha = new JPasswordField(10);
		campoSenha.setEchoChar('•');

		// Cria a estrutura da janela
		Object[] campos = { "Nome", campoNome, "Usuário: (6 à 20 caracteres)", campoUsuario,
				"Senha: (6 à 20 caracteres)", campoSenha };
		int result = JOptionPane.showConfirmDialog(null, campos, "Cadastro - Funcionário",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String senha = String.valueOf(campoSenha.getPassword());
			String[] infos = { campoNome.getText(), campoUsuario.getText(), senha };
			return infos;
		}
		return null;

	}

	static String[] cadastroCliente(String nomeCliente) {
		int result;

		JTextField campoNome = new JTextField(5);
		JTextField campoCPF = new JTextField(5);
		JTextField campoTelefone = new JTextField(5);
		campoNome.setText(nomeCliente);
		if (nomeCliente.isEmpty()) {
			Object[] campos = { "Nome:", campoNome, "CPF:", campoCPF, "Fone:", campoTelefone };
			result = JOptionPane.showConfirmDialog(null, campos, "Login", JOptionPane.OK_CANCEL_OPTION);
		} else {
			Object[] campos = { "Nome:", nomeCliente, "CPF:", campoCPF, "Fone:", campoTelefone };
			result = JOptionPane.showConfirmDialog(null, campos, "Login", JOptionPane.OK_CANCEL_OPTION);
		}

		if (result == JOptionPane.OK_OPTION) {
			String[] infos = { campoNome.getText(), campoCPF.getText(), campoTelefone.getText() };
			return infos;
		}
		return null;
	}

	static Lanche selecionaLanche(ArrayList<Lanche> lanches) {
		Lanche lancheSelecionado = null;

		String[] nomeLanches = lancheToArray(lanches);
		String item = (String) JOptionPane.showInputDialog(null, "Escolha um item", "Opçao",
				JOptionPane.INFORMATION_MESSAGE, null, nomeLanches, nomeLanches[0]);

		if (item != null) {
			for (Lanche lanche : lanches) {
				if (lanche.getNome().equals(item)) {
					lancheSelecionado = lanche;
					break;
				}
			}
		}
		return lancheSelecionado;

	}

	static String[] lancheToArray(ArrayList<Lanche> lanches) {
		int tam = lanches.size(), i = 0;
		String[] array = new String[tam];

		for (Lanche lanche : lanches) {
			array[i] = lanche.getNome();
			i++;
		}
		return array;
	}

	static Jogo selecionaJogo(ArrayList<Jogo> jogos) {
		Jogo jogoSelecionado = null;
		try {
			String[] nomeJogos = jogoToArray(jogos);
			if (nomeJogos != null) {
				String item = (String) JOptionPane.showInputDialog(null, "Escolha um item", "Opçao",
						JOptionPane.INFORMATION_MESSAGE, null, nomeJogos, nomeJogos[0]); //
				for (Jogo jogo : jogos) {
					if (jogo.getNome().equals(item)) {
						jogoSelecionado = jogo;
						break;
					}
				}
			}
		} catch (NullPointerException e) {
			View.exibirErro("Operação Cancelada!", "");
		}
		return jogoSelecionado;
	}

	static String[] jogoToArray(ArrayList<Jogo> jogos) {
		ArrayList<Jogo> jogosDisponíveis = new ArrayList<Jogo>();

		for (Jogo jogo : jogos) {
			if (jogo.isDisponivel()) {
				jogosDisponíveis.add(jogo);
			}
		}
		int i = 0;
		if (jogosDisponíveis.size() > 0) {
			String[] array = new String[jogosDisponíveis.size()];
			for (Jogo jogo : jogosDisponíveis) {
				array[i] = jogo.getNome();
				i++;
			}
			return array;
		} else {
			return null;
		}
	}
}
