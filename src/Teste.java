
public class Teste {
	
	/*	Essa classe � para ajudar o usu�rio a testar o programa com mais facilidade, n�o � uma classe que ser� usada num projeto real.
	 *  Ser�o criadas inst�ncias de todas as classes existentes no projeto.
	 */
	
	static void pegaDados (Pub pub) {
		
		// Cria Funcion�rios
		Funcionario funcionario; 
		funcionario = new Funcionario("Michas", "michaslindo", "meda10porfavor", pub);
		pub.funcionarios.add(funcionario);
		funcionario = new Funcionario("Gabriel", "gabriel", "queronota", pub);
		pub.funcionarios.add(funcionario);
		funcionario = new Funcionario("Thiago", "thiaguinho123", "321thiaguinho", pub);
		pub.funcionarios.add(funcionario);
		funcionario = new Funcionario("Lucas", "luscas999", "325arg32", pub);
		pub.funcionarios.add(funcionario);
		
		// Cria Lanches
		Lanche lanche;
		lanche = new Lanche("X-Burg�o", 6.99);
		pub.lanches.add(lanche);
		lanche = new Lanche("X-Salada", 5.99);
		pub.lanches.add(lanche);
		lanche = new Lanche("X-Gigante", 8.99);
		pub.lanches.add(lanche);
		lanche = new Lanche("Batata Frita P", 6);
		pub.lanches.add(lanche);
		lanche = new Lanche("Batata Frita M", 8);
		pub.lanches.add(lanche);
		lanche = new Lanche("Batata Frita G", 10);
		pub.lanches.add(lanche);
		lanche = new Lanche("Milk-Shake", 6.99);
		pub.lanches.add(lanche);
		
		//Cria Jogos
		Jogo jogo;
		jogo = new Jogo("Monopoly");
		pub.jogos.add(jogo);
		jogo = new Jogo("Banco Imobili�rio");
		pub.jogos.add(jogo);
		jogo = new Jogo("Detetive");
		pub.jogos.add(jogo);
		jogo = new Jogo("7 Wonders");
		pub.jogos.add(jogo);
		jogo = new Jogo("Dixit");
		pub.jogos.add(jogo);
		jogo = new Jogo("The Mind");
		pub.jogos.add(jogo);
		jogo = new Jogo("War");
		pub.jogos.add(jogo);
		
		
	}
}
