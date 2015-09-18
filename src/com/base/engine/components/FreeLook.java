package com.base.engine.components;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import com.base.engine.core.Input;
import com.base.engine.core.Vector;
import com.base.engine.rendering.Window;

public class FreeLook extends GameComponent
{
	private static final Vector yAxis = new Vector(0,1,0);

	private boolean mouseLocked = false;
	private float sensitivity;
	private int unlockMouseKey;
	private Window window;

	public FreeLook(float sensitivity, Window window)
	{
		this(sensitivity, window, GLFW_KEY_ESCAPE);
	}

	public FreeLook(float sensitivity, Window window, int unlockMouseKey)
	{
		this.sensitivity = sensitivity;
		this.unlockMouseKey = unlockMouseKey;
		this.window = window;
	}

	@Override
	public void input(float delta)
	{
		Vector centerPosition = new Vector(window.getWidth()/2, window.getHeight()/2);

		if(Input.getKey(unlockMouseKey))
		{
			Input.setCursor(true);
			mouseLocked = false;
		}
		if(Input.getMouse(0))
		{
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}

		if(mouseLocked)
		{
			Vector deltaPos = Input.getMousePosition().sub(centerPosition);

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if(rotY)
				getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
			if(rotX)
				getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(-deltaPos.getY() * sensitivity));

			if(rotY || rotX)
				Input.setMousePosition(centerPosition);
		}
	}

}
