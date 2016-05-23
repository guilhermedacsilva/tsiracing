{
	/*
	--------------------------
	-- O ALUNO PODE ALTERAR --

	this.pedal = 'acelerar', 'frear', ''

	this.volante = 'direita', 'esquerda', ''
	
	----------------------
	-- O ALUNO DEVE LER --

	info.velocidade = 0 até 200

	info.curvaDistancia = 0 até 99999

	info.curvaDirecao = 'esquerda', 'direita'

	info.centro = -20 até 20, é a distância até o centro da pista, 
				negativo significa que está à esquerda do centro.
	*/
	pedal: '',
	volante: '',
	
	jogar: function(info) {
		this.pedal = 'acelerar';

		if (info.centro > 0) this.volante = 'esquerda';
		else if (info.centro < 0) this.volante = 'direita';
		else this.volante = '';
	}
}
