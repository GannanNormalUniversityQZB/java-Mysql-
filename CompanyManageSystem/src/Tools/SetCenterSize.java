package Tools;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
      //该类完成将窗口放在屏幕中央
public class SetCenterSize {
	public static Rectangle getCenterBounds(int frameWidth,int frameHeight){
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth=dim.width;   //屏幕的宽
		int screenHeight=dim.height;//屏幕的高
		if(screenWidth<frameWidth) frameWidth=screenWidth;
		if(screenHeight<frameHeight) frameHeight=screenHeight;
		Rectangle r=new Rectangle((screenWidth-frameWidth)/2, (screenHeight-frameHeight)/2, frameWidth, frameHeight);
		return r;
		
	}

}
