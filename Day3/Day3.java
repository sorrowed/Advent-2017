public class Day3 {
	class Point {
		public int x;
		public int y;
		public int id;

		public void setValues(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}

	public enum Dir {
		Right, Up, Left, Down
	}

	public static void main(String[] args) {
		Day3 day3 = new Day3();

		int count = 265149;

		Point[] cells = day3.createCells(count);

		Point target = cells[count - 1];

		System.out.printf("Target is %d(%d,%d), MD=%d\n", target.id, target.x, target.y,
				Math.abs(target.x) + Math.abs(target.y));

		return;
	}

	public Point[] createCells(int count) {
		Point[] cells = new Point[count];

		int current = 0;
		Dir dir = Dir.Right;
		int actions = 1;

		Point o = new Point();
		o.setValues(1, 0, 0);
		cells[current++] = o;

		while (current < count) {

			for (int i = 0; i < actions && current < count; ++i) {

				Point prev = cells[current - 1];

				Point p = fromPoint(prev, dir);

				cells[current++] = p;
			}

			switch (dir) {
			case Right:
				dir = Dir.Up;
				break;
			case Up:
				dir = Dir.Left;
				++actions;
				break;
			case Left:
				dir = Dir.Down;
				break;
			case Down:
				dir = Dir.Right;
				++actions;
				break;
			}
		}

		return cells;
	}

	Point fromPoint(Point p, Dir d) {

		Point r = new Point();

		switch (d) {
		case Right:
			r.setValues(p.id + 1, p.x + 1, p.y);
			break;
		case Up:
			r.setValues(p.id + 1, p.x, p.y + 1);
			break;
		case Left:
			r.setValues(p.id + 1, p.x - 1, p.y);
			break;
		case Down:
			r.setValues(p.id + 1, p.x, p.y - 1);
			break;
		}

		return r;
	}
}