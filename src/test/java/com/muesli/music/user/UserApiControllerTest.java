package com.muesli.music.user;

import com.muesli.music.JwtToken;
import com.muesli.music.MuonBackendV1Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class UserApiControllerTest {
    private MockMvc mockMvc;
    private final String JWT_TOKEN = JwtToken.JWT_TOKEN;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("user/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

//    @Test
//    @DisplayName("회원가입 TEST")
//    public void registerUser() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        var requestParams = UserDto.RegisterUser.builder()
//                .email("test12433@testmusicweb1.com")
//                .password("12345678")
//                .username("테스트유저")
//                .phoneNumber("010-0000-0000")
//                .gender("M")
//                .authType("local")
//                .build();
//        this.mockMvc.perform(post("/api/v1/user")
//                        .header("content-type", "application/json")
//                        .content(mapper.writeValueAsString(requestParams))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("user/{method-name}",
//                        requestFields(
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
//                                fieldWithPath("username").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
//                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호"),
//                                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
//                                fieldWithPath("birthday").type(JsonFieldType.STRING).description("생일").ignored(),
//                                fieldWithPath("authType").type(JsonFieldType.STRING).description("가입 타입")
//                        ),
//                        responseFields(
//                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
//                                fieldWithPath("message.dev").type(JsonFieldType.STRING).description("결과 메세지").optional(),
//                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
//                                fieldWithPath("data.username").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
//                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호"),
//                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
//                                fieldWithPath("data.birthday").type(JsonFieldType.STRING).description("생일").optional()
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("로그인 TEST")
//    public void loginUser() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        var requestParams = UserDto.LoginUser.builder()
//                .email("test12433@testmusicweb1.com")
//                .password("12345678")
//                .build();
//        this.mockMvc.perform(post("/api/v1/user/login")
//                        .header("content-type", "application/json")
//                        .content(mapper.writeValueAsString(requestParams))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("user/{method-name}",
//                        requestFields(
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
//                                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
//                        ),
//                        responseFields(
//                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
//                                fieldWithPath("message.dev").type(JsonFieldType.STRING).description("결과 메세지").optional(),
//                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
//                                fieldWithPath("data.token").type(JsonFieldType.STRING).description("토큰"),
//                                fieldWithPath("data.exp").type(JsonFieldType.NUMBER).description("토큰 유효시간"),
//                                fieldWithPath("data.user.id").type(JsonFieldType.NUMBER).description("유저 IDX"),
//                                fieldWithPath("data.user.uuid").type(JsonFieldType.STRING).description("유저 UUID"),
//                                fieldWithPath("data.user.username").type(JsonFieldType.STRING).description("이름"),
//                                fieldWithPath("data.user.email").type(JsonFieldType.STRING).description("이메일"),
//                                fieldWithPath("data.user.phoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호"),
//                                fieldWithPath("data.user.gender").type(JsonFieldType.STRING).description("성별"),
//                                fieldWithPath("data.user.birthday").type(JsonFieldType.STRING).description("생일").optional(),
//                                fieldWithPath("data.user.alarm").type(JsonFieldType.NUMBER).description("알람 허용 여부").optional(),
//                                fieldWithPath("data.user.alarmMidnight").type(JsonFieldType.NUMBER).description("심야 알람 허용 여부").optional(),
//                                fieldWithPath("data.user.confirmed").type(JsonFieldType.NUMBER).description("이메일 인증 여부").optional(),
//                                fieldWithPath("data.user.authType").type(JsonFieldType.STRING).description("회원 타입").optional()
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 TEST")
//    public void changeUserPassword() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        var requestParams = UserDto.ChangePasswordRequest.builder()
//                .password("123456789")
//                .newPassword("12345678")
//                .build();
//        this.mockMvc.perform(put("/api/v1/user/modify/password")
//                        .header("content-type", "application/json")
//                        .header("Authorization", JWT_TOKEN)
//                        .content(mapper.writeValueAsString(requestParams))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("user/{method-name}",
//                        requestFields(
//                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
//                                fieldWithPath("newPassword").type(JsonFieldType.STRING).description("새 비밀번호")
//                        ),
//                        responseFields(
//                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
//                                fieldWithPath("message.dev").type(JsonFieldType.STRING).description("결과 메세지 (개발자용)").optional(),
//                                fieldWithPath("message.success").type(JsonFieldType.STRING).description("결과 메세지").optional(),
//                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
//                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터").optional()
//                        )
//                ));
//    }

}
