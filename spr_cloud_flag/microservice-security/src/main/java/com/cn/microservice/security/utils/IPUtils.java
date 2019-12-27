package com.cn.microservice.security.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author LJ-silver
 */
public class IPUtils {
	/**
	 * 获取用户真实IP，用作校验
	 * @param httpServletRequest
	 * @return
	 */
	
	public static String getAccessRealIp(HttpServletRequest httpServletRequest) { 
		String ipAddress = null; 
		    ipAddress = httpServletRequest.getHeader("X-Real-IP"); 
		    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
		  ipAddress = httpServletRequest.getHeader("x-forwarded-for"); 
		} 
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
		  ipAddress = httpServletRequest.getHeader("Proxy-Client-IP"); 
		} 
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
		    ipAddress = httpServletRequest.getHeader("WL-Proxy-Client-IP"); 
		} 
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
		 ipAddress = httpServletRequest.getRemoteAddr(); 
		if(ipAddress.equals("127.0.0.1")){ 
		  InetAddress inet=null; 
		try { 
		inet = InetAddress.getLocalHost(); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		 ipAddress= inet.getHostAddress(); 
		} 
		} 
		if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15 
		    if(ipAddress.indexOf(",")>0){ 
		      String[] ipArray = new String[5]; 
		        //ipAddress = ipAddress.substring(0,ipAddress.indexOf(",")); 
		        ipArray = ipAddress.split(","); 
		        ipAddress = ipArray[1].trim(); 
		    } 
		} 
		return ipAddress; 
		}
	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	 *
	 * @return ip
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		System.out.println("x-forwarded-for ip: " + ip);
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if( ip.indexOf(",")!=-1 ){
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println("Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println("WL-Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			System.out.println("HTTP_CLIENT_IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
			System.out.println("X-Real-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			System.out.println("getRemoteAddr ip: " + ip);
		}
		System.out.println("获取客户端ip: " + ip);
		return ip;
	}
} 
