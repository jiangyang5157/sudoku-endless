package com.gmail.jiangyang5157.sudoku.puzzle.render;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class State {

	public static final int NULL = -1;

	private int id = NULL;
	private int backgroundColor = NULL;
	private int borderWidth = NULL;
	private int borderColor = NULL;

	public abstract void update(Component component);
	public abstract void render(Component component, Canvas canvas, Paint paint);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int color) {
		this.backgroundColor = color;
	}
	
	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	public int getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
	}
}
