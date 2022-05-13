package ch.zhaw.graphy;

/**
 * Interface for all graphic objects that are displayed by the GUI
 */
public interface GraphGuiObject {
    /**
     * Returns if this object is colored
     * @return true if this object is not colored in standart color.
     */
    boolean isColored();
    /**
     * Sets the color of this object to standart color.
     */
    void setStdColor();
    /**
     * Sets the color of this object to selected color.
     */
    void setSelectedColor();
}
