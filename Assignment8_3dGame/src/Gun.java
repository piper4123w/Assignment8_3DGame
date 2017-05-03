import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

/**
 * Based off Dr. Slattery's gun class in shooting 3D
 */

public class Gun extends Group {
	public PerspectiveCamera camera;
	Color color = Color.BLACK;
	Rotate rotate = new Rotate();
	double angle = 0;

	Rotate yRotate;
	Rotate xRotate;
	
	public Gun() {
		super();
		final PhongMaterial black = new PhongMaterial();
		black.setDiffuseColor(color);
		Sphere s = new Sphere(60);
		s.setMaterial(black);
		Cylinder barrel = new Cylinder(20, 170);
		barrel.setRotate(90);
		barrel.setRotationAxis(new Point3D(1, 0, 0));
		barrel.setTranslateZ(110);
		barrel.setTranslateY(-30);
		Cylinder muzzel = new Cylinder(24, 10);
		muzzel.setRotate(90);
		muzzel.setRotationAxis(new Point3D(1, 0, 0));
		muzzel.setTranslateZ(110);
		muzzel.setTranslateY(-30);
		muzzel.setMaterial(black);
		getChildren().addAll(s, barrel, muzzel);

		rotate.setAngle(angle);
		rotate.setAxis(Rotate.Y_AXIS);
		getTransforms().add(rotate);
		setTranslateY(-20);

		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		xRotate.setAngle(-7);
		camera.getTransforms().addAll(yRotate, xRotate);
	}

	public void update() {
		rotate.setAngle(angle);
		
	}

}
