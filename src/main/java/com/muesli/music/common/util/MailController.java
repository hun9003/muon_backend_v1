package com.muesli.music.common.util;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;


public class MailController {

    public static void main(String[] args) throws Exception {
        sendMail("hun9003@naver.com", "찌눈", REGISTER);
    }

    public static final String REGISTER = "회원가입";
    public static final String FIND_PASSWORD = "비밀번호 찾기";

    public static void sendMail(String email, String username, String type) throws Exception {
        MailTemplate template = new MailTemplate();

        if (type.equals(REGISTER)) {
            template.registerTemplate(email, username);
        } else {
            return;
        }

        String title = template.title;
        String content = template.content;

        URL url;
        HttpURLConnection conn;
        String jsonData;
        BufferedReader br = null;
        StringBuilder sb;
        String returnText = "";
        String targetUrl = "http://141.164.35.140:8080/?key=muon_system_web";

        try {
            url = new URL(targetUrl);
            Map<String, Object> params = new LinkedHashMap<>(); // 파라미터 세팅
            params.put("mail", email);
            params.put("title", title);
            params.put("contents", content);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestProperty("Accept", "application/json");
//            conn.setRequestProperty("Authorization", "Bearer 68651222360e1dfb8901a939067c57bb485856d1");
//            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.getOutputStream().write(postDataBytes);
            conn.connect();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            sb = new StringBuilder();

            while ((jsonData = br.readLine()) != null) {
                sb.append(jsonData);
            }

            returnText = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Getter
    public static class MailTemplate {

        private String title;
        private String content;
        private String email;

        private String makeApiUrl(String api, String email) throws Exception {
            // 현재 시간 호출
            String now = System.currentTimeMillis()+"";
            now = now.substring(0,now.length()-3);

            // api key 인코딩 전달 값
            String makeKey = "[\""+email+"\","+now+"]";

            // url , api, key 세팅
            String url = "https://music.moe.work";
            String key = URLEncoder.encode(HashGenerator.encAES(makeKey), "UTF-8").replace("*", "%2A").replace("+", "%20").replace("%7E", "~");

            return url+api+"?key="+key;
        }

        public void registerTemplate(String email, String username) throws Exception {
            String api = "/api/v1/user/verification";
            String apiurl = makeApiUrl(api, email);
            String type = "회원가입";

            this.email = email;
            this.title = "MuOn 회원가입 인증 메일입니다.";
            this.content = baseTemplate(type, apiurl, username);
        }

        private String baseTemplate(String type, String apiurl,String username) {
            return "<meta charset=\"utf-8\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#333\" style=\"font-family:'나눔고딕', 'Nanum Gothic', 'NanumGothic', '맑은고딕', 'MalgunGothic', 'Apple SD Gothic Neo', sans-serif;\">\n" +
                    "    <tbody>\n" +
                    "        <!-- icon -->\n" +
                    "        <tr>\n" +
                    "            <td align=\"center\" style=\"padding-top:30px; padding-bottom:20px;\">\n" +
                    "                <img src=\"https://rawcdn.githack.com/DevMoeWork/assets/3e911ea15ccc0cf87a51a09ed07ac37dcce17d1b/email/topLogo.png\" alt=\"MuOn 로고\">\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "\n" +
                    "        <!-- header -->\n" +
                    "        <tr>\n" +
                    "            <td align=\"center\">\n" +
                    "                <table border=\"0\" cellpadding=\"0\" align=\"center\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "                    <tbody>\n" +
                    "                        <tr>\n" +
                    "                            <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"border-radius:3px 3px 0px 0px; padding-top:10px; padding-bottom:10px;\">\n" +
                    "                                <h1 style=\"color:#333\">MuOn "+type+"</h1>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </tbody>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "\n" +
                    "        <!-- content -->\n" +
                    "        <tr>\n" +
                    "            <td bgcolor=\"#eee\" align=\"center\" >\n" +
                    "                <table border=\"0\" cellpadding=\"0\" align=\"center\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "                    <tbody>\n" +
                    "                        <tr>\n" +
                    "                            <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"border-radius:0px 0px 3px 3px; padding-top:10px; padding-left:20px; padding-right:20px; padding-bottom:10px; text-align:left; letter-spacing:-0.01em; line-height:1.5; color:#333; font-size:0.95em;\">\n" +
                    "                                <p>안녕하세요, "+username+"님.</p>\n" +
                    "                                <p>서브컬쳐 음원 스트리밍 서비스 MuOn 입니다. 고객님께서 <b>MuOn</b> 서비스에 "+type+"을 요쳥해주신 부분을 확인하여 이메일 인증 링크를 발송해드립니다.</p>\n" +
                    "                                <p>만약 고객님께서 MuOn 서비스에 "+type+"을 요청하신게 아니라면, 본 메일을 삭제하여 주시거나 무시하여 주시기 바랍니다.</p>\n" +
                    "                                <p style=\"padding-top:20px; padding-bottom:20px;\">\n" +
                    "                                    <a href=\""+apiurl+"\" style=\"display:block; width:234px; height:47px; background:#aea1e9; color:#fff; text-align:center; text-decoration:none; line-height:47px; margin-left:auto; margin-right:auto;\">이메일 인증</a>\n" +
                    "                                </p>\n" +
                    "                                <p>위의 버튼이 눌리지 않는 경우, 아래의 링크를 브라우저에 붙여넣어서 이메일 인증을 진행할 수 있습니다.</p>\n" +
                    "                                <p><a href=\""+apiurl+"\" style=\"text-decoration:underline; color:#aea1e9;\">"+apiurl+"</a></p>\n" +
                    "                                <p>본 이메일과 관련하여 문의사항이 있는 경우, 하단의 채널톡을 통해서 문의 남겨주시면 영업시간 중으로 답변 드리겠습니다.</p>\n" +
                    "                                <p>감사합니다.<br>- MuOn 운영팀 드림</p>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </tbody>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td style=\"padding-top:40px; padding-bottom:40px;\" bgcolor=\"#eee\" align=\"center\" >\n" +
                    "                <table border=\"0\" cellpadding=\"0\" align=\"center\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "                    <tbody>\n" +
                    "                        <td bgcolor=\"#f9f9f9\" align=\"center\" valign=\"top\" style=\"font-size:0.9em; padding-top:15px; padding-bottom:15px;\">\n" +
                    "                            <p>도움이 필요하신가요?</p>\n" +
                    "                            <p><a href=\"https://muon.channel.io/\" style=\"text-decoration:underline; color:#aea1e9;\">채널톡을 통해서 문의하기</a></p>\n" +
                    "                        </td>\n" +
                    "                    </tbody>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>";
        }
    }
}
