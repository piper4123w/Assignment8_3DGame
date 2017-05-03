import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.geometry.Point3D;

public class Bullet extends Sphere {
	// public double radius = 10;
	// Color color = Color.BLACK;
	double theta;
	double distFromGun = 0;
	double bulletSpeed = 25;
	double x, z;
	boolean isAlive = false;

	public Bullet(double theta) {
		super(20);
		this.theta = theta;
		final PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.WHITE);
		mat.setSpecularColor(Color.WHITE);
		this.setMaterial(mat);
		this.setTranslateY(-30);
		isAlive = true;
	}

	public void update() {
		if (isAlive) {
			distFromGun += bulletSpeed;
			x = Math.sin(theta) * distFromGun;
			z = Math.cos(theta) * distFromGun;
			this.setTranslateX(x);
			this.setTranslateZ(z);
			if (distFromGun + this.getRadius() > world.levelRad) {
				isAlive = false;
				x = 0;
				z = 0;
			}
		}
	}

	public boolean isHitting(Sphere s) {
		double sRad = s.getRadius();
		Point3D target = new Point3D(s.getTranslateX(), s.getTranslateY(), s.getTranslateZ());
		Point3D bullet = new Point3D(this.getTranslateX(), this.getTranslateY(), this.getTranslateZ());
		if (bullet.distance(target) + this.getRadius() < sRad)
			return true;
		return false;
	}
}
