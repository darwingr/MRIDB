package engine.gfx;

public class Font {

	public static final Font STANDARD = new Font("font");
	public static final Font SMALL_STANDARD = new Font("font_small");
	
	private Image fontImage;
	private int[] offsets;
	private int[] widths;
	
	public Font(String path) {
		fontImage = new Image(path);
		
		offsets =  new int[256];
		widths = new int[256];
		
		int unicode = 0;
		
		for(int i = 0; i < fontImage.getW(); i++) {
			if(fontImage.getP()[i] == 0xff0000ff) {
				offsets[unicode] = i;
			}
			if(fontImage.getP()[i] == 0xffffff00) {
				widths[unicode] = i-offsets[unicode];
				unicode++;
			}
		}
	}

	public Image getFontImage() {
		return fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public int[] getWidths() {
		return widths;
	}
	
}
