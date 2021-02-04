package client;

import graphics.Engine;
import map.MapManager;
import org.aspectj.lang.JoinPoint;

public class MapAspect {

	public MapAspect(MapManager map) {
	}

	public void afterEntityRemove(JoinPoint joinPoint) {
		int[] point = MapManager.getCurrentStageTab();
		Engine.client.removeEntity(point[0], point[1], (Integer) joinPoint.getArgs()[0]);
	}
}
