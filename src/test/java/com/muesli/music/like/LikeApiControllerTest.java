package com.muesli.music.like;

import com.muesli.music.JwtToken;
import com.muesli.music.MuonBackendV1Application;
import com.muesli.music.interfaces.like.LikeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class LikeApiControllerTest {
    private MockMvc mockMvc;
    private final String JWT_TOKEN = JwtToken.JWT_TOKEN;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("like/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("컨텐츠 좋아요 TEST")
    public void doLike() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var requestParams = LikeDto.RegisterLike.builder()
                .likeableId(1L)
                .likeableType("Track")
                .build();
        this.mockMvc.perform(post("/api/v1/like")
                        .header("Authorization", JWT_TOKEN)
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(requestParams))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("like/{method-name}",
                        requestFields(
                                fieldWithPath("likeableId").type(JsonFieldType.NUMBER).description("좋아요할 컨텐츠 IDX"),
                                fieldWithPath("likeableType").type(JsonFieldType.STRING).description("좋아요할 컨텐츠 타입"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 IDX").ignored()
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("좋아요 IDX"),
                                fieldWithPath("data.likeableId").type(JsonFieldType.NUMBER).description("좋아요 컨텐츠 IDX"),
                                fieldWithPath("data.likeableType").type(JsonFieldType.STRING).description("좋아요 컨텐츠 타입").optional(),
                                fieldWithPath("data.isLike").type(JsonFieldType.NUMBER).description("좋아요 상태").optional(),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("좋아요한 날짜").optional(),
                                fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("컨텐츠 좋아요 개수").optional()
                        )
                ));
    }

    @Test
    @DisplayName("컨텐츠 좋아요 상태 변경 TEST")
    public void doChangeLike() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var requestParams = LikeDto.LikeInfo.builder()
                .id(136481L)
                .build();
        this.mockMvc.perform(put("/api/v1/like")
                        .header("Authorization", JWT_TOKEN)
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(requestParams))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("like/{method-name}",
                        requestFields(
                                fieldWithPath("id").description("좋아요 IDX").type(JsonFieldType.NUMBER),
                                fieldWithPath("likeableId").type(JsonFieldType.NUMBER).ignored(),
                                fieldWithPath("likeableType").type(JsonFieldType.STRING).ignored(),
                                fieldWithPath("isLike").type(JsonFieldType.NUMBER).ignored(),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).ignored(),
                                fieldWithPath("likeCount").type(JsonFieldType.NUMBER).ignored()
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("좋아요 IDX"),
                                fieldWithPath("data.likeableId").type(JsonFieldType.NUMBER).description("좋아요 컨텐츠 IDX"),
                                fieldWithPath("data.likeableType").type(JsonFieldType.STRING).description("좋아요 컨텐츠 타입").optional(),
                                fieldWithPath("data.isLike").type(JsonFieldType.NUMBER).description("좋아요 상태").optional(),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("좋아요한 날짜").optional(),
                                fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("컨텐츠 좋아요 개수").optional()
                        )
                ));
    }

    @Test
    @DisplayName("컨텐츠 리스트 좋아요 여부 조회 TEST")
    public void showLikeItem() throws Exception {
        this.mockMvc.perform(get("/api/v1/like/show")
                        .header("content-type", "application/json")
                        .param("ids", "1,2,3,4")
                        .param("type", "track")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("like/{method-name}",
                        requestParameters(
                                parameterWithName("ids").description("컨텐츠 IDX 리스트"),
                                parameterWithName("type").description("컨텐츠 타입")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("컨텐츠 타입"),
                                fieldWithPath("data.likeInfoList[].id").type(JsonFieldType.NUMBER).description("좋아요 IDX").optional(),
                                fieldWithPath("data.likeInfoList[].likeableId").type(JsonFieldType.NUMBER).description("좋아요 컨텐츠 IDX"),
                                fieldWithPath("data.likeInfoList[].likeableType").type(JsonFieldType.STRING).description("좋아요 컨텐츠 타입").optional(),
                                fieldWithPath("data.likeInfoList[].isLike").type(JsonFieldType.NUMBER).description("좋아요 상태").optional(),
                                fieldWithPath("data.likeInfoList[].createdAt").type(JsonFieldType.STRING).description("좋아요한 날짜").optional(),
                                fieldWithPath("data.likeInfoList[].likeCount").type(JsonFieldType.NUMBER).description("컨텐츠 좋아요 개수").optional()
                        )
                ));
    }
}
