import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

/** 
 * Based off Dr. Slattery's gun class in shooting 3D
 */

public class Gun extends Group {
	Color color = Color.BLACK;
	Rotate rotate = new Rotate(); 
	int angle = 30; 
	
	public Gun() {
		super();
		final PhongMaterial black = new PhongMaterial();
		black.setDiffuseColor(color);
		Sphere s = new Sphere(30);
		s.setMaterial(black);
		Cylinder barrel = new Cylinder(20,80);
		barrel.setRotate(90);
		barrel.setRotationAxis(new Point3D(1,0,0));
		barrel.setTranslateZ(110);
		Cylinder muzzel = new Cylinder(24,10);
		muzzel.setRotate(90);
		muzzel.setRotationAxis(new Point3D(1,0,0));
		muzzel.setTranslateZ(110);
		muzzel.setMaterial(black);
		getChildren().addAll(s, barrel, muzzel);
		
		rotate.setAngle(angle);
		rotate.setAxis(Rotate.X_AXIS);
		getTransforms().add(rotate);
		
		setTranslateY(-20);
	}

}
