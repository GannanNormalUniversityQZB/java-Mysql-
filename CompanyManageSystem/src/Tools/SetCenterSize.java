package Tools;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
      //������ɽ����ڷ�����Ļ����
public class SetCenterSize {
	public static Rectangle getCenterBounds(int frameWidth,int frameHeight){
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth=dim.width;   //��Ļ�Ŀ�
		int screenHeight=dim.height;//��Ļ�ĸ�
		if(screenWidth<frameWidth) frameWidth=screenWidth;
		if(screenHeight<frameHeight) frameHeight=screenHeight;
		Rectangle r=new Rectangle((screenWidth-frameWidth)/2, (screenHeight-frameHeight)/2, frameWidth, frameHeight);
		return r;
		
	}

}
