package br.utfpr.gp.tsi.racing.car;

import org.json.JSONObject;

public class JsParameters {
	private static final JsParameters SINGLETON = new JsParameters();
	private int centro;
	private int velocidade;
	private int curvaDistancia;
	private int curvaDirecao;
	
	private JsParameters() {}

	public static JsParameters reuseSingleton(
			int center,
			int speed,
			int curveDistance,
			int curveSide) {
		
		SINGLETON.centro = center;
		SINGLETON.velocidade = speed;
		SINGLETON.curvaDistancia = curveDistance;
		SINGLETON.curvaDirecao = curveSide;
		return SINGLETON;
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
