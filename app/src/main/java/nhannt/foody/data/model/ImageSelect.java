package nhannt.foody.data.model;

/**
 * Created by nhannt on 29/11/2017.
 */
public class ImageSelect {
    String path;
    boolean isChecked;

    public ImageSelect() {
    }

    public ImageSelect(String path, boolean isChecked) {
        this.path = path;
        this.isChecked = isChecked;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
