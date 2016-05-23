package br.utfpr.gp.tsi.racing.car;

import org.json.JSONObject;

public class JsParameters {
	public static final JsParameters INSTANCE = new JsParameters();
	private int centro;
	private int velocidade;
	private int curvaDistancia;
	private int curvaDirecao;
	
	private JsParameters() {}

	public void update(int center,
			int speed,
			int curveDistance,
			int curveSide) {
		
		this.centro = center;
		this.velocidade = speed;
		this.curvaDistancia = curveDistance;
		this.curvaDirecao = curveSide;
		
	}

	public String generateJson() {
		return new JSONObject(this).toString();
	}

	public int getCentro() {
		return centro;
	}

	public void setCentro(int centro) {
		this.centro = centro;
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public int getCurvaDistancia() {
		return curvaDistancia;
	}

	public void setCurvaDistancia(int curvaDistancia) {
		this.curvaDistancia = curvaDistancia;
	}

	public String getCurvaDirecao() {
		return curvaDirecao == -1 ? "esquerda" : "direita";
	}
	public void setCurvaDirecao(String curvaDirecao) {}
	
}
