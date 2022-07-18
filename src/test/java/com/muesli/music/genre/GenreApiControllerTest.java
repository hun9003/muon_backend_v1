package com.muesli.music.genre;

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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class GenreApiControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("genre/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("장르 전체 조회 TEST")
    public void retrieveGenreList() throws Exception {
        this.mockMvc.perform(get("/api/v1/genre")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("genre/{method-name}",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("장르 IDX"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("장르 이름"),
                                fieldWithPath("data[].image").type(JsonFieldType.STRING).description("장르 이미지").optional(),
                                fieldWithPath("data[].displayName").type(JsonFieldType.STRING).description("장르 노출 이름").optional(),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("장르 제목").optional(),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("장르 설명").optional(),
                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("장르 조회수"),
                                fieldWithPath("data[].albumList[].id").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data[].albumList[].name").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data[].albumList[].releaseDate").type(JsonFieldType.STRING).description("앨범 발매일"),
                                fieldWithPath("data[].albumList[].originalName").type(JsonFieldType.STRING).description("앨범 이름(원문)").optional(),
                                fieldWithPath("data[].albumList[].image").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data[].albumList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data[].albumList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data[].albumList[].trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data[].albumList[].trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data[].albumList[].trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data[].albumList[].trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data[].albumList[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data[].albumList[].trackList[].image").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data[].albumList[].trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data[].albumList[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data[].albumList[].trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data[].albumList[].trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data[].albumList[].trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data[].albumList[].trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data[].albumList[].trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data[].albumList[].trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data[].albumList[].trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("장르 개별 조회 TEST")
    public void retrieveGenre() throws Exception {
        this.mockMvc.perform(get("/api/v1/genre/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("genre/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("장르 IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("리스트 종류"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.list[].releaseDate").type(JsonFieldType.STRING).description("앨범 발매일"),
                                fieldWithPath("data.list[].originalName").type(JsonFieldType.STRING).description("앨범 이름(원문)").optional(),
                                fieldWithPath("data.list[].image").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.list[].trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.list[].trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.list[].trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.list[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.list[].trackList[].image").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.list[].trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.list[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.list[].trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.list[].trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.list[].trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.list[].trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("장르 개별 트랙 리스트 조회 TEST")
    public void retrieveGenreTracklist() throws Exception {
        this.mockMvc.perform(get("/api/v1/genre/{id}/track", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("genre/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("장르 IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("리스트 종류"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.list[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.list[].artistsLegacy").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.list[].image").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.list[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.list[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.list[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.list[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.list[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.list[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.list[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

}
