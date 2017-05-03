import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Bullet extends Sphere{
	//public double radius = 10;
	//Color color = Color.BLACK;
	double vx =0; double vy=0; double vz=0;
	
	public Bullet(double radius, Color fill){
		super(radius);
		final PhongMaterial black = new PhongMaterial();
		black.setDiffuseColor(fill);
		black.setSpecularColor(fill);
	
	}
    public void update() {
        setTranslateX(getTranslateX() + vx);
        setTranslateY(getTranslateY() + vy);
        setTranslateZ(getTranslateZ() + vz);
    }
}
