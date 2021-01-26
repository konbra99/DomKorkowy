package client;

import graphics.Engine;
import map.MapManager;
import org.aspectj.lang.JoinPoint;

public class MapAspect {
	private MapManager map;

	public MapAspect(MapManager map) {
		this.map = map;
	}

	public void afterEntityRemove(JoinPoint joinPoint) {
		Engine.client.removeEntity(MapManager.getCurrentStageNoumber(), (Integer)joinPoint.getArgs()[0]);
	}
}
