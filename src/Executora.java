
public class Executora {

	public static void main(String[] args) {
		Pub pub = null;
		boolean cancelar;
		int optionMenuPrincipal, optionSubMenu = 0;

		do {
			cancelar = false;
			try {
				pub = Utils.criarPub();
			} catch (NullPointerException e) {
				cancelar = true;
			}
		} while (pub == null && !cancelar);
		if (pub != null) {

			Teste.pegaDados(pub);
			// Inicializa as vari�veis que mostrar�o os menus
			String[] menuOptions = { "Pedido", "Pub", "Lanches", "Jogos", "Sair" };
			String[] menuPedidoOptions = { "Novo Pedido", "Devolver Jogo", "+Jogo", "+Lanche", "Ver pedido", "Pagar",
					"Voltar" };
			String[] menuPubOptions = { "Add Funcionario", "Editar Funcionarios", "Alterar Nome", "Excluir Funcion�rio",
					"Voltar" };
			String[] menuLancheOptions = { "Card�pio", "Add Lanche", "Alterar Lanche", "Excluir Lanche", "Voltar" };
			String[] menuJogoOptions = { "Ver Jogos", "Add Jogo", "Alterar Jogo", "Excluir Jogo", "Voltar" };
			do {
				// Mostra o Menu Principal
				optionMenuPrincipal = View.exibirMenu("Selecione uma op��o", menuOptions);

				// Mostra o Sub-menu Selecionado
				switch (optionMenuPrincipal) {
				case 0:
					optionSubMenu = View.exibirMenu("Selecione uma op��o", menuPedidoOptions);
					Pedido.fazAcao(pub, optionSubMenu);
					break;

				case 1:
					optionSubMenu = View.exibirMenu("Selecione uma op��o", menuPubOptions);
					Pub.fazAcao(pub, optionSubMenu);
					break;

				case 2:
					optionSubMenu = View.exibirMenu("Selecione uma op��o", menuLancheOptions);
					Lanche.fazAcao(pub, optionSubMenu);
					break;

				case 3:
					optionSubMenu = View.exibirMenu("Selecione uma op��o", menuJogoOptions);
					Jogo.fazAcao(pub, optionSubMenu);
				}

			} while (optionMenuPrincipal != 4); // Permanece no loop enquanto a op��o != Sair
		} else {
			View.exibirErro("Pub n�o criado...", "");
		}

	}

}
