package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserAPI")
public class UserAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User CartObj = new User();
	
	/**
     * @see HttpServlet#HttpServlet()
     */
	
	public UserAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String output = CartObj.insertCart(request.getParameter("hidItemIDSave"),
				request.getParameter("buyerName"),
				request.getParameter("address"),
				request.getParameter("NIC"),
				request.getParameter("softwareName"), 
				request.getParameter("size"),
				request.getParameter("version"), 
				request.getParameter("cost"),
				request.getParameter("date")
				);
response.getWriter().write(output);
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map paras = getParasMap(request); 
		String output = CartObj.updateCart(paras.get("hidItemIDSave").toString(), 
												paras.get("buyerName").toString(), 
												paras.get("address").toString(),
												paras.get("NIC").toString(), 
												paras.get("softwareName").toString(), 
												paras.get("size").toString(), 
												paras.get("version").toString(), 
												paras.get("cost").toString(), 
												paras.get("date").toString()
												
												); 
		response.getWriter().write(output); 
	}

	/*private Map getParasMap(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	Map paras = getParasMap(request); 
		
		String output = CartObj.deleteCart(paras.get("orderId").toString()); 
		response.getWriter().write(output);
	}
	// Convert request parameters to a Map
		
		private static Map getParasMap(HttpServletRequest request) {
			
			Map<String, String> map = new HashMap<String, String>(); 
			try{ 
				
				 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
				 String queryString = scanner.hasNext() ? 
				 scanner.useDelimiter("\\A").next() : ""; 
				 scanner.close(); 
				 String[] params = queryString.split("&"); 
				 for (String param : params) {
					 
					 String[] p = param.split("=");
					 map.put(p[0], p[1]); 
				 } 
			}catch (Exception e) { 
				
			} 
			return map; 
		}

}
