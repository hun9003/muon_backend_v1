package com.muesli.music.album;

import com.muesli.music.JwtToken;
import com.muesli.music.MuonBackendV1Application;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
class AlbumApiControllerTest {
    private MockMvc mockMvc;
    private final String JWT_TOKEN = JwtToken.JWT_TOKEN;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("album/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("앨범 조회 TEST")
    public void retrieveAlbum() throws Exception {
        this.mockMvc.perform(get("/api/v1/album/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("album/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("앨범 IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.releaseDate").type(JsonFieldType.STRING).description("앨범 발매일"),
                                fieldWithPath("data.originalName").type(JsonFieldType.STRING).description("앨범 이름(원문)").optional(),
                                fieldWithPath("data.image").type(JsonFieldType.STRING).description("앨범 이미지"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("앨범 설명").optional(),
                                fieldWithPath("data.artistInfo.id").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.artistInfo.name").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.artistInfo.originalName").type(JsonFieldType.STRING).description("아티스트 이름(원문)").optional(),
                                fieldWithPath("data.artistInfo.englishName").type(JsonFieldType.STRING).description("아티스트 이름(영어)").optional(),
                                fieldWithPath("data.trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("최신 앨범 조회 TEST")
    public void retrieveNewAlbum() throws Exception {
        this.mockMvc.perform(get("/api/v1/album/new")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("album/{method-name}",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("타입"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.list[].releaseDate").type(JsonFieldType.STRING).description("앨범 발매일"),
                                fieldWithPath("data.list[].originalName").type(JsonFieldType.STRING).description("앨범 이름(원문)").optional(),
                                fieldWithPath("data.list[].image").type(JsonFieldType.STRING).description("앨범 이미지"),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("앨범 설명").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.list[].trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.list[].trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.list[].trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.list[].trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.list[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.list[].trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.list[].trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.list[].trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.list[].trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.list[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.list[].trackList[].image").type(JsonFieldType.STRING).description("트랙 이미지").optional()
                        )
                ));
    }

    @Test
    @DisplayName("좋아하는 앨범 조회 TEST")
    public void retrieveLikeAlbumList() throws Exception {
        this.mockMvc.perform(get("/api/v1/album/likeables")
                        .header("Authorization", JWT_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("album/{method-name}",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("타입"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.list[].releaseDate").type(JsonFieldType.STRING).description("앨범 발매일"),
                                fieldWithPath("data.list[].originalName").type(JsonFieldType.STRING).description("앨범 이름(원문)").optional(),
                                fieldWithPath("data.list[].image").type(JsonFieldType.STRING).description("앨범 이미지"),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("앨범 설명").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.list[].trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.list[].trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.list[].trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.list[].trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.list[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.list[].trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.list[].trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.list[].trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.list[].trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.list[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.list[].trackList[].image").type(JsonFieldType.STRING).description("트랙 이미지").optional()
                        )
                ));
    }

}
