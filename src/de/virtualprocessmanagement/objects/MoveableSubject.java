package de.virtualprocessmanagement.objects;

public class MoveableSubject extends RectShape {

	public MoveableSubject() {
		super();
	}

	public MoveableSubject(double arg0, double arg1, double arg2, double arg3, int x_index, int y_index) {
		super(arg0, arg1, arg2, arg3, x_index, y_index);
		
//		shape = new RectShape(RectShape.DEFAULT_WIDTH, 2.5*RectShape.DEFAULT_HEIGHT, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT);
		setSubjectTyp(RectShape.MOVEABLE_SUBJECT);
	}

	public MoveableSubject(double arg0, double arg1, int x_index, int y_index) {
		super(arg0, arg1, x_index, y_index);
		
		setSubjectTyp(RectShape.MOVEABLE_SUBJECT);
	}

}
