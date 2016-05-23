package br.utfpr.gp.tsi.racing.screen;

import java.util.List;

import br.utfpr.gp.tsi.racing.car.ICar;
import br.utfpr.gp.tsi.racing.track.Track;

public interface IScreen {

	void setTrack(Track track);
	void setCarList(List<? extends ICar> carList);
	void update();	

}
