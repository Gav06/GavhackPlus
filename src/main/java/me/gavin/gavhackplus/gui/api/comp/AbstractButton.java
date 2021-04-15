package me.gavin.gavhackplus.gui.api.comp;

@Deprecated
public abstract class AbstractButton extends AbstractClickable {

	public AbstractButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public abstract void render(int mouseX, int mouseY);
	
	@Override
	public abstract void processClick(int mouseX, int mouseY, int mouseButton);
}
