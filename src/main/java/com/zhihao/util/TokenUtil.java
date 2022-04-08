package com.zhihao.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zhihao.common.MyException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TokenUtil {
    public static final String ISSUER = "zhihao1.cn";
    public static final String AUDIENCE = "Client";
    public static final String KEY = "ThisIsMySecretKey";
    public static final Algorithm ALGORITHM = Algorithm.HMAC256("ThisIsMySecretKey");
    public static final Map<String, Object> HEADER_MAP = new HashMap<String, Object>() {
        {
            this.put("alg", "HS256");
            this.put("typ", "JWT");
        }
    };

    public static String GenerateToken(Map<String, String> claimMap) {
        Date nowDate = new Date();
        Date expireDate = AddDate(nowDate, 60*2);
        Builder tokenBuilder = JWT.create();
        Iterator var4 = claimMap.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<String, String> entry = (Entry)var4.next();
            tokenBuilder.withClaim((String)entry.getKey(), (String)entry.getValue());
        }

        String token = tokenBuilder.withHeader(HEADER_MAP).withIssuer("zhihao1.cn").withAudience(new String[]{"Client"}).withIssuedAt(nowDate).withExpiresAt(expireDate).sign(ALGORITHM);
        return token;
    }

    private static Date AddDate(Date date, Integer minute) {
        if (null == date) {
            date = new Date();
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(12, minute);
        return calendar.getTime();
    }

    public static boolean VerifyJWTToken(String webToken) throws MyException {
        String token = webToken;
        if (webToken.equals("")) {
            throw new MyException(204, "token不能为空", "");
        } else {
            JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer("zhihao1.cn").build();

            try {
                verifier.verify(token);
            } catch (SignatureVerificationException var9) {
                throw new MyException(202, var9.getMessage(), "");
            } catch (TokenExpiredException var10) {
                throw new MyException(208, var10.getMessage(), "");
            } catch (InvalidClaimException var11) {
                throw new MyException(209, var11.getMessage(), "");
            } catch (JWTDecodeException var12) {
                throw new MyException(209, "不是有效的token", "");
            }

            DecodedJWT jwt = verifier.verify(webToken);
            List<String> audienceList = jwt.getAudience();
            String audience = (String)audienceList.get(0);
            Map<String, Claim> claimMap = jwt.getClaims();
            Iterator var7 = claimMap.entrySet().iterator();

            while(var7.hasNext()) {
                Entry<String, Claim> entry = (Entry)var7.next();
                System.out.println(((Claim)entry.getValue()).asString());
            }

            Date issueTime = jwt.getIssuedAt();
            Date expiresTime = jwt.getExpiresAt();
            System.out.println(jwt.getClaims());
            System.out.println(expiresTime);
            return true;
        }
    }
}
