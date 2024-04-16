package part1.utils;

public record Point2D(double x, double y) {

	public static double len(Point2D p0, Point2D p1) {
		double dx = p0.x - p1.x;
		double dy = p0.y - p1.y;
		return Math.sqrt(dx * dx + dy * dy);
	}


}
