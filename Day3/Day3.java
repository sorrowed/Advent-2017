import java.util.HashMap;

public class Day3 {

	class Key {
		private int x;
		private int y;

		public Key(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return String.format("%d %d", x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (!Key.class.isAssignableFrom(obj.getClass())) {
				return false;
			}

			Key b = (Key) obj;
			return x == b.x && y == b.y;
		}

		@Override
		public int hashCode() {
			return x ^ y;
		}
	}

	class Point {

		public int x;
		public int y;
		public int id;

		public Key getKey() {
			return new Key(x, y);
		}

		public void setValues(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}

		public void addNeighbor(Point n) {
			if (n != null) {
				id += n.id;
			}
		}
	}

	public enum Dir {
		Right, Up, Left, Down
	}

	public static void main(String[] args) {
		Day3 day3 = new Day3();

		day3.Part1();

		day3.Part2();

		return;
	}

	public static final int Count = 265149;

	public void Part1() {

		Point target = createCells1(Count);

		System.out.printf("Target is %d(%d,%d), MD=%d\n", target.id, target.x, target.y,
				Math.abs(target.x) + Math.abs(target.y));
	}

	public void Part2() {
		Point target = createCells2(Count);

		System.out.printf("Target is %d(%d,%d)\n", target.id, target.x, target.y);
	}

	public Point createCells1(int count) {
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

				Point p = fromPoint1(prev, dir);

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

		return cells[cells.length - 1];
	}

	private Point fromPoint1(Point p, Dir d) {

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

	public Point createCells2(int input) {
		HashMap<Key, Point> cells = new HashMap<Key, Point>();

		Dir dir = Dir.Right;
		int actions = 1;

		Point p = new Point();
		p.setValues(1, 0, 0);
		cells.put(p.getKey(), p);

		while (p.id < input) {

			for (int i = 0; i < actions && p.id < input; ++i) {

				Point next = fromPoint2(p, dir);

				addNeighbors(cells, next);

				cells.put(next.getKey(), next);

				p = next;
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

		return p;
	}

	private Point fromPoint2(Point p, Dir d) {

		Point r = new Point();

		switch (d) {
		case Right:
			r.setValues(0, p.x + 1, p.y);
			break;
		case Up:
			r.setValues(0, p.x, p.y + 1);
			break;
		case Left:
			r.setValues(0, p.x - 1, p.y);
			break;
		case Down:
			r.setValues(0, p.x, p.y - 1);
			break;
		}

		return r;
	}

	private void addNeighbors(HashMap<Key, Point> cells, Point p) {
		p.addNeighbor(cells.get(new Key(p.x + 1, p.y)));
		p.addNeighbor(cells.get(new Key(p.x + 1, p.y + 1)));
		p.addNeighbor(cells.get(new Key(p.x, p.y + 1)));
		p.addNeighbor(cells.get(new Key(p.x - 1, p.y + 1)));
		p.addNeighbor(cells.get(new Key(p.x - 1, p.y)));
		p.addNeighbor(cells.get(new Key(p.x - 1, p.y - 1)));
		p.addNeighbor(cells.get(new Key(p.x, p.y - 1)));
		p.addNeighbor(cells.get(new Key(p.x + 1, p.y - 1)));
	}

}