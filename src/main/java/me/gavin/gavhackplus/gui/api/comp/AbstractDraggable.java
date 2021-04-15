package me.gavin.gavhackplus.gui.api.comp;

@Deprecated
public abstract class AbstractDraggable extends AbstractClickable {
	
	public int dragX, dragY;
	public boolean dragging;

	public AbstractDraggable(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (isMouseWithin(mouseX, mouseY, x, y, width, height)) {
			dragging = true;
			dragX = mouseX - x;
			dragY = mouseY - y;
		}
		
		processClick(mouseX, mouseY, mouseButton);
	}
	
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0)
			dragging = false;
	}
	
	private void updatePosition(int mouseX, int mouseY) {
		if (dragging) {
			x = (mouseX - dragX);
			y = (mouseY - dragY);
		}
	}
	
	public void update(int mouseX, int mouseY) {
		updatePosition(mouseX, mouseY);
		render(mouseX, mouseY);
	}
	
	@Override
	public abstract void processClick(int mouseX, int mouseY, int mouseButton);
	
	@Override
	public abstract void render(int mouseX, int mouseY);
}
