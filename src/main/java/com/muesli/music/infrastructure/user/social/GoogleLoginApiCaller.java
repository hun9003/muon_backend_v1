package com.muesli.music.infrastructure.user.social;

import com.muesli.music.domain.user.User;
import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.social.SocialMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleLoginApiCaller implements SocialLoginApiCaller {
    @Override
    public boolean support(SocialMethod socialMethod) {
        return SocialMethod.GOOGLE == socialMethod;
    }

    @Override
    public User getSocialUserInfo(UserCommand.SocialLoginRequest command) {
        URL url = null;
        String readLine = null;
        StringBuilder buffer = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        HttpURLConnection urlConnection = null;

        int connTimeout = 5000;
        int readTimeout = 3000;
        System.out.println("token : "+command.getAccessToken());
        String apiUrl = "https://www.googleapis.com/oauth2/v2/userinfo?access_token="+command.getAccessToken();	// 각자 상황에 맞는 IP & url 사용

        try
        {
            url = new URL(apiUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.setRequestProperty("Accept", "application/json;");

            buffer = new StringBuilder();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
                while((readLine = bufferedReader.readLine()) != null)
                {
                    buffer.append(readLine).append("\n");
                }
            }
            else
            {
                buffer.append("code : ");
                buffer.append(urlConnection.getResponseCode()).append("\n");
                buffer.append("message : ");
                buffer.append(urlConnection.getResponseMessage()).append("\n");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufferedWriter != null) { bufferedWriter.close(); }
                if (bufferedReader != null) { bufferedReader.close(); }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        var result = JSONValue.parse(buffer.toString());
        JSONObject js = (JSONObject) result;
        System.out.println(buffer.toString());
        String email = (String) js.get("email");
        String username = (String) js.get("name");
        String image = (String) js.get("picture");
        var userCommand = UserCommand.RegisterUserRequest.builder().build();
        return userCommand.toSocialEntity(email, username, image);
    }
}
