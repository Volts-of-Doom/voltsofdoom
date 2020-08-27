package vision.voltsofdoom.silverspark.display;

import vision.voltsofdoom.silverspark.text.FontState;

public class Label {

    private final String key;
    private final FontState font;
    private final String mainText;

    public Label (String key, FontState font, String mainText) {
        this.key = key;
        this.font = font;
        this.mainText = mainText;
    }

    public String getKey() {
        return key;
    }

    public FontState getFont() {
        return font;
    }

    public String getMainText() {
        return mainText;
    }
}
