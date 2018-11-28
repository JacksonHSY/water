<%@page import="javax.imageio.ImageIO"%>
<%@ page contentType="image/jpeg"%>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.util.Random"/>
<jsp:directive.page import="java.awt.image.BufferedImage"/>
<jsp:directive.page import="java.awt.Graphics2D"/>
<jsp:directive.page import="java.awt.Color"/>
<jsp:directive.page import="java.awt.Font"/>
<%!
	Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
%>
<%
	//Solves on weblogic the response already committed question
	response.reset();

	//set no cache
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);

	int length = 4;
	Random random = new Random(new Date().getTime());
	//get verify code length
	String reqLen=request.getParameter("length");
	if (null != reqLen && !"".equals(reqLen)) {
		try {
			length = Integer.parseInt(reqLen);
		}catch (NumberFormatException e) {
			System.out.println(e);
			length = 4;
		}
	}
	//get image
	int width=70, height=22;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	Graphics2D g = image.createGraphics();
	//backgroundcolor
	g.setColor(getRandColor(240,255));
	g.fillRect(0, 0, width, height);
	//set font
	g.setFont(new Font("Times New Roman",Font.PLAIN,24));
	//set disturb background line, count=30
	g.setColor(getRandColor(160,200));
	for (int i=0;i<100;i++) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(12);
        int yl = random.nextInt(12);
        g.drawLine(x,y,x+xl,y+yl);
	}
	//get verify code
	StringBuffer sb = new StringBuffer();
	String[] ss= {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	for (int i = 0; i < length; i++) {
		String rand=ss[random.nextInt(ss.length)];
		sb.append(rand);
		//font color and location
		g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
    	g.drawString(rand,15*i+4,18);
	}
	
	//set verifyCode to session attribute
	request.getSession().setAttribute("vnum",sb.toString());   
	
	//out image
	//if deviant show ,use follow on the heels of code (now annotated)
	//ImageIO.write(image, "JPEG", response.getOutputStream());
	/* JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
	JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
	param.setQuality(1.0f, false);
	encoder.setJPEGEncodeParam(param);
	encoder.encode(image); */
	ImageIO.write(image, "JPEG", response.getOutputStream());
	//complete out
	response.flushBuffer();
	
	//add by zhuheng
	out.clear();
	out = pageContext.pushBody();
	
%> 