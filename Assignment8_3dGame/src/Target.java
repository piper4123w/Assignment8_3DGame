import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Target extends Sphere{
	double x, y, z;

	public Target(double x, double y, double z, double radius) {
		super(radius);
		this.x = x;
		this.y = y;
		this.z = z;
		final PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(Color.RED);
        mat.setSpecularColor(Color.WHITE);
	}
	
	
	
}
