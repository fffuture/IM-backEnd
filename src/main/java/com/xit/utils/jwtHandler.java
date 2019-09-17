package com.xit.utils;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;

public class jwtHandler {

	private static String secret = "xitwsj";
	
	public static String creatJWT( String account, String device ) {
    	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//    	String authJm = secret;
    	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
    	Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());
    	Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, 10);
        Date expiresDate = nowTime.getTime();
 
    	JwtBuilder builder = Jwts.builder()
    			.setHeaderParam("typ","JWT")
    			.setHeaderParam("alg","HS256")
    			.setId(account)
    			.setAudience(device)
    			.setIssuedAt( new Date() )
    			.setExpiration( expiresDate )
    			.signWith(signatureAlgorithm,signingKey);//部分签名,用HS256加密
    	return builder.compact();
    }
    
	 public static Claims parseJWT(String jwt) {
//	    	String autuJm = secret;
	    	if(jwt.split("\\.").length == 3) {
	    		String payload = jwt.split("\\.")[1];//荷载
	    		System.out.println(Base64Codec.BASE64URL.decodeToString(payload));
//	    		String sign = jwt.split("\\.")[2];//签名
//	    		System.out.println(sign);
	    		JwsHeader claims1 = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret))
	    				.parseClaimsJws(jwt).getHeader();
	    		System.out.println("头部:"+claims1.toString());
	    		
	    		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret))
	    				.parseClaimsJws(jwt).getBody();//存储的是负荷信息
	    		
	    		return claims;
	    		
	    	}else {
	    		return null;
	    	}
	    }
	
	
	
}
