package game;

public class Hydra extends GameObject {
	
	public Hydra(int x, int y) {
		super(new Sprite(new int[]{4},new int[]{4},"hydra"), x, y);
	}

	@Override
	public void update(double dt) {
		this.sprite.animate("hydra", dt);
	}

	@Override
	public boolean isCollidable(GameObject obj) {
		return false;
	}

}
