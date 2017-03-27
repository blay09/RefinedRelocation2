package net.blay09.mods.refinedrelocation.util;

import net.minecraft.util.EnumFacing;

public enum RelativeSide {
	BACK,
	LEFT,
	RIGHT,
	TOP,
	BOTTOM,
	FRONT;

	public static RelativeSide fromFacing(EnumFacing baseFacing, EnumFacing facing) {
		if(baseFacing == facing) {
			return FRONT;
		} else if(baseFacing.getOpposite() == facing) {
			return BACK;
		}

		if(baseFacing.getAxis() == EnumFacing.Axis.Y) {
			EnumFacing rot = baseFacing.rotateAround(EnumFacing.Axis.X);
			if(rot == facing) {
				return BOTTOM;
			} else if(rot == facing.getOpposite()) {
				return TOP;
			}
		} else {
			if(facing == EnumFacing.UP) {
				return TOP;
			} else if(facing == EnumFacing.DOWN) {
				return BOTTOM;
			}
		}

		if(baseFacing.getAxis() == EnumFacing.Axis.Y) {
			EnumFacing rot = baseFacing.rotateAround(EnumFacing.Axis.Z);
			if(rot == facing) {
				return baseFacing.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? RIGHT : LEFT;
			} else if(rot == facing.getOpposite()) {
				return baseFacing.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? LEFT : RIGHT;
			}
		} else {
			if(baseFacing.rotateY() == facing) {
				return LEFT;
			} else if(baseFacing.rotateYCCW() == facing) {
				return RIGHT;
			}
		}

		return FRONT;
	}

	private static final RelativeSide[] values = values();

	public static RelativeSide fromIndex(int i) {
		return values[Math.abs(i % values.length)];
	}

	public EnumFacing toFacing(EnumFacing baseFacing) {
		switch(this) {
			case FRONT:
				return baseFacing;
			case BACK:
				return baseFacing.getOpposite();
			case LEFT:
				if(baseFacing.getAxis() == EnumFacing.Axis.Y) {
					return baseFacing.rotateAround(EnumFacing.Axis.Z);
				} else {
					return baseFacing.rotateY();
				}
			case RIGHT:
				if(baseFacing.getAxis() == EnumFacing.Axis.Y) {
					return baseFacing.rotateAround(EnumFacing.Axis.Z).getOpposite();
				} else {
					return baseFacing.rotateYCCW();
				}
			case TOP:
				if(baseFacing.getAxis() == EnumFacing.Axis.Y) {
					return baseFacing.rotateAround(EnumFacing.Axis.X);
				} else {
					return EnumFacing.UP;
				}
			case BOTTOM:
				if(baseFacing.getAxis() == EnumFacing.Axis.Y) {
					return baseFacing.rotateAround(EnumFacing.Axis.X).getOpposite();
				} else {
					return EnumFacing.DOWN;
				}
		}
		return null;
	}

	public RelativeSide getOpposite() {
		switch(this) {
			case BACK:
				return FRONT;
			case FRONT:
				return BACK;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			case TOP:
				return BOTTOM;
			case BOTTOM:
				return TOP;
		}
		return this;
	}

	@SuppressWarnings("SuspiciousNameCombination")
	public RelativeSide rotateY() {
		switch(this) {
			case BACK:
				return RIGHT;
			case RIGHT:
				return FRONT;
			case FRONT:
				return LEFT;
			case LEFT:
				return BACK;
		}
		return this;
	}

	@SuppressWarnings("SuspiciousNameCombination")
	public RelativeSide rotateX() {
		switch(this) {
			case TOP:
				return LEFT;
			case BOTTOM:
				return RIGHT;
			case RIGHT:
				return BOTTOM;
			case LEFT:
				return TOP;
		}
		return this;
	}

	public RelativeSide rotateZ() {
		switch(this) {
			case TOP:
				return BACK;
			case BOTTOM:
				return FRONT;
			case BACK:
				return TOP;
			case FRONT:
				return BOTTOM;
		}
		return this;
	}
}
