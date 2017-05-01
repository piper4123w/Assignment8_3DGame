import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;

/**
 * Simple 3D Game
 * @author Courtney Connery and Kyle Lawson
 *
 */


public class world extends Application {
	private PerspectiveCamera camera;
	private Group cameraDolly;
	private final double cameraQuantity = 10.0;
	private final double sceneWidth = 600;
	private final double sceneHeight = 600;

	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;
	
	
	private void constructWorld(Group root){
		AmbientLight light = new AmbientLight(Color.rgb(100, 100, 100));

		PointLight pl = new PointLight();
		pl.setTranslateX(100);
		pl.setTranslateY(-100);
		pl.setTranslateZ(-200);
		root.getChildren().add(pl);
	}
	@Override
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		// Build your Scene and Camera
				Group sceneRoot = new Group();
				constructWorld(sceneRoot);

				Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight, true);
				scene.setFill(Color.LIGHTBLUE);
				camera = new PerspectiveCamera(true);
				camera.setNearClip(0.1);
				camera.setFarClip(10000.0);
				scene.setCamera(camera);
				// translations through dolly
				cameraDolly = new Group();
				cameraDolly.setTranslateZ(-1000);
				cameraDolly.setTranslateX(200);
				cameraDolly.getChildren().add(camera);
				sceneRoot.getChildren().add(cameraDolly);
				// rotation transforms
				Rotate xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
				Rotate yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
				camera.getTransforms().addAll(xRotate, yRotate);

				// Use keyboard to control camera position
				scene.setOnKeyPressed(event -> {
					double change = cameraQuantity;
					// What key did the user press?
					KeyCode keycode = event.getCode();

					Point3D delta = null;
					if (keycode == KeyCode.COMMA) {
						delta = new Point3D(0, 0, change);
					}
					if (keycode == KeyCode.PERIOD) {
						delta = new Point3D(0, 0, -change);
					}
					if (keycode == KeyCode.A) {
						delta = new Point3D(-change, 0, 0);
					}
					if (keycode == KeyCode.D) {
						delta = new Point3D(change, 0, 0);
					}
					if (keycode == KeyCode.W) {
						delta = new Point3D(0, -change, 0);
					}
					if (keycode == KeyCode.S) {
						delta = new Point3D(0, change, 0);
					}

					if (delta != null) {
						Point3D delta2 = camera.localToParent(delta);
						cameraDolly.setTranslateX(cameraDolly.getTranslateX() + delta2.getX());
						cameraDolly.setTranslateY(cameraDolly.getTranslateY() + delta2.getY());
						cameraDolly.setTranslateZ(cameraDolly.getTranslateZ() + delta2.getZ());

					}
				});

				// Use mouse to control camera rotation
				scene.setOnMousePressed(me -> {
					mousePosX = me.getSceneX();
					mousePosY = me.getSceneY();
				});

				scene.setOnMouseDragged(me -> {
					mouseOldX = mousePosX;
					mouseOldY = mousePosY;
					mousePosX = me.getSceneX();
					mousePosY = me.getSceneY();
					mouseDeltaX = (mousePosX - mouseOldX);
					mouseDeltaY = (mousePosY - mouseOldY);

					yRotate.setAngle(((yRotate.getAngle() - mouseDeltaX * 0.2) % 360 + 540) % 360 - 180); // +
					xRotate.setAngle(((xRotate.getAngle() + mouseDeltaY * 0.2) % 360 + 540) % 360 - 180); // -
				});
				primaryStage.setTitle("World1");
				primaryStage.setScene(scene);
				primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
