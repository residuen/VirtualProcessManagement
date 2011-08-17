package de.virtualprocessmanagement.objects;

public class MoveableSubject extends RectShape {

	public MoveableSubject() {
		super();
	}

	public MoveableSubject(double arg0, double arg1, double arg2, double arg3) {
		super(arg0, arg1, arg2, arg3);
		
//		shape = new RectShape(RectShape.DEFAULT_WIDTH, 2.5*RectShape.DEFAULT_HEIGHT, RectShape.DEFAULT_WIDTH, RectShape.DEFAULT_HEIGHT);
		setSubjectTyp(RectShape.MOVEABLE_SUBJECT);
	}

	public MoveableSubject(double arg0, double arg1) {
		super(arg0, arg1);
		
		setSubjectTyp(RectShape.MOVEABLE_SUBJECT);
	}

}
