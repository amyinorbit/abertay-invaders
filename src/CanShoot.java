package CesarParent;

public interface CanShoot
{
	/*
	* CanShoot is implemented by invaders that can shoot bullets at the player
	* The time between firings varies between minShootInterval and
	* minShootInterval+shootIntervalVar
	*/
	int minShootInterval = 240;
	int shootIntervalVar = 480;
	public void triggerShoot();
}