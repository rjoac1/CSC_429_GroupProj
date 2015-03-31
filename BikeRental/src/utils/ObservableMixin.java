package utils;

import java.util.ArrayList;

public class ObservableMixin {

	public ArrayList<Observer> mObs = new ArrayList<>();

	public void addObserver(final Observer ob) {
		mObs.add(ob);
	}

	public void removeObserver(final Observer ob) {
		mObs.remove(ob);
	}

	public void updateAll(final Observable ob, final Object datas) {
		mObs.stream().forEach(
				e -> e.update(ob, datas)
				);
	}

}
