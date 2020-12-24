package com.cos.hello.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {

	public static void back(HttpServletResponse resp, String msg) throws IOException {
		PrintWriter out = resp.getWriter();
		out.print("<script>");
		out.print("alert('\"+msg+\"');");	//경고창
		out.print("history.back();");		//뒤로가기
		out.print("</script>");
		out.flush();
	}
	
	public static void href(HttpServletResponse resp, String url, String msg) throws IOException {
		PrintWriter out = resp.getWriter();
		out.print("<script>");
		out.print("alert('"+msg+"');");	//경고창
		out.print("location.href = '"+url+"';");
		out.print("</script>");
		out.flush();
	}
	
	public static void msg() {
		
	}
}
