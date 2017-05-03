import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Enemy extends Sphere {
	double x, y, z;
	double distFromGun = world.levelRad;
	double stepDist = 2;
	double theta = 0;

	public Enemy(double angle, double radius) {
		super(radius);
		theta = angle;
		x = Math.sin(theta) * world.levelRad;
		y = -20;
		z = Math.cos(theta) * world.levelRad;

		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setTranslateZ(z);
		final PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.RED);
		mat.setSpecularColor(Color.WHITE);
		this.setMaterial(mat);
	}

	public void update() {
		distFromGun -= stepDist;
		x = Math.sin(theta) * distFromGun;
		z = Math.cos(theta) * distFromGun;
		this.setTranslateX(x);
		this.setTranslateZ(z);
		if (x == 0 && z == 0) {
			System.out.println("GameOver");
		}
	}

}
