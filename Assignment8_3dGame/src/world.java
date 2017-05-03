import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;

/**
 * Simple 3D Game
 * 
 * @author Courtney Connery and Kyle Lawson
 *
 */

public class world extends Application {
	final String appName = "Shooter Game";
	final int FPS = 30;
	private Group cameraDolly;
	private final double sceneWidth = 600;
	private final double sceneHeight = 600;

	private double camDist = 50;

	public static double levelRad = 1000;

	private double spawnChance = 0.005;
	private int levelCount = 0;
	private double gameTime = 0;

	ArrayList<Enemy> enemyList;

	Gun gun;
	Bullet amo;
	Group sceneRoot; 
	boolean shoot = false; 
	
	private void constructWorld(Group root) {
		AmbientLight light = new AmbientLight(Color.rgb(100, 100, 100));

		PointLight pl = new PointLight();
		pl.setTranslateX(100);
		pl.setTranslateY(-100);
		pl.setTranslateZ(-100);
		root.getChildren().add(pl);

		gun = new Gun();

		final PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.DARKGREEN);
		mat.setSpecularColor(Color.GREEN);
		Cylinder floor = new Cylinder(levelRad, 1);
		floor.setMaterial(mat);

		Sphere skySphere = new Sphere(levelRad);
		final PhongMaterial skyMat = new PhongMaterial();
		skyMat.setDiffuseMap(new Image(getClass().getResource("sky.jpg").toExternalForm()));
		skySphere.setMaterial(skyMat);
		skySphere.setCullFace(CullFace.NONE);

		root.getChildren().addAll(light, gun, floor, skySphere);
		enemyList = new ArrayList<Enemy>();
	}

	public void update(Group root) {
		gun.update();
		if (shoot == true){
		amo.update();}
		
		ArrayList<Enemy> killList = new ArrayList<Enemy>();

		root.getChildren().removeAll(enemyList);
		for (Enemy e : enemyList) {
			e.update();
			if (!e.isAlive) {
				killList.add(e);
			}
		}
		enemyList.removeAll(killList);
		root.getChildren().removeAll(killList);

		if (Math.random() < spawnChance) {
			double theta = Math.toRadians(Math.random() * 360);
			double rad = (Math.random() * 25) + 25;
			Enemy en = new Enemy(theta, rad);
			enemyList.add(en);
		}
		root.getChildren().addAll(enemyList);
		gameTime += 0.0333333333; // game Time
		levelCount++;
		if (levelCount >= 600) {
			spawnChance += 0.005; // spawn ammount increases every 3 seconds
			System.out.println("harder");
			levelCount = 0;
		}
	}

	public void shoot(){
		shoot = true;
		amo = new Bullet(20, Color.BLACK);
		Point3D loc = gun.localToScene(0, 0, 60);
		amo.setTranslateX(loc.getX());
		amo.setTranslateY(loc.getY()-30);
		amo.setTranslateZ(loc.getZ()+100);
		Transform rot = gun.getTransforms().get(0);
		Point3D vel = rot.deltaTransform(0, 0, 4);
		amo.vx = vel.getX(); amo.vy = vel.getY(); amo.vz = vel.getZ();
		amo.setVisible(true);
		sceneRoot.getChildren().add(amo);
	}
	@Override
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		// Build your Scene and Camera
		sceneRoot = new Group();
		constructWorld(sceneRoot);

		Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight, true);
		scene.setFill(Color.LIGHTBLUE);

		scene.setCamera(gun.camera);
		// translations through dolly
		cameraDolly = new Group();
		// cameraDolly.setTranslateZ();
		cameraDolly.setTranslateX(0);
		cameraDolly.setTranslateY(-100);
		cameraDolly.setTranslateZ(-100);
		cameraDolly.getChildren().add(gun.camera);
		cameraDolly.setRotationAxis(Rotate.Y_AXIS);
		sceneRoot.getChildren().add(cameraDolly);
		// rotation transforms

		// Use keyboard to control camera position
		scene.setOnKeyPressed(event -> {

			KeyCode keycode = event.getCode();

			if (keycode == KeyCode.A) {
				gun.angle -= 1;
			}
			if (keycode == KeyCode.D) {
				gun.angle += 1;
			}
			if (keycode == KeyCode.ENTER) {
				System.out.println(gun.xRotate.getAngle() + " = X rot");
				System.out.println(gun.yRotate.getAngle() + " = Y rot");
				System.out.println(cameraDolly.getTranslateX() + "," + cameraDolly.getTranslateY() + ","
						+ cameraDolly.getTranslateZ());
			}
			if (keycode == KeyCode.W && camDist > 25) { // zooming
				camDist -= 1;
			}
			if (keycode == KeyCode.S && camDist < 100) {
				camDist += 1;
			}
			if(keycode == KeyCode.SPACE){
				shoot();
			}

		});

		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS), e -> {
			// update position
			update(sceneRoot);
			cameraDolly.setRotate(gun.angle);
			cameraDolly.setTranslateX(-Math.sin(Math.toRadians(gun.angle)) * camDist);
			cameraDolly.setTranslateZ(-Math.cos(Math.toRadians(gun.angle)) * camDist);
		});
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		primaryStage.setTitle("World1");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
