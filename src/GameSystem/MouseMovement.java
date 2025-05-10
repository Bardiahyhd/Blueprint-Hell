package GameSystem;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;


public class MouseMovement {

    public static double mouseAnchorX;
    public static double mouseAnchorY;
    public static double initialTranslateX;
    public static double initialTranslateY;

    public static void onMousePressed(MouseEvent e, Group group) {
        mouseAnchorX = e.getSceneX();
        mouseAnchorY = e.getSceneY();
        initialTranslateX = group.getTranslateX();
        initialTranslateY = group.getTranslateY();
    }

    public static void onMouseDragged(MouseEvent e, Group group) {
        double offsetX = e.getSceneX() - mouseAnchorX;
        double offsetY = e.getSceneY() - mouseAnchorY;

        double newTranslateX = initialTranslateX + offsetX;
        double newTranslateY = initialTranslateY + offsetY;

        group.setTranslateX(newTranslateX);
        group.setTranslateY(newTranslateY);
    }

}
